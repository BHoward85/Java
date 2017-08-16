//Brad Howard
//Problem 4: Own Problem

import java.io.*;
import java.util.*;

public class AircraftRegistry
{
   public static void main(String[] args)
   {
      String fileName;
      Scanner fin = null;
      PrintStream fout = null;
      File outputFile = new File("output.txt");
      LinkedList<String[]> dataList = new LinkedList<String[]>();
      LinkedList<String[]> codeList = new LinkedList<String[]>();
      int airFrame = 1;
      String date = "";//Date of registry
      String manufacturerCode = "";//Company Two letter code
      String eof = "ZZ";//End of file
   	
      if(args.length == 0)
         fileName = "input.txt";
      else
         fileName = args[0];
   	
      try
      {
         File inputFile = new File(fileName);
         fin = new Scanner(inputFile);
         fout = new PrintStream(outputFile);
      }
      catch(IOException e)
      {
         System.out.println("File not found, Error 01");
         return;
      }
      
      while(!fin.hasNext(eof))//Splitting the data and building the list
      {
         String dataTemp = fin.nextLine();
         String[] temp = dataTemp.split("([ ])");//used regex to split the data
         dataList.addLast(temp);
      }
      dateSort(dataList);//Sorting list by date
      
      while(dataList.size() != 0)
      {
         int trip = 0;//reset
         String[] info = dataList.removeFirst();
         if(info.length == 2)//Company name[one word] and date
         {
            date = info[1];
            manufacturerCode = info[0].charAt(0) + "" + info[0].charAt(1);
            String[] temp = new String[2];
            temp[0] = info[0];
            temp[1] = manufacturerCode;
            if(codeList.size() == 0)
               codeList.addLast(temp);
            else if(!manufacturerFinder(manufacturerCode, temp[0], codeList))//Checking to see if the code is taken
               while(trip != 1)//working loop, ends when a vaild code is found
               {
                  manufacturerCode = info[0].charAt(0) + "";
                  for(int index = 1; index < info[0].length() && manufacturerCode.length() < 2; index++)
                  {
                     if(!codeTester(manufacturerCode + "" + info[0].charAt(index), info[0], codeList))//testing the code if vaild
                     {
                        manufacturerCode += "" + info[0].charAt(index);
                        temp[1] = manufacturerCode;
                        codeList.addLast(temp);
                        trip = 1;
                     }
                  }//if not vaild run to find one that is vaild
               }
         }
         else if(info.length == 3)//Company name[two words] and date
         {
            date = info[2];
            manufacturerCode = info[0].charAt(0) + "" + info[1].charAt(0);
            String[] temp = new String[2];
            temp[0] = info[0] + " " + info[1];
            temp[1] = manufacturerCode;
            if(codeList.size() == 0)
               codeList.addLast(temp);
            else if(!manufacturerFinder(manufacturerCode, temp[0], codeList))//Checking to see if the code is taken
               while(trip != 1)//working loop, ends when a vaild code is found
               {
                  manufacturerCode = info[0].charAt(0) + "";
                  for(int index = 0; index < info[1].length() && manufacturerCode.length() < 2; index++)
                  {
                     if(!codeTester(manufacturerCode + "" + info[1].charAt(index), temp[0], codeList))//testing the code if vaild
                     {
                        manufacturerCode += "" + info[1].charAt(index);
                        temp[1] = manufacturerCode;
                        codeList.addLast(temp);
                        trip = 1;
                     }
                  }//if not vaild run to find one that is vaild
               }
         }
         else if(info.length == 4)//Company name[three words with 'and' in the middle] and date
         {
            date = info[3];
            manufacturerCode = info[0].charAt(0) + "" + info[2].charAt(0);
            String[] temp = new String[2];
            temp[0] = info[0] + " " + info[1] + " " + info[2];
            temp[1] = manufacturerCode;
            if(codeList.size() == 0)
               codeList.addLast(temp);
            else if(!manufacturerFinder(manufacturerCode, temp[0], codeList))//Checking to see if the code is taken
               while(trip != 1)//working loop, ends when a vaild code is found
               {
                  manufacturerCode = info[0].charAt(0) + "";
                  for(int index = 0; index < info[0].length() && manufacturerCode.length() < 2; index++)
                  {
                     if(!codeTester(manufacturerCode + "" + info[2].charAt(index), info[0], codeList))//testing the code if vaild
                     {
                        manufacturerCode += "" + info[2].charAt(index);
                        temp[1] = manufacturerCode;
                        codeList.addLast(temp);
                        trip = 1;
                     }
                  }//if not vaild run to find one that is vaild
               }
         }
         fout.println(manufacturerCode + "-" + airFrame);//printing to output
         System.out.println(manufacturerCode + "-" + airFrame);//Testing stdout
         airFrame++;//Advancing the Air Frame number
      }
   }
   
   //Checks the code list to find out if the code sent in is taken or not, returns false the code is taken
   public static boolean codeTester(String manufacturerCode, String manufacturer, LinkedList codeList)
   {
      boolean success = false;
   	
      for(int index = 0; index < codeList.size(); index++)
      {
         String[] temp = (String[])codeList.get(index);
         if(manufacturerCode.equals(temp[1]) && !manufacturer.equals(temp[0]))
            return true;
      }
      
      return success;
   }
   
   //Checks the code list to find out if the code sent is in use by the same company or if the code does not match it returns false
   public static boolean manufacturerFinder(String manufacturerCode, String manufacturer, LinkedList codeList)
   {
      boolean success = false;
   	
      for(int index = 0; index < codeList.size(); index++)
      {
         String[] temp = (String[])codeList.get(index);
         if(manufacturer.equals(temp[0]) && manufacturerCode.equals(temp[1]))
            return true;
      }
      
      return success;
   }
   
   //Sorting routine, sorts by date.
   public static void dateSort(LinkedList<String[]> dataList)
   {
      String[] temp;
      int last, sortPost;
      for(last = 0; last < dataList.size() - 1; last++)
      {
         temp = dataList.get(last + 1);
         for(sortPost = last; sortPost >= 0 && dataList.get(sortPost)[dataList.get(sortPost).length - 1].compareTo(temp[temp.length - 1]) > 0; sortPost--)
         {
            dataList.set(sortPost + 1, dataList.get(sortPost));
         }
         dataList.set(sortPost + 1, temp);
      }
   }
}