// Brad Howard
// Project 1: Buffer Manager

import java.util.*;
import java.io.*;

public class BufMgr
{
   private Frame[] bufferpool;
   private BufHashTbl pageTable;
   private LinkedList<Frame> usedpool;
	
   public BufMgr(int numberOfFrames)
   {
      bufferpool = new Frame[numberOfFrames];
      pageTable = new BufHashTbl();
      usedpool = new LinkedList<Frame>();
   }
	
   public void pin(int pageNumber)
   {
      int frameNumber = pageTable.lookup(pageNumber);
      
      if(frameNumber != -1)
      {
         bufferpool[frameNumber].upPinCount();
         checkIfUnused(pageNumber);
      }
      else
         System.out.println("\nThe pages is not loaded in the buffer.");
   }
   
   private void checkIfUnused(int pageNumber)
   {
      for(int index = 0; index < usedpool.size(); index++)
      {
         if(pageNumber == usedpool.get(index).getPageNumber())
            usedpool.remove(index);
      }
   }
	
   public void unpin(int pageNumber)
   {
      int frameNumber = pageTable.lookup(pageNumber);
      
      if(frameNumber != -1)
      {
         if(bufferpool[frameNumber].getPinCount() != 0)
         {
            bufferpool[frameNumber].downPinCount();
            if(bufferpool[frameNumber].getPinCount() == 0)
               usedpool.add(bufferpool[frameNumber]);
         }
      }
      else
         System.out.println("\nThe page is not in the buffer.");
         
   }
   
   public void lru(Frame frame)
   {
      if(frame.getFrameNumber() == -1 && pageTable.lookup(frame.getPageNumber()) == -1)
      {
         if(hasOpenFrames())
         {
            place(frame);
         }
         else if(usedpool.size() != 0)
         {
            replace(frame, usedpool.get(0).getFrameNumber());
         }
         else
         {
            System.out.println("\nThe buffer is full, try again later.");
         }
      }
   }
   
   private void replace(Frame frame, int frameNumber)
   {
      if(usedpool.getFirst().getPinCount() == 0)
      {
         if(usedpool.getFirst().isDirty())
         {
            writePage(bufferpool[frameNumber].getPageNumber());
            bufferpool[frameNumber] = frame;
            bufferpool[frameNumber].setFrameNumber(frameNumber);
            pageTable.remove(usedpool.getFirst().getPageNumber(), usedpool.getFirst().getFrameNumber());
            pageTable.insert(frame.getPageNumber(), frame.getFrameNumber());
            usedpool.remove();
         }
         else // page to be replaced has not changed
         {
            bufferpool[frameNumber] = frame;
            bufferpool[frameNumber].setFrameNumber(frameNumber);
            pageTable.remove(usedpool.getFirst().getPageNumber(), usedpool.getFirst().getFrameNumber());
            pageTable.insert(frame.getPageNumber(), frame.getFrameNumber());
            usedpool.remove();
         }
      }
   }
   
   private void place(Frame frame)
   {
      for(int index = 0; index < bufferpool.length; index++)
      {
         if(bufferpool[index] == null)
         {
            bufferpool[index] = frame;
            frame.setFrameNumber(index);
            pageTable.insert(frame.getPageNumber(), frame.getFrameNumber());
            break;
         }
      }
   }
	
   public void createPage(int pageNumber)
   {
      PrintStream output;
      String fileName = pageNumber + ".txt";
      
      try
      {
         output = new PrintStream(fileName);
      }
      catch(IOException ioe)
      {
         System.out.println("File Error");
         return;
      }
      
      output.println("This is page " + pageNumber);
      output.close();
   }
	
   public void readPage(int pageNumber)
   {
      Scanner input;
      String fileName = pageNumber + ".txt";
      String temp = "";
      Frame frame;
      
      try
      {
         File name = new File(fileName);
         input = new Scanner(name);
      }
      catch(IOException ioe)
      {
         System.out.println("File Error");
         return;
      }
      
      while(input.hasNext())
      {
         temp += (input.nextLine() + "\r\n");
      }
      
      frame = new Frame(temp);
      frame.setPageNumber(pageNumber);
      input.close();
      lru(frame);
   }
   
   public void writeFrame(int frameNumber)
   {
      int pageNumber = -1;
      
      if(bufferpool[frameNumber] != null)
      {
         pageNumber = bufferpool[frameNumber].getPageNumber();
         writePage(pageNumber);
      }
   }
   
   public void writePage(int pageNumber)
   {
      PrintStream output;
      String temp = pageNumber + ".txt";
      int frameNumber = pageTable.lookup(pageNumber);
      
      if(frameNumber != -1 && bufferpool[frameNumber].isDirty())
      {
         try
         {
            output = new PrintStream(temp);
         }
         catch(IOException ioe)
         {
            System.out.println("File Error");
            return;
         }
      
         if(bufferpool[frameNumber].isDirty())
         {
            output.print(bufferpool[frameNumber] + "\r\n");
         }
         output.close();
      }
   }
	
   public void addToPage(int pageNumber)
   {
      Scanner kb = new Scanner(System.in);
      String s = "";
      int frameNumber = pageTable.lookup(pageNumber);
      
      System.out.print("\nEnter content to be add: ");
      s = kb.nextLine();
      
      if(frameNumber != -1)
      {
         bufferpool[frameNumber].addToContents(s);
         bufferpool[frameNumber].mark();
      }
      else
         System.out.println("\nThe page is not in the buffer.");
   }
   
   private boolean hasOpenFrames()
   {
      for(int index = 0; index < bufferpool.length; index++)
      {
         if(bufferpool[index] == null)
         {
            return true;
         }
      }
      
      return false;
   }
   
   public boolean hasPage(int pageNumber)
   {
      boolean test = false;
      int frameNumber = pageTable.lookup(pageNumber);
      
      if(frameNumber != -1)
         test = true;
      
      return test;
   }
   
   public void printFrame(int pageNumber)
   {
      int frameNumber = pageTable.lookup(pageNumber);
      
      if(frameNumber != -1)
         System.out.println("\n" + bufferpool[frameNumber]);
   }
   
   public int getNumberOfFrames()
   {
      return bufferpool.length;
   }
}