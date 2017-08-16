// Brad Howard
// ACTBS

// Flight User Menu System
// Ver 0.99

package travelpackage;

import java.util.*;
import java.io.*;

public class FlightCustomerMenuSystem implements CustomerMenuSystem
{
	// Static working set
   private static Scanner kb;
   private static SystemManager sysMan;
   private static ReadFile rFile;
   private static FileInSystem fileIn;
   private static String fileName = "flightData.txt";
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
   
   public FlightCustomerMenuSystem(SystemManager sysMan, Scanner kb)
   {
      this.kb = kb;
      this.sysMan = sysMan;
      readFromFile();
   }
   
   public String menuType()
   {
      return "Flight Customer";
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
            System.out.println("\nAirline booking Customer menu: ACTBS");
            System.out.println("1: Book a Seat  2: Book any Seat 3: Book a Preferred seat");
            System.out.println("4: Find Flight  5: Find a Seat   6: Exit Menu");
            System.out.print("Options: ");
            menu = kb.nextInt();
            kb.nextLine();
         
            switch(menu)
            {
               case 1:
                  bookPlacement();
                  break;
               case 2:
                  bookAnyPlacement();
                  break;
               case 3:
                  bookPreferredPlacement();
                  break;
               case 4:
                  findTrips();
                  break;
               case 5:
                  findPlacement();
                  break;
               case 6:
                  System.out.println("Exiting Menu.");
                  dumpToFile();
                  return sysMan;
               default:
                  if(menu < 1 || menu > 7)
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
   
   public void findTrips()
   {
      while(!trip)
      {
         System.out.print("\nEnter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the date (YYYY MM DD): ");
         year = kb.nextInt();
         month = kb.nextInt();
         day = kb.nextInt();
         System.out.print("Enter the time (HH MM): ");
         hour = kb.nextInt();
         min = kb.nextInt();
               	
         trip = sysMan.findAvailableTrips(orig, dest, new FlightDate(year, month, day, hour, min));
      }
   }
	
   public void findPlacement()
   {
      while(!trip)
      {
         System.out.print("\nEnter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the date (YYYY MM DD): ");
         year = kb.nextInt();
         month = kb.nextInt();
         day = kb.nextInt();
         System.out.print("Enter the time (HH MM): ");
         hour = kb.nextInt();
         min = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the seat class(F/B/E): ");
         String temp = kb.nextLine();
         
         if(temp.equals("F"))
            trip = sysMan.findAvailablePlacement(orig, dest, "First", new FlightDate(year, month, day, hour, min));
         else if(temp.equals("B"))
            trip = sysMan.findAvailablePlacement(orig, dest, "Business", new FlightDate(year, month, day, hour, min));
         else
            trip = sysMan.findAvailablePlacement(orig, dest, "Economy", new FlightDate(year, month, day, hour, min));
      }
   }
	
   public void bookPlacement()
   {
      System.out.print("\nEnter the number of Seat to be booked: ");
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
         System.out.print("Enter the seat class(F/B/E): ");
         String temp = kb.nextLine();
         System.out.print("Enter the col(A-J): ");
         String c = kb.nextLine();
         col = c.charAt(0);
         System.out.print("Enter the row: ");
         row = kb.nextInt();
               	
         if(temp.equals("F"))
            trip = sysMan.bookPlacement(airline, flightID, "First", row, col);
         else if(temp.equals("B"))
            trip = sysMan.bookPlacement(airline, flightID, "Business", row, col);
         else
            trip = sysMan.bookPlacement(airline, flightID, "Economy", row, col);
                     
         kb.nextLine();
                  
         if(trip)
         {
            n--;
            System.out.println("The seat was booked on flight: " + flightID + " of Airline: " + airline);
         }
      }
   }
   
   public void bookPreferredPlacement()
   {
      System.out.print("\nEnter the number of Seat to be booked: ");
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
         System.out.print("Enter the seat class(F/B/E): ");
         String temp = kb.nextLine();
         System.out.print("Enter preferred seat(Window or Aisle): ");
         seatType = kb.nextLine();
         
         if(temp.equals("F"))
            trip = sysMan.bookPreferredPlacement(airline, flightID, "First", seatType);
         else if(temp.equals("B"))
            trip = sysMan.bookPreferredPlacement(airline, flightID, "Business", seatType);
         else
            trip = sysMan.bookPreferredPlacement(airline, flightID, "Economy", seatType);
         
         if(trip)
         {
            n--;
            System.out.println("The seat was booked on flight: " + flightID + " of Airline: " + airline);
         }
      }
   }
	
   public void bookAnyPlacement()
   {
      System.out.print("\nEnter the number of Seat to be booked: ");
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
         trip = sysMan.bookAnyAvailablePlacement(airline, flightID);
         
         if(trip)
         {
            n--;
            System.out.println("The seat was booked on flight: " + flightID + " of Airline: " + airline);
         }
      }
   }
   
   public void readFromFile()
   {
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