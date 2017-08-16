// Brad Howard
// Project 2: Main

import java.io.*;
import java.net.*;
import java.util.*;

public class WordFrequency
{
   private static BPlusTree BPT = new BPlusTree();
   private static ArrayList<String> stopSet = new ArrayList<String>();
   private static ArrayList<String> wordSet = new ArrayList<String>();
   private static String ignoreFile = "";
   private static String webAddess = "";
	
   public static void main(String[] args)
   {
      if(args.length == 2)
      {
         webAddess = args[0];
         ignoreFile = args[1];
      }
      else
      {
         System.out.println("Command line argument error");
         System.out.println("input needs to formed as showen\n % java WordFrequency <URL> [ignore_file.txt]");
         return;
      }
   	
      setUp();
      runMenu();
   }
	
   private static void runMenu()
   {
      Scanner userIn = new Scanner(System.in);
      String word = "";
      String min = "";
      String max = "";
      int menuNumber = 0;
      int nodeNumber = 0;
   	
      while(true)
      {
         System.out.println("\n1: Print out all words   2: Display B+ Tree");
         System.out.println("3: Print a Node          4: Insert a Word");
         System.out.println("5: Search for a Word     6: Range Search");
         System.out.print("-1: Exit\nEnter a option number: ");
         menuNumber = userIn.nextInt();
         userIn.nextLine();
      
         switch(menuNumber)
         {
            case 1:
               BPT.printAllStrings();
               break;
            case 2:
               BPT.printAllEntries();
               break;
            case 3:
               System.out.print("Enter in the node to be displayed\nNode number: ");
               nodeNumber = userIn.nextInt();
               BPT.printNode(nodeNumber);
               break;
            case 4:
               System.out.print("Enter in word to be inserted for: ");
               word = userIn.nextLine();
               BPT.insertWord(word);
               break;
            case 5:
               System.out.print("Enter in word to be searched for: ");
               word = userIn.nextLine();
               BPT.search(word);
               break;
            case 6:
               System.out.print("Enter the start of the start search range: ");
               min = userIn.nextLine();
               System.out.print("Enter the start of the end search range: ");
               max = userIn.nextLine();
               BPT.rangeSearch(min, max);
               break;
            case -1:
               System.out.println("Exiting the system.");
               return;
            default:
               System.out.println("Out side the menu range.");
         }
      }
   }
	
   private static void setUp()
   {
      URL url;
      BufferedReader buffIn;
      File in = new File(ignoreFile);
      Scanner fileIn;
      String temp = "";
      String line = "";
   	
      try
      {
         url = new URL(webAddess);
         buffIn = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
         fileIn = new Scanner(in);
      }
      catch(MalformedURLException w)
      {
         System.out.println("The URL was malformed.");
         return;
      }
      catch(IOException e)
      {
         System.out.println("File in error");
         return;
      }
   	
      while(fileIn.hasNext())
      {
         stopSet.add(fileIn.nextLine());
      }
   	
      try
      {
         while((temp = buffIn.readLine()) != null)
         {
            line += temp;
         }
      }
      catch(IOException i)
      {
         System.out.println("Input error");
      }
   	
      String[] set = line.split("[^a-zA-Z]");
   	
      for(String s : set)
      {
         if(!s.equals(""))
         {
            wordSet.add(s.toLowerCase());
         }
      }
   	
      Collections.sort(wordSet);
      
      for(int index = 0; index < wordSet.size(); index++)
      {
         if(!stopSet.contains(wordSet.get(index)))
            BPT.insertWord(wordSet.get(index));
      }
   }
}