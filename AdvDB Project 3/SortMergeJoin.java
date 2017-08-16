// Brad Howard
// Project 3: Sort Merge Join

import java.util.*;
import java.io.*;

public class SortMergeJoin
{
   private ArrayList<String> dataSet1 = new ArrayList<String>();
   private ArrayList<String> dataSet2 = new ArrayList<String>();
   private ArrayList<Block> sortedDataSet1;
   private ArrayList<Block> sortedDataSet2;
   private List<Block> joinedDataSet;
   private Block[] blocks1;
   private Block[] blocks2;
   private Partition[] pars1;
   private Partition[] pars2;
   private int numberOfPart1;
   private int numberOfPart2;
   private Scanner input1;
   private Scanner input2;
   private PrintStream output1;
   private PrintStream output2;
   private File file1;
   private File file2;
   private int memorySize;
   private int blockSize;
   private Block[] memory;
   private int dataSetSize1;
   private int dataSetSize2;
   private int[] parsBlock1;
   private int[] parsBlock2;
   private int[] parsIndex1;
   private int[] parsIndex2;
   private int numberOfBlocks1;
   private int numberOfBlocks2;

   
   public SortMergeJoin(File a, File b, int sizeOfMemory, int sizeOfBlock)
   {
      file1 = a;
      file2 = b;
      memorySize = sizeOfMemory;
      blockSize = sizeOfBlock;
      memory = new Block[memorySize];
      
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
   
   public void sort()
   {
      getData();
      
      numberOfBlocks1 = 1 + dataSet1.size() / blockSize;
      numberOfBlocks2 = 1 + dataSet2.size() / blockSize;
      
      if(Math.sqrt((double)numberOfBlocks1) > (double)(memorySize - 1) || Math.sqrt((double)numberOfBlocks2) > (double)(memorySize - 1))
      {
         System.out.println("Not enough memory is available.");
         return;
      }
      
      blocks1 = new Block[numberOfBlocks1];
      blocks2 = new Block[numberOfBlocks2];
            
      makeBlocks();
            
      numberOfPart1 = 1 + numberOfBlocks1 / memorySize;
      numberOfPart2 = 1 + numberOfBlocks2 / memorySize;
      
      pars1 = new Partition[numberOfPart1];
      pars2 = new Partition[numberOfPart2];
      
      makePartitions();
      sortPartitions();
      writePartitionsToFile();
   }
   
   public void merge()
   {
      if(Math.sqrt((double)numberOfBlocks1) > (double)(memorySize - 1) || Math.sqrt((double)numberOfBlocks2) > (double)(memorySize - 1))
      {
         System.out.println("Not enough memory is available.");
         return;
      }
      
      dataSetSize1 = blocks1.length;
      dataSetSize2 = blocks2.length;
      parsBlock1 = new int[numberOfPart1];
      parsBlock2 = new int[numberOfPart2];
      parsIndex1 = new int[numberOfPart1];
      parsIndex2 = new int[numberOfPart2];
      sortedDataSet1 = new ArrayList<Block>(dataSetSize1);
      sortedDataSet2 = new ArrayList<Block>(dataSetSize2);
            
      //place in sort method
      mergePars(pars1, parsIndex1, parsBlock1, sortedDataSet1, dataSetSize1);
      mergePars(pars2, parsIndex2, parsBlock2, sortedDataSet2, dataSetSize2);
      writeSortedDataSetsToFile();
   }
   
   public void join()
   {
      if(Math.sqrt((double)numberOfBlocks1) > (double)(memorySize - 1) || Math.sqrt((double)numberOfBlocks2) > (double)(memorySize - 1))
      {
         System.out.println("Not enough memory is available.");
         return;
      }
      
      joinedDataSet = new LinkedList<Block>();
      int ndex = 0;
      int odex = 0;
      try
      {
         output1 = new PrintStream(".\\output\\smj.txt");
         
         for(int index = 0, jdex = 0; index < sortedDataSet1.size() && jdex < sortedDataSet2.size(); index++)
         {
            for(odex = 0; jdex < sortedDataSet2.size() && odex < sortedDataSet1.get(index).numberOfRecords();)
            {
               if(jdex < sortedDataSet2.size() && ndex >= sortedDataSet2.get(jdex).numberOfRecords())
               {
                  jdex++;
                  ndex = 0;
               }
               
               for(;jdex < sortedDataSet2.size() && ndex < sortedDataSet2.get(jdex).numberOfRecords() && odex < sortedDataSet1.get(index).numberOfRecords();)
               {
                  if(Integer.parseInt(sortedDataSet1.get(index).getRecord(odex).get(0)) == Integer.parseInt(sortedDataSet2.get(jdex).getRecord(ndex).get(0)))
                  {
                     String s = sortedDataSet1.get(index).getRecord(odex).toString();
                     
                     for(int ldex = 1; ldex < sortedDataSet2.get(jdex).getRecord(ndex).numberCols(); ldex++)
                     {
                        s += "\t" + sortedDataSet2.get(jdex).getRecord(ndex).get(ldex);
                     }
                     
                     output1.println(s);
                     ndex++;
                  }
                  else
                  {
                     odex++;
                  }
               }
            }
         }
      }
      catch(IOException i)
      {
         System.out.println("Error");
      }
   }
   
   private void mergePars(Partition[] pars, int[] parsIndex, int[] parsBlock, ArrayList<Block> sortedDataSet, int dataSetSize)
   {
      for(int index = 0, jdex = 0; index < parsIndex.length && jdex < (memorySize - 1); index++, jdex++)
      {
         memory[jdex] = pars[index].get(parsIndex[index]);
      }
      
      while(sortedDataSet.size() < dataSetSize)
      {
         memory[memorySize - 1] = new Block(blockSize);
         
         for(int index = 0; index < blockSize; index++)
         {
            int small = Integer.MAX_VALUE;
            int sdex = 0;
            
            for(int jdex = 0; jdex < pars.length; jdex++)
            {
               if(parsIndex[jdex] < pars[jdex].get(parsBlock[jdex]).numberOfRecords() && small > Integer.parseInt(pars[jdex].get(parsBlock[jdex]).getRecord(parsIndex[jdex]).get(0)))
               {
                  small = Integer.parseInt(pars[jdex].get(parsBlock[jdex]).getRecord(parsIndex[jdex]).get(0));
                  sdex = jdex;
               }
            }
            
            if(parsIndex[sdex] < pars[sdex].get(parsBlock[sdex]).numberOfRecords())
            {
               memory[memorySize - 1].addRecord(pars[sdex].get(parsBlock[sdex]).getRecord(parsIndex[sdex]).toString());
               parsIndex[sdex] += 1;
            }
            
            if(parsIndex[sdex] > (blockSize - 1) && parsBlock[sdex] < pars[sdex].numberOfBlocks() && (parsBlock[sdex] + 1) < pars[sdex].numberOfBlocks())
            {
               memory[sdex] = getNextBlock(pars, sdex, parsBlock[sdex] + 1);
               parsBlock[sdex] += 1;
               parsIndex[sdex] = 0;
            }
         }
         sortedDataSet.add(memory[memorySize - 1]);
      }
   }
   
   private Block getNextBlock(Partition[] pars,int index, int next)
   {
      return pars[index].get(next);
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
   
   private void makePartitions()
   {
      for(int index = 0, jdex = 0; index < pars1.length; index++)
      {
         pars1[index] = new Partition(memorySize);
         for(int pdex = 0; pdex < pars1[index].size() && jdex < blocks1.length; jdex++, pdex++)
         {
            pars1[index].set(blocks1[jdex]);
         }
      }
      
      for(int index = 0, jdex = 0; index < pars2.length; index++)
      {
         pars2[index] = new Partition(memorySize);
         for(int pdex = 0; pdex < pars2[index].size() && jdex < blocks2.length; jdex++, pdex++)
         {
            pars2[index].set(blocks2[jdex]);
         }
      }
   }
   
   private void sortPartitions()
   {
      for(int index = 0; index < pars1.length; index++)
      {
         Block temp = new Block(blockSize * memorySize);
         for(int jdex = 0; jdex < pars1[index].numberOfBlocks(); jdex++)
         {
            for(int kdex = 0; kdex < pars1[index].get(jdex).numberOfRecords(); kdex++)
            {
               temp.addRecord(pars1[index].get(jdex).getRecord(kdex).toString());
            }
         }
         temp.sortRecords();
         int oldParSize = pars1[index].numberOfBlocks();
         pars1[index] = new Partition(memorySize);
         for(int sdex = 0, jdex = 0; sdex < oldParSize; sdex++)
         {
            Block newBlock = new Block(blockSize);
            for(int bdex = 0; bdex < blockSize && jdex < temp.numberOfRecords(); bdex++, jdex++)
            {
               newBlock.addRecord(temp.getRecord(jdex).toString());
            }
            pars1[index].set(newBlock);
         }
      }
      
      for(int index = 0; index < pars2.length; index++)
      {
         Block temp = new Block(blockSize * memorySize);
         for(int jdex = 0; jdex < pars2[index].numberOfBlocks(); jdex++)
         {
            for(int kdex = 0; kdex < pars2[index].get(jdex).numberOfRecords(); kdex++)
            {
               temp.addRecord(pars2[index].get(jdex).getRecord(kdex).toString());
            }
         }
         temp.sortRecords();
         int oldParSize = pars2[index].numberOfBlocks();
         pars2[index] = new Partition(memorySize);
         for(int sdex = 0, jdex = 0; sdex < oldParSize; sdex++)
         {
            Block newBlock = new Block(blockSize);
            for(int bdex = 0; bdex < blockSize && jdex < temp.numberOfRecords(); bdex++, jdex++)
            {
               newBlock.addRecord(temp.getRecord(jdex).toString());
            }
            pars2[index].set(newBlock);
         }
      }
   }
   
   private void writePartitionsToFile()
   {
      try
      {
         for(int index = 0; index < pars1.length; index++)
         {
            output1 = new PrintStream(".\\output\\smj_student_" + index + ".txt");
            for(int jdex = 0; jdex < pars1[index].numberOfBlocks(); jdex++)
            {
               for(int kdex = 0; kdex < pars1[index].get(jdex).numberOfRecords(); kdex++)
               {
                  output1.println(pars1[index].get(jdex).getRecord(kdex).toString());
               }
            }
         }
         
         for(int index = 0; index < pars2.length; index++)
         {
            output2 = new PrintStream(".\\output\\smj_takes_" + index + ".txt");
            for(int jdex = 0; jdex < pars2[index].numberOfBlocks(); jdex++)
            {
               for(int kdex = 0; kdex < pars2[index].get(jdex).numberOfRecords(); kdex++)
               {
                  output2.println(pars2[index].get(jdex).getRecord(kdex).toString());
               }
            }
         }
      }
      catch(IOException i)
      {
         System.out.println("Error");
      }
   }
   
   private void writeSortedDataSetsToFile()
   {
      try
      {
         output1 = new PrintStream(".\\output\\sorted_student.txt");
         for(Block b : sortedDataSet1)
         {
            for(int index = 0; index < b.numberOfRecords(); index++)
            {
               output1.println(b.getRecord(index));
            }
         }
         
         output2 = new PrintStream(".\\output\\sorted_takes.txt");
         for(Block b : sortedDataSet2)
         {
            for(int index = 0; index < b.numberOfRecords(); index++)
            {
               output2.println(b.getRecord(index));
            }
         }
      }
      catch(IOException i)
      {
         System.out.println("Error");
      }
   }
}