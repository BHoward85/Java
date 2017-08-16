// Brad Howard
// Project 1: Frame Object

import java.util.*;

public class BufMgrTester
{
   private static BufMgr BM;
   public static void main(String[] args)
   {
      Scanner kb = new Scanner(System.in);
      int numberOfPage = 0;
      int userInput = 0;
      int page = 0;
   	
      if(args.length == 1)
         BM = new BufMgr(Integer.parseInt(args[0]));
      else
         BM = new BufMgr(4);
   	
      while(true)
      {
         System.out.println("\n1: Create pages   2: Request a page");
         System.out.println("3: Update a page  4: Relinquish a page");
         System.out.print("-1: Quit\nUser input: ");
         userInput = kb.nextInt();
      	
         switch(userInput)
         {
            case 1:
               System.out.print("\nEnter the number of pages to be made\nNumber: ");
               numberOfPage = kb.nextInt();
               createPage(numberOfPage);
               break;
            case 2:
               System.out.print("\nEnter a page number to be read\nPage number: ");
               page = kb.nextInt();
               request(page);
               break;
            case 3:
               System.out.print("\nEnter a page number to be changed\nPage number: ");
               page = kb.nextInt();
               update(page);
               break;
            case 4:
               System.out.print("\nEnter a page number to be relinquish\nPage number: ");
               page = kb.nextInt();
               relinquish(page);
               break;
            case -1:
               writeAll(BM.getNumberOfFrames());
               System.out.println("\nExiting system.");
               return;
            default:
               System.out.println("\nMenu option not available.");
               break;
         }
      }
   }
	
   public static void createPage(int numberOfPages)
   {
      for(int index = 0; index < numberOfPages; index++)
      {
         BM.createPage(index);
      }
      System.out.printf("\nNumber of pages created: %d %s\n", numberOfPages, numberOfPages == 1 ? "page" : "pages");
   }
	
   public static void request(int pageNumber)
   {
      BM.readPage(pageNumber);
      
      if(BM.hasPage(pageNumber))
      {
         BM.pin(pageNumber);
         BM.printFrame(pageNumber);
      }
   }
	
   public static void update(int pageNumber)
   {
      if(BM.hasPage(pageNumber))
      {
         BM.printFrame(pageNumber);
         BM.addToPage(pageNumber);
      }
      else
         System.out.println("\nThe page is not loaded in the buffer.");
   }
	
   public static void relinquish(int pageNumber)
   {
      BM.unpin(pageNumber);
   }
   
   public static void writeAll(int numberOfFrames)
   {
      for(int index = 0; index < numberOfFrames; index++)
      {
         BM.writeFrame(index);
      }
   }
}