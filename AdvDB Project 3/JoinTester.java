// Brad Howard
// Project 3: Join Tester

import java.io.*;
import java.util.*;

public class JoinTester
{
   public static void main(String[] args)
   {
      int blockSize;
      int memorySize;
      File students = new File("student.txt");
      File takes = new File("takes.txt");
      SortMergeJoin SMJ;
      HashJoin HJ;
      
      if(args.length == 2)
		{
			blockSize = Integer.parseInt(args[0]);
			memorySize = Integer.parseInt(args[1]);
		}
      else
      {
         System.out.println("Length of arguments is incompatible.");
         System.out.println("The command line input should be:\n% java joinTester block_size memory_size");
         return;
      }

      SMJ = new SortMergeJoin(students, takes, memorySize, blockSize);
      HJ = new HashJoin(students, takes, memorySize, blockSize);
      
      SMJ.sort();
      SMJ.merge();
      SMJ.join();
      HJ.hash();
      HJ.join();
   }
}