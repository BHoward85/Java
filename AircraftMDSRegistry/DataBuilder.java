//Problem 04 support programe

import java.io.*;
import java.util.*;

public class DataBuilder
{
	public static void main(String[] args)
	{
		int number = 1;
      int numberOfEnteries = 0;
      int[] companyRate = new int[50];
      String[] companyList = {"Aero-fleet", "Arado", "Arrow", "Draken", "Dornier", "Douglas", "Grenit", "Grumman",
								"Fairey", "Vickers", "Vought", "Scoots and Palmer", "New Republic", "Sumter and Norrland",
								"Marin", "Curtiss", "New American", "Focke Wulf", "National Electric", "Hawker", "Lockheed",
								"New Fokker", "United Air", "Lanyard and Standard", "Ikssoma", "Avro", "Bell", "Blackburn",
								"Boeing", "Blohm und Voss", "Canadian", "Convair", "Dassault", "De Havilland", "Heinkel",
								"Handley Page", "Henschel", "Hunting", "Johnson", "Junkers", "McDonnel", "Messerschmitt",
								"New Martin", "Northrop", "Siddeley", "Supermarine", "Tropp", "Zolo", "Westland", "Rockwell"};
		
		System.out.println(companyList.length);
      Arrays.sort(companyList);
		for(String s : companyList)
      {
         System.out.println(number + ": " + s);
         number++;
      }
      
      companyRate[0] = 100 * 2;//Aero-Fleet common company
      companyRate[2] = 300 * 3;//Arrow high rate of entery
      companyRate[5] = 125 * 2;//Blackburn common Navy company
      companyRate[6] = 25 * 1;//Blohm und Voss not very common
      companyRate[7] = 80 * 2;//Boeing common at first
      companyRate[10] = 70 * 2;//Curtiss common at first
      companyRate[14] = 175 * 3;//Douglas very common Navy company
      companyRate[15] = 200 * 3;//Draken Very common company
      companyRate[18] = 250 * 3;//Grenit Very common company
      companyRate[19] = 195 * 3;//Grumman Very common Navy company
      companyRate[21] = 80 * 2;//Hawker some what common company
      companyRate[25] = 10 * 1;//Ikssoma Very rare company
      companyRate[28] = 65 * 2;//Lanyard and Standard limited company
      companyRate[30] = 220 * 3;//Marin Very common company
      companyRate[33] = 150 * 3;//National Electric Very common company
      companyRate[37] = 250 * 3;//New Republic Very common company
      companyRate[40] = 250 * 3;//Scoots and Palmer Very common company
      companyRate[42] = 250 * 3;//Sumter and Norrland Very common company
      companyRate[45] = 180 * 2;//United Air common company
      companyRate[47] = 175 * 2;//Vought common Navy company
      
      for(int index = 0; index < 50; index++)
      {
         if(companyRate[index] == 0)
            companyRate[index] = 45 * 2;//Base rate
      }
      
      for(int index = 0; index < 50; index++)
      {
         numberOfEnteries += companyRate[index];
      }
      System.out.println("The number of enteries is: " + numberOfEnteries);
            
      outToFile(4500, companyRate, companyList);
	}
   
   public static void outToFile(int numberOfEnteries, int[] companyRate, String[] companyList)
   {
      Random rng = new Random();
      int[] date = new int[3];
      int push = 1;
      date[0] = 1912;
      date[1] = 1;
      date[2] = 1;
      PrintStream fout;
      File outputFile = new File("input.txt");
      try
      {
         fout = new PrintStream(outputFile);
      }
      catch(IOException e)
      {
         System.out.println("Something went bad, Error LOL");
         return;
      }

      while(numberOfEnteries > 0)
      {
         int index = indexFinder(rng);
         
         if(push == 45)
         {
            push = 1;
            date[0]++;
         }
         if(companyRate[index] != 0)
         {
            date[1] = rng.nextInt(12) + 1;
            date[2] = rng.nextInt(28) + 1;
            fout.println(companyList[index] + " " + date[0] + "-" + date[1] + "-" + date[2]);
            companyRate[index]--;
            numberOfEnteries--;
            push++;
         }
      }
      fout.print("ZZ");
      fout.close();
   }
   
   public static int indexFinder(Random rng)
   {
      int n = rng.nextInt(10000) + 1;
      int index = 0;
      
      if(n >= 1 && n <= 667)
         index = 2;
      else if(n >= 668 && n <= 1223)
         index = 18;
      else if(n >= 1224 && n <= 1779)
         index = 37;
      else if(n >= 1780 && n <= 2335)
         index = 40;
      else if(n >= 2336 && n <= 2891)
         index = 42;
      else if(n >= 2892 && n <= 3381)
         index = 30;
      else if(n >= 3382 && n <= 3826)
         index = 15;
      else if(n >= 3827 && n <= 4227)
         index = 45;
      else if(n >= 4228 && n <= 4661)
         index = 19;
      else if(n >= 4662 && n <= 5051)
         index = 47;
      else if(n >= 5052 && n <= 5441)
         index = 14;
      else if(n >= 5442 && n <= 5775)
         index = 33;
      else if(n >= 5776 && n <= 6054)
         index = 5;
      else if(n >= 6055 && n <= 6277)
         index = 0;
      else if(n >= 6278 && n <= 6456)
         index = 7;
      else if(n >= 6457 && n <= 6635)
         index = 21;
      else if(n >= 6636 && n <= 6792)
         index = 10;
      else if(n >= 6793 && n <= 6937)
         index = 28;
      else if(n >= 6938 && n <= 6994)
         index = 6;
      else if(n >= 6995 && n <= 7017)
         index = 25;
      else if(n >= 7018 && n <= 8218)
      {
         int m = rng.nextInt(11) + 1;
         if(m == 1)
            index = 1;
         else if(m == 2)
            index = 3;
         else if(m == 3)
            index = 4;
         else if(m == 4)
            index = 8;
         else if(m == 5)
            index = 9;
         else if(m == 6)
            index = 11;
         else if(m == 7)
            index = 12;
         else if(m == 8)
            index = 13;
         else if(m == 9)
            index = 16;
         else if(m == 10)
            index = 17;
         else if(m == 11)
            index = 20;
         else if(m == 12)
            index = 22;
         
      }
      else//for n >= 8219 to n <= 10000
      {
         int m = rng.nextInt(17) + 1;
         if(m == 1)
            index = 23;
         else if(m == 2)
            index = 24;
         else if(m == 3)
            index = 26;
         else if(m == 4)
            index = 27;
         else if(m == 5)
            index = 29;
         else if(m == 6)
            index = 31;
         else if(m == 7)
            index = 32;
         else if(m == 8)
            index = 34;
         else if(m == 9)
            index = 35;
         else if(m == 10)
            index = 36;
         else if(m == 11)
            index = 38;
         else if(m == 12)
            index = 39;
         else if(m == 13)
            index = 41;
         else if(m == 14)
            index = 43;
         else if(m == 15)
            index = 44;
         else if(m == 16)
            index = 46;
         else if(m == 17)
            index = 48;
         else if(m == 18)
            index = 49;
      }
         
      return index;
   }
}