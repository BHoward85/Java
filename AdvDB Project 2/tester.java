// Tester for B+ Tree

import java.io.*;
import java.net.*;
import java.util.*;

public class tester
{
   public static void main(String[] args)
   {
      Scanner input = null;
      Scanner htmlIn = null;
      Scanner kb = new Scanner(System.in);
      BufferedReader br = null;
      ArrayList<String> fullSet = new ArrayList<String>();
      ArrayList<String> stopSet = new ArrayList<String>();
      File ht = new File("test.html");
      File in = new File("sortedstoplist.txt");
      BPlusTree BPT = new BPlusTree(2);
      String line = "";
      String te = "";
      String addess = "";
      String[] sAra = {"bob", "all", "aaa", "alt", "bot", "this", "ask", "atom", "zed", "set", "sell", "jet", "abc", "dog", "me", "ned"};
      URL url;
      
      System.out.print("Enter a url: ");
      addess = kb.nextLine();
      
      try
      {
         input = new Scanner(in);
         //htmlIn = new Scanner(ht);
         url = new URL(addess);
         br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
      }
      catch (MalformedURLException e)
      {
         System.out.println("Internet flub");
         return;
      }
      catch(IOException ioe)
      {
         System.out.println("File flub");
         return;
      }
      
      while(input.hasNext())
      {
         String t = input.nextLine();
         BPT.insertWord(t);
         stopSet.add(t);
      }
      
      BPT.printAllEntries();
      BPT.printAllStrings();
      System.out.println();
      
      /*while(htmlIn.hasNext())
      {
         line = htmlIn.nextLine();
      }*/
      try
      {
         while((te = br.readLine()) != null)
         {
            line += te;
         }
      }
      catch(IOException i)
      {
         System.out.println("error");
      }
   
      
      String[] temp = line.split("[^a-zA-Z]");
      
      for(String s : temp)
      {
         if(!s.equals(""))
         {
            //System.out.println(s);
            fullSet.add(s.toLowerCase());
         }
      }
      
      Collections.sort(fullSet);
      
      BPT = new BPlusTree(2);
      
      boolean test = false;
      
      for(int index = 0; index < fullSet.size(); index++)
      {
         for(int jdex = 0; jdex < stopSet.size(); jdex++)
         {
            if(!fullSet.get(index).equals(stopSet.get(jdex)))
               test = true;
            else
            {
               test = false;
               break;
            }
         }
         if(test)
            BPT.insertWord(fullSet.get(index));
         
         test = false;
      }
      
      BPT.printAllEntries();
      BPT.printAllStrings();
      BPT.insertWord("test");
      BPT.printAllStrings();
      BPT.search("test");
      //System.out.println(line);
      
      /*for(int index = 0; index < 12; index++)
      {
         BPT.insertWord(sAra[index]);
      }
      System.out.println("this: " + BPT.find("this"));
      BPT.insertWord(sAra[12]);
      System.out.println("this: " + BPT.find("this"));
      BPT.insertWord(sAra[13]);
      System.out.println("this: " + BPT.find("this"));
      BPT.insertWord(sAra[14]);
      System.out.println("this: " + BPT.find("this"));
      BPT.insertWord(sAra[15]);
      System.out.println("this: " + BPT.find("this"));
      BPT.insertWord(sAra[0]);
      BPT.printAllEntries();
      BPT.printNode(10);
      BPT.printNode(13);
      BPT.printNode(1);
      BPT.search("bob");
      BPT.search("ned");
      BPT.rangeSearch("b", "jet");
      BPT.rangeSearch("jet", "b");
      System.out.println();
      BPT.printAllStrings();
      
      /*BPT = new BPlusTree(2);
      
      BPT.add("Bob");
      BPT.add("Bob");
      BPT.add("All");
      System.out.println("Bob: " + BPT.find("Bob"));
      BPT.add("Alt");
      BPT.add("Aaa");
      BPT.add("Bot");
      System.out.println("Bot: " + BPT.find("Bot"));
      BPT.add("This");
      System.out.println("Bot: " + BPT.find("Bot"));
      BPT.add("Ask");
      System.out.println("this: " + BPT.find("This"));
      BPT.add("Atom");
      //BT.display();
      
      /*char m = (char)0;
      
      String s = "";
      String b = "Bob";
      String n = "Bot";
      
      System.out.println(n.compareTo(s));
      System.out.println(n.compareTo(b));
      
      int t = b.hashCode();
      int y = n.hashCode();
      
      System.out.println(t);
      System.out.println(y);
      
      BTNode rootNode = new BTNode(0, 3, false);
      BTNode leafNode1 = new BTNode(0, 3, true);
      BTNode leafNode2 = new BTNode(0, 3, true);
      
      rootNode.setEntry(leafNode1);
      rootNode.setEntry(leafNode2);
      leafNode1.setEntry(leafNode2);
      
      rootNode.add("This");
      rootNode.add("All");
      rootNode.add("Alt");
      rootNode.add("Bot");
      rootNode.add("Bob");
      rootNode.add("Zed");*/
   }
}