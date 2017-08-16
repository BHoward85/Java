//Brad Howard
//Problem 4: Own Problem

import java.io.*;
import java.util.*;

public class AircraftRegistryA
{
	public static void main(String[] args)
	{
		String fileName;
		Scanner fin = null;
		PrintStream fout = null;
		File outputFile = new File("outputTest.txt");
		LinkedList<String> dataOut = new LinkedList<String>();
      LinkedList<String[]> codeList = new LinkedList<String[]>();
		int airFrame = 1;
		String date = "";
		String manufacturerCode = "";
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
		
		while(!fin.hasNext(eof))
		{
			String dataIn = fin.nextLine();
			String[] info = dataIn.split("([ ])");
			if(info.length == 2)//One word, Manufacturer name
			{
				date = info[1];
				manufacturerCode = info[0].charAt(0) + "";
				String[] temp = new String[2];
				temp[0] = info[0];
				
				for(int index = 1; index < info[0].length() && manufacturerCode.length() < 2; index++)
				{
				   if(info[0].charAt(index) != 'a' && info[0].charAt(index) != 'e' && info[0].charAt(index) != 'i' && info[0].charAt(index) != 'u')
				   {
					  manufacturerCode += "" + info[0].charAt(index);
					  temp[1] = manufacturerCode;
					  codeList.addLast(temp);
				   }
				}
				while(codeList.size() != 1 && !codeTester(manufacturerCode, info[0], codeList))
				{
				   manufacturerCode = info[0].charAt(0) + "";
				   for(int index = 1; index < info[0].length() && manufacturerCode.length() < 2; index++)
				   {
					  if(info[0].charAt(index) != 'a' && info[0].charAt(index) != 'e' && info[0].charAt(index) != 'i' && info[0].charAt(index) != 'u')
						 if(!codeTester(manufacturerCode + "" + info[0].charAt(index), info[0], codeList))
						 {
							manufacturerCode += "" + info[0].charAt(index);
							temp[1] = manufacturerCode;
							codeList.addLast(temp);
						 }
				   }
				}
			}
			else if(info.length == 3)//Two words, Manufacturer name
			{
				date = info[2];
				manufacturerCode = info[0].charAt(0) + "" + info[1].charAt(0);
			}
			else if(info.length == 4)//Two words with a and in the middle of the Manufacturer name
			{
				date = info[3];
				if(!info[1].equals("and"))
               manufacturerCode = info[0].charAt(0) + "" + info[1].charAt(0);
            else
               manufacturerCode = info[0].charAt(0) + "" + info[2].charAt(0);
			}
         dataOut.addLast(manufacturerCode + "-" + airFrame);//Add to the list
         System.out.println(manufacturerCode + "-" + airFrame);//Testing stdout
         fout.println(manufacturerCode + "-" + airFrame);//Printing to file
         airFrame++;//Advancing the Air Frame number
		}
		fin.close();
		fout.close();
	}
	//Finding out if the code is already in uses by another Manufacturer
	public static boolean codeTester(String manufacturerCode, String manufacturer, LinkedList codeList)
	{
		boolean success = false;
		
      for(int index = 0; index < codeList.size() - 1; index++)
      {
         String[] temp = (String[])codeList.get(index);
         if(manufacturerCode.equals(temp[1]) && manufacturer.equals(temp[0]))
            success = true;
      }
      
		return success;
	}
	//Finding a code used by Manufacturer
	public static boolean manufacturerFinder(String manufacturerCode, String manufacturer)
	{
		boolean success = false;
		
		return success;
	}
}