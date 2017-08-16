// Read Sort Write Program

import java.io.*;
import java.util.*;

public class ReadSortWriter
{
	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);
		Scanner input = null;
		File in;
		String fileIn = "";
		String fileOut = "";
		String userInput = "";
		PrintStream out = null;
		List<String> list = new ArrayList<String>();
		
		if(args.length == 2)
		{
			fileIn = args[0];
			fileOut = args[1];
		}
		
		try
		{
			in = new File(fileIn);
			input = new Scanner(in);
			out = new PrintStream(fileOut);
		}
		catch(IOException ioe)
		{
			System.out.println("File flub");
			return;
		}
		
		while(input.hasNext())
		{
			list.add(input.next());
		}
		
		Collections.sort(list);
		
		for(int index = 0; index < list.size(); index++)
		{
			out.println(list.get(index));
		}
		
		System.out.println("Done");
		out.close();
		input.close();
	}
}