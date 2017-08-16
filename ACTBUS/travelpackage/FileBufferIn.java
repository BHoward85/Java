// Brad Howard
// Project ACTBS

// FileSystem
// Ver 0.99

package travelpackage;

import java.util.*;
import java.io.*;

public class FileBufferIn implements FileInSystem
{
   private BufferedReader reader;
   private String fileIn;
	
   public FileBufferIn(String fileIn)
   {
      this.fileIn = fileIn;
      
      try
      {
         reader = new BufferedReader(new FileReader(fileIn));
      }
      catch(IOException ioe)
      {
         System.out.println("File in error.");
         reader = null;
      }
   }
	
   public char read()
   {
      char c;
      int test;
   	
      try
      {
         test = reader.read();
         
         if(test != -1)
            c = (char)test;
         else
         {
            System.out.println("End of File");
            return ' ';
         }
      }
      catch(IOException ioe)
      {
         System.out.println("File Reader read error.");
         c = ' ';
      }
   	
      return c;
   }
	
   public String readLine()
   {
      String s;
   	
      try
      {
         s = reader.readLine();
      }
      catch(IOException ioe)
      {
         System.out.println("File Reader readline error.");
         s = " ";
      }
   	
      return s;
   }
	
   public boolean hasNext()
   {
      boolean test = false;
   	
      try
      {
         test = reader.ready();
      }
      catch(IOException ioe)
      {
         System.out.println("File Reader ready error.");
      }
      catch(NullPointerException npe)
      {
         System.out.println("No such file.");
      }
   	
      return test;
   }
	
   public String getFileIn()
   {
      return fileIn;
   }
	
   public void setFileIn(String fileName)
   {
      this.fileIn = fileIn;
      
      try
      {
         reader = new BufferedReader(new FileReader(fileIn));
      }
      catch(IOException ioe)
      {
         System.out.println("File in error.");
         reader = null;
      }
   }
}