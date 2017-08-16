// Brad Howard
// Project 3: Hash Join

import java.util.*;
import java.io.*;

public class HashJoin
{
   private ArrayList<String> dataSet1 = new ArrayList<String>();
   private ArrayList<String> dataSet2 = new ArrayList<String>();
   private LinkedList<Tuple>[] hashTuple1;
   private LinkedList<Tuple>[] hashTuple2;
   private LinkedList<Block>[] hashTable1;
   private LinkedList<Block>[] hashTable2;
   private LinkedList<String> joinSet = new LinkedList<String>();
   private Block[] blocks1;
   private Block[] blocks2;
   private Scanner input1;
   private Scanner input2;
   private PrintStream output;
   private File file1;
   private File file2;
   private int memorySize;
   private int blockSize;
   private int hashedDataSet;
   private Block[] memory;
   
   public HashJoin(File a, File b, int sizeOfMemory, int sizeOfBlock)
   {
      file1 = a;
      file2 = b;
      memorySize = sizeOfMemory;
      blockSize = sizeOfBlock;
      
      memory = new Block[memorySize];
      hashTuple1 = new LinkedList[memorySize - 1];
      hashTuple2 = new LinkedList[memorySize - 1];
      hashTable1 = new LinkedList[memorySize - 1];
      hashTable2 = new LinkedList[memorySize - 1];
      
      for(int index = 0; index < memorySize - 1; index++)
      {
         hashTuple1[index] = new LinkedList<Tuple>();
         hashTuple2[index] = new LinkedList<Tuple>();
         hashTable1[index] = new LinkedList<Block>();
         hashTable2[index] = new LinkedList<Block>();
      }
         
      try
      {
         input1 = new Scanner(file1);
         input2 = new Scanner(file2);
      }
      catch(IOException ioe)
      {
         System.out.println("Error");
         return;
      }
   }
   
   public void hash()
   {
      getData();
      
      int numberOfBlocks1 = 1 + dataSet1.size() / blockSize;
      int numberOfBlocks2 = 1 + dataSet2.size() / blockSize;
      
      if(Math.sqrt((double)numberOfBlocks1) > (double)memorySize || Math.sqrt((double)numberOfBlocks2) > (double)memorySize)
      {
         System.out.println("Not enough memory is available.");
         return;
      }
   
      blocks1 = new Block[numberOfBlocks1];
      blocks2 = new Block[numberOfBlocks2];
      
      makeBlocks();
      
      buildHashTable(hashTable1, hashTuple1, blocks1);
      buildHashTable(hashTable2, hashTuple2, blocks2);
      
      writeHashBucketsToFile();
      
      if(numberOfBlocks1 <= numberOfBlocks2)
      {
         hashedDataSet = 1;
      }
      else
      {
         hashedDataSet = 2;
      }
   }
   
   public void buildHashTable(LinkedList<Block>[] hashTable, LinkedList<Tuple>[] hashTuple, Block[] blocks)
   {
      int bdex;
      
      for(int index = 0; index < blocks.length; index++)
      {
         for(bdex = 0; bdex < blocks[index].numberOfRecords(); bdex++)
         {
            int t = hashFunction(blocks[index].getRecord(bdex).get(0));
            Tuple tuple = new Tuple();
            tuple.update(blocks[index].getRecord(bdex).toString().split("\t"));
            
            hashTuple[t].add(tuple);
         }
      }
      
      for(int index = 0, jdex = 0; index < memorySize - 1;)
      {
         Block temp = new Block(blockSize);
         
         for(bdex = 0; bdex < blockSize && jdex < hashTuple[index].size(); bdex++, jdex++)
         {
            temp.addRecord(hashTuple[index].get(jdex).toString());
         }
         
         if(temp.numberOfRecords() != 0)
            hashTable[index].add(temp);
         if(jdex <= hashTuple[index].size() && bdex < blockSize)
         {
            jdex = 0;
            index++;
         }
      }
   }
   
   private void writeHashBucketsToFile()
   {
      try
      {
         for(int index = 0; index < hashTable1.length; index++)
         {
            output = new PrintStream(".\\output\\hj_student_" + index + ".txt");
            for(int jdex = 0; jdex < hashTable1[index].size(); jdex++)
            {
               for(int kdex = 0; kdex < hashTable1[index].get(jdex).numberOfRecords(); kdex++)
               {
                  output.println(hashTable1[index].get(jdex).getRecord(kdex).toString());
               }
            }
         }
         
         for(int index = 0; index < hashTable2.length; index++)
         {
            output = new PrintStream(".\\output\\hj_takes_" + index + ".txt");
            for(int jdex = 0; jdex < hashTable2[index].size(); jdex++)
            {
               for(int kdex = 0; kdex < hashTable2[index].get(jdex).numberOfRecords(); kdex++)
               {
                  output.println(hashTable2[index].get(jdex).getRecord(kdex).toString());
               }
            }
         }
      }
      catch(IOException d)
      {
         System.out.println("Error");
         return;
      }
   }
   
   public void join()
   {
      int numberOfBlocks1 = 1 + dataSet1.size() / blockSize;
      int numberOfBlocks2 = 1 + dataSet2.size() / blockSize;
      
      if(Math.sqrt((double)numberOfBlocks1) > (double)memorySize || Math.sqrt((double)numberOfBlocks2) > (double)memorySize)
      {
         System.out.println("Not enough memory is available.");
         return;
      }
      
      if(hashedDataSet == 1)
         join(hashTable1, blocks2);
      else
         join(hashTable2, blocks1);
   }
   
   private void join(LinkedList<Block>[] hashTable, Block[] blocks)
   {
      int n = 0;
      for(int index = 0; index < hashTable.length; index++)
      {
         for(int bdex = 0; bdex < hashTable[index].size(); bdex++)
         {
            memory[bdex] = hashTable[index].get(bdex);
         }
         
         for(int jdex = 0; jdex < blocks.length; jdex++)
         {
            for(int kdex = 0; kdex < blocks[jdex].numberOfRecords(); kdex++)
            {
               int t = hashFunction(blocks[jdex].getRecord(kdex).get(0));
               
               if(t == index)
               {
                  for(int ndex = 0; ndex < hashTable[index].size(); ndex++)
                  {
                     for(int bdex = 0; bdex < memory[ndex].numberOfRecords(); bdex++)
                     {
                        if(Integer.parseInt(blocks[jdex].getRecord(kdex).get(0)) == Integer.parseInt(memory[ndex].getRecord(bdex).get(0)))
                        {
                           String s = memory[ndex].getRecord(bdex).toString();
                           
                           for(int ldex = 1; ldex < blocks[jdex].getRecord(kdex).numberCols(); ldex++)
                           {
                              s += "\t" + blocks[jdex].getRecord(kdex).get(ldex);
                           }
                           
                           joinSet.add(s);
                        }
                     }
                  }
               }
            }
         }
      }
      
      writeJoinToFile();
   }
   
   private void writeJoinToFile()
   {
      try
      {
         output = new PrintStream(".//output//hj.txt");
      }
      catch(IOException s)
      {
         System.out.println("Error");
         return;
      }
      
      for(String s : joinSet)
      {
         output.println(s);
      }  
   }
   
   private void makeBlocks()
   {
      for(int index = 0, jdex = 0; index < blocks1.length; index++)
      {
         blocks1[index] = new Block(blockSize);
         blocks1[index].setBID(index);
         for(int bdex = 0; bdex < blockSize && jdex < dataSet1.size(); jdex++, bdex++)
         {
            blocks1[index].addRecord(dataSet1.get(jdex));
         }
         blocks1[index].sortRecords();
      }
      
      for(int index = 0, jdex = 0; index < blocks2.length; index++)
      {
         blocks2[index] = new Block(blockSize);
         blocks2[index].setBID(index);
         for(int bdex = 0; bdex < blockSize && jdex < dataSet2.size(); jdex++, bdex++)
         {
            blocks2[index].addRecord(dataSet2.get(jdex));
         }
         blocks2[index].sortRecords();
      }
   }
   
   private void getData()
   {
      while(input1.hasNext())
      {
         dataSet1.add(input1.nextLine());
      }
      while(input2.hasNext())
      {
         dataSet2.add(input2.nextLine());
      }
   }
   
   public int hashFunction(String ID)
   {
      int k = Integer.parseInt(ID);
      int j = 0;
      
      for(int index = 0; k != 0; index++)
      {
         j += k;
         k /= 10;
      }
      
      j %= memorySize - 1;
      
      return j;
   }
}