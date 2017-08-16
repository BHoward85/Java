// Brad Howard
// ACTBS

// Flight Admin Menu System
// Ver 0.99

package travelpackage;

import java.util.*;
import java.io.*;

public class FlightAdminMenuSystem implements AdminMenuSystem
{
   // Static working set
   private static Scanner kb;
   private static SystemManager sysMan;
   private static ReadFile rFile;
   private static FileInSystem fileIn;
   private static String fileName;
   // Data working set
   private String seatClass;
   private String orig;
   private String dest;
   private String airport;
   private String airline;
   private String flightID;
   private String seatType;
   private char col = ' ';
   private char layout = ' ';
   private char seat = ' ';
   private int menu;
   private int year;
   private int month;
   private int day;
   private int hour;
   private int min;
   private int cost;
   private int row;
   private boolean trip;
   private int colSize;
   private int n;
	
		
   public FlightAdminMenuSystem(SystemManager sysMan, Scanner kb)
   {
      this.kb = kb;
      this.sysMan = sysMan;
   }
   
   public String menuType()
   {
      return "Flight Admin";
   }
	
   public SystemManager runMenu(SystemManager sm)
   {
      if(sm != null)
         this.sysMan = sm;
      
      while(true)
      {
         try
         { 
            trip = false;
            System.out.println("\nAirline booking Admin menu: ACTBS");
            System.out.println("1: Add Airport                2: Add Airline");
            System.out.println("3: Add Flight                 4: Add Section");
            System.out.println("5: Change Section Price by Orig/Dest");
            System.out.println("6: Change Section Price by Flight");
            System.out.println("7: Read from file             8: Dump to file");
            System.out.println("9: Display System Details     10: Exit Menu");
            System.out.print("Options: ");
            menu = kb.nextInt();
            kb.nextLine();
         
            switch(menu)
            {
               case 1:
                  makePort();
                  break;
               case 2:
                  makeLine();
                  break;
               case 3:
                  makeTrip();
                  break;
               case 4:
                  makeClass();
                  break;
               case 5:
                  changeClassPrice();
                  break;
               case 6:
                  changeLineClassPrice();
                  break;
               case 7:
                  readFromFile();
                  break;
               case 8:
                  dumpToFile();
                  break;
               case 9:
                  display();
                  break;
               case 10:
                  System.out.println("Exiting menu.");
                  dumpToFile();
                  return sysMan;
               default:
                  if(menu < 1 || menu > 10)
                     System.out.println("out side range.");
                  break;
            }
         }
         catch(Exception e)
         {
            System.out.println("System error\nRestarting menu");
            kb.nextLine();
         }
      }
   }
	
   public void makePort()
   {
      System.out.print("\nEnter the number of airport: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!trip || (n != 0))
      {
         System.out.print("Enter the name of the Airport: ");
         airport = kb.nextLine();
         trip = sysMan.createPort(airport);
         
         if(trip)
            n--;
      }
   }
	
   public void makeLine()
   {
      System.out.print("\nEnter the number of airline: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!trip || (n != 0))
      {
         System.out.print("Enter the name of the Airline: ");
         airline = kb.nextLine();
         trip = sysMan.createLine(airline);
                  
         if(trip)
            n--;
      }
   }
	
   public void makeTrip()
   {
      System.out.print("\nEnter the number of Flight: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!trip || (n != 0))
      {
         System.out.print("\nEnter the name of the Airline: ");
         airline = kb.nextLine();
         System.out.print("Enter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the date(YYYY MM DD): ");
         year = kb.nextInt();
         month = kb.nextInt();
         day = kb.nextInt();
         System.out.print("Enter the time(HH MM): ");
         hour = kb.nextInt();
         min = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the Flight ID: ");
         flightID = kb.nextLine();
         trip = sysMan.createTrip(airline, orig, dest, new FlightDate(year, month, day, hour, min), flightID);
                  
         if(trip)
            n--;
      }
   }
	
   public void makeClass()
   {
      System.out.print("\nEnter the number of Section: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!trip || (n != 0))
      {
         System.out.print("\nEnter the name of the Airline: ");
         airline = kb.nextLine();
         System.out.print("Enter the Flight ID: ");
         flightID = kb.nextLine();
         System.out.print("Enter the number of row: ");
         row = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the price of the section: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the layout (S/M/W): ");
         String temp = kb.nextLine();
         layout = temp.charAt(0);
         System.out.print("Enter the seat class(F/B/E): ");
         temp = kb.nextLine();
         seat = temp.charAt(0);
                  
         trip = sysMan.createClass(airline, flightID, row, layout, cost, seat);
                  
         if(trip)
            n--;
      }
   }
   
   public void changeClassPrice()
   {
      String temp;
      
      while(!trip)
      {
         System.out.print("\nEnter the name of the Airline: ");
         airline = kb.nextLine();
         System.out.print("Enter the Flight ID: ");
         flightID = kb.nextLine();
         System.out.print("Enter the price of the section: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the seat class(F/B/E): ");
         temp = kb.nextLine();
         seat = temp.charAt(0);
         
         if(temp.equals("F"))
            trip = sysMan.changeClassPrice(airline, flightID, "First", cost);
         else if(temp.equals("B"))
            trip = sysMan.changeClassPrice(airline, flightID, "Business", cost);
         else
            trip = sysMan.changeClassPrice(airline, flightID, "Economy", cost);
      }
   }
   
   public void changeLineClassPrice()
   {
      String temp;
      
      while(!trip)
      {
         System.out.print("\nEnter the name of the Airline: ");
         airline = kb.nextLine();
         System.out.print("Enter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the price of the section: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the seat class(F/B/E): ");
         temp = kb.nextLine();
         seat = temp.charAt(0);
         
         if(temp.equals("F"))
            trip = sysMan.changeLineClassPrice(airline, orig, dest, "First", cost);
         else if(temp.equals("B"))
            trip = sysMan.changeLineClassPrice(airline, orig, dest, "Business", cost);
         else
            trip = sysMan.changeLineClassPrice(airline, orig, dest, "Economy", cost);
      }
   
   }
	
   public void makePlacement()
   {
      System.out.println("make Seat");
   }
   
   public void display()
   {
      sysMan.displaySystemDetails();
   }
   
   public void readFromFile()
   {
      System.out.print("\nEnter the name of the file: ");
      fileName = kb.nextLine();
         
      fileIn = new FileBufferIn(fileName);
      rFile = new ReadAMSFile(fileIn, sysMan = new FlightSystemManager());
      rFile.readFile();
   }
   
   public void readFromFile(String file)
   {
      fileIn = new FileBufferIn(file);
      rFile = new ReadAMSFile(fileIn, sysMan = new FlightSystemManager());
      rFile.readFile();
   }
   
   public void dumpToFile()
   {
      PrintStream out;
      String temp = "";
      
      try
      {
         out = new PrintStream("flightData.txt");
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("File error.");
         return;
      }
      
      temp = sysMan.outToFile();
      
      out.println(temp);
   }
   
   public SystemManager getSystemManager()
   {
      return sysMan;   
   }
}