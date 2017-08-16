// Brad Howard
// ACTBS

// Cruise Admin Menu System
// Ver 0.99

package travelpackage;

import java.util.*;

public class CruiseAdminMenuSystem implements AdminMenuSystem
{
   // Static working set
   private static Scanner kb;
   private static SystemManager sysMan;
   // Data working set
   private DateStamp date;
   private List<Port> ports;
   private String cabinClass;
   private String orig;
   private String dest;
   private String seaport;
   private String cruiseline;
   private String shipName;
   private int menu;
   private int sYear;
   private int sMonth;
   private int sDay;
   private int sHour;
   private int sMin;
   private int eYear;
   private int eMonth;
   private int eDay;
   private int eHour;
   private int eMin;
   private int cost;
   private int numberCabin;
   private char deck;
   private char layout;
   private boolean event;
   private int numberOfPorts;
   private int n;
	
   public CruiseAdminMenuSystem(SystemManager sysMan, Scanner kb)
   {
      this.kb = kb;
      this.sysMan = sysMan;
   }
	
   public String menuType()
   {
      return "Cruise Admin";
   }
	
   public SystemManager runMenu(SystemManager sm)
   {
      if(sm != null)
         this.sysMan = sm;
      
      while(true)
      {  
         try
         {    
            event = false;
            System.out.println("\nCruiseline booking Admin menu: ACTBS");
            System.out.println("1: Add Seaport                2: Add Cruiseline");
            System.out.println("3: Add Cruise                 4: Add Class");
            System.out.println("5: Change Cabin Price by Orig/Dest");
            System.out.println("6: Change Cabin Price by Curise");
            System.out.println("7: Display System Details     8: Exit Menu");
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
                  changeLineClassPrice();
                  break;
               case 6:  
                  changeClassPrice();
                  break;
               case 7:
                  display();
                  break;
               case 8:
                  System.out.println("Exiting Menu.");
                  return sysMan;
               default:
                  if(menu < 1 || menu > 8)
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
      System.out.print("\nEnter the number of Seaport: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
         
      while(!event || (n != 0))
      {
         System.out.print("Enter the name of the Seaport: ");
         seaport = kb.nextLine();
         event = sysMan.createPort(seaport);
         
         if(event)
            n--;
      }
   }
	
   public void makeLine()
   {
      System.out.print("\nEnter the number of Cruiseline: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!event || (n != 0))
      {
         System.out.print("Enter the name of the Cruiseline: ");
         cruiseline = kb.nextLine();
         event = sysMan.createLine(cruiseline);
         
         if(event)
            n--;
      }
   }
	
   public void makeTrip()
   {
      System.out.print("\nEnter the number of Cruise: ");
      n = kb.nextInt();
      kb.nextLine();
      
      if(n == 0)
         return;
      
      while(!event || (n != 0))
      {
         System.out.print("\nEnter in the name of the Cruiseline: ");
         cruiseline = kb.nextLine();
         System.out.print("Enter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the number of ports: ");
         numberOfPorts = kb.nextInt();
         kb.nextLine();
         ports = setPorts(numberOfPorts);
         System.out.print("Enter the Name of the Ship: ");
         shipName = kb.nextLine();
         
         while(true)
         {
            System.out.print("Enter the start date(YYYY MM DD): ");
            sYear = kb.nextInt();
            sMonth = kb.nextInt();
            sDay = kb.nextInt();
            System.out.print("Enter the start time(HH MM): ");
            sHour = kb.nextInt();
            sMin = kb.nextInt();
            kb.nextLine();
            System.out.print("Enter the end date(YYYY MM DD): ");
            eYear = kb.nextInt();
            eMonth = kb.nextInt();
            eDay = kb.nextInt();
            System.out.print("Enter the end time(HH MM): ");
            eHour = kb.nextInt();
            eMin = kb.nextInt();
            kb.nextLine();
            
            date = new CruiseDate(sYear, sMonth, sDay, sHour, sMin, eYear, eMonth, eDay, eHour, eMin);
            
            if(date.getDuration() >= 21)
               System.out.println("The trip duration is to long.");
            if(!date.isValid())
               System.out.println("Date error.");
            if(date.getDuration() < 21 && date.isValid())
               break;
         }
         
         event = sysMan.createTrip(cruiseline, orig, dest, ports, date, shipName);
         
         if(event)
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
      
      while(!event || (n != 0))
      {
         System.out.print("\nEnter the name of the Cruiseline: ");
         cruiseline = kb.nextLine();
         System.out.print("Enter the Ship name: ");
         shipName = kb.nextLine();
         System.out.print("Enter the number of cabin: ");
         numberCabin = kb.nextInt();
         kb.nextLine();
         System.out.print("Enter the price of the class: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.println("Layouts: E = Deluxe Family, D = Deluxe Couple, C = Couple, F = Family");
         System.out.print("Enter the layout (E/D/C/F): ");
         String temp = kb.nextLine();
         layout = temp.charAt(0);
         System.out.print("Enter the deck(A-Z): ");
         temp = kb.nextLine();
         deck = temp.charAt(0);
                  
         event = sysMan.createClass(cruiseline, shipName, numberCabin, layout, cost, deck);
                  
         if(event)
            n--;
      }
   }
	
   public void makePlacement()
   {
      System.out.println("make Cabin");
   }
   
   public void changeClassPrice()
   {
      String temp;
      
      while(!event)
      {
         System.out.print("\nEnter the name of the Cruiseline: ");
         cruiseline = kb.nextLine();
         System.out.print("Enter the Ship name: ");
         shipName = kb.nextLine();
         System.out.print("Enter the price of the class: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.println("Layouts: E = Deluxe Family, D = Deluxe Couple, C = Couple, F = Family");
         System.out.print("Enter the layout (E/D/C/F): ");
         temp = kb.nextLine();
         
         if(temp.equals("E"))
            event = sysMan.changeClassPrice(cruiseline, shipName, "Deluxe Family", cost);
         else if(temp.equals("D"))
            event = sysMan.changeClassPrice(cruiseline, shipName, "Deluxe Couples", cost);
         else if(temp.equals("C"))
            event = sysMan.changeClassPrice(cruiseline, shipName, "Couples", cost);
         else
            event = sysMan.changeClassPrice(cruiseline, shipName, "Family", cost);
      }
   }
   
   public void changeLineClassPrice()
   {
      String temp;
      
      while(!event)
      {
         System.out.print("\nEnter the name of the Cruiseline: ");
         cruiseline = kb.nextLine();
         System.out.print("Enter the Origin: ");
         orig = kb.nextLine();
         System.out.print("Enter the Destination: ");
         dest = kb.nextLine();
         System.out.print("Enter the price of the class: ");
         cost = kb.nextInt();
         kb.nextLine();
         System.out.println("Layouts: E = Deluxe Family, D = Deluxe Couple, C = Couple, F = Family");
         System.out.print("Enter the layout (E/D/C/F): ");
         temp = kb.nextLine();
         
         if(temp.equals("E"))
            event = sysMan.changeLineClassPrice(cruiseline, orig, dest, "Deluxe Family", cost);
         else if(temp.equals("D"))
            event = sysMan.changeLineClassPrice(cruiseline, orig, dest, "Deluxe Couples", cost);
         else if(temp.equals("C"))
            event = sysMan.changeLineClassPrice(cruiseline, orig, dest, "Couples", cost);
         else
            event = sysMan.changeLineClassPrice(cruiseline, orig, dest, "Family", cost);
      }
   }
   
   public void readFromFile()
   {
      System.out.println("No Such Function.");
   }
   
   public void readFromFile(String file)
   {
      System.out.println("No Such Function.");
   }
   
   public void dumpToFile()
   {
      System.out.println("No Such Function.");
   }
   
   public void display()
   {
      sysMan.displaySystemDetails();
   }
   
   private LinkedList<Port> setPorts(int n)
   {
      LinkedList<Port> temp = new LinkedList<Port>();
      boolean event = false;
      String s;
      
      for(int index = 0; index < n; index++)
      {
         while(!event)
         {
            System.out.print("Enter in the name of the Port: ");
            s = kb.nextLine();
            event = sysMan.hasPort(s);
            if(event)
            {
               temp.add(sysMan.getPort(s));
            }
         }
         event = false;
      }
      
      return temp;
   }
   
   public SystemManager getSystemManager()
   {
      return sysMan;   
   }
}