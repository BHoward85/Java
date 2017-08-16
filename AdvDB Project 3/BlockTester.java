import java.io.*;
import java.util.*;

public class BlockTester
{
   public static void main(String[] args)
   {
      Block[] memory = new Block[3];
      String[] inputData = new String[9];
      
      for(int index = 0; index < memory.length; index++)
      {
         memory[index] = new Block(3);
      }
      
      for(int index = 0; index < inputData.length; index++)
      {
         inputData[index] = "A   B G   C   D   5";
      }
      
      memory[0].addRecord(inputData[0]);
      
      SortMergeJoin SMJ = new SortMergeJoin(new File("student.txt"), new File("takes.txt"), 32, 4);
      SMJ.sort();  
      SMJ.merge();
      SMJ.join();
      
      HashJoin HJ = new HashJoin(new File("student.txt"), new File("takes.txt"), 32, 4);
      HJ.hash();
      HJ.join();
      
      int t = HJ.hashFunction("1000");
      t = HJ.hashFunction("163");
      t = HJ.hashFunction("123");
      t = HJ.hashFunction("108");
      t = HJ.hashFunction("107");
   }
}