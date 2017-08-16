// Brad Howard
// Project ACTBS

// System Manager - Cruise
// Ver 0.99

package travelpackage;

import java.util.*;

public class CruiseSystemManager implements SystemManager
{
   private List<Port> seaports;
   private List<Line> cruiselines;
   private List<Trip> cruises;
   private CabinClass cabinClass;
   private Placement cabin;
	
   public CruiseSystemManager()
   {
      seaports = new LinkedList<Port>();
      cruiselines = new LinkedList<Line>();
      cruises = new LinkedList<Trip>();
   }
	
   public boolean createPort(String portCode)
   {
      boolean event = false;
   	
      if(!hasSeaport(portCode))
      {
         seaports.add(new Seaport(portCode));
         event = true;
      }
      else
         System.out.println("That Seaport is already listed.");
   	
      return event;
   }
	
   public boolean createLine(String lineCode)
   {
      boolean event = false;
   
      if(!hasCruiseline(lineCode))
      {
         cruiselines.add(new Cruiseline(lineCode));
         event = true;
      }
      else
         System.out.println("The Curiseline is already listed.");
   
      return event;
   }
   
   public boolean createTrip(String lineName, String orig, String dest, DateStamp date, String id)
   {
      System.out.println("No such method.");
      return true;
   }
   
   public boolean createTrip(String lineName, String orig, String dest, List<Port> listPorts, DateStamp date, String id)
   {
      boolean event = false;
            
      if(!date.isValid())
         return event;
   
      if(orig.equals(dest))
         System.out.println("The origin and destination are the same.");
      else
      {
         if(hasCruiseline(lineName))
         {
            if(hasSeaport(orig) && hasSeaport(dest))
            {
               if(!hasCruise(id))
               {
                  Trip temp = new Cruise(lineName, orig, dest, listPorts, date, id);
                  cruises.add(temp);
                  getCruiseline(lineName).addTrip(temp);
                  event = true;
               }
               else
                  System.out.println("The Curise is already listed.");
            }
            else
               System.out.println("Invalid Seaport.");
         }
         else
            System.out.println("Invalid Curiseline.");
      }
   
      return event;
   }
   
   public boolean createClass(String lineName, String tripID, int... args)
   {
      boolean event = false;
      int numberOfRooms = args[0];
      char layout = (char)args[1];
      int price = args[2];
      int deck = args[3];
      String cabinClass;
   
      if(layout == 'E') // Deluxe Family
         cabinClass = "Deluxe Family";
      else if(layout == 'D') // Deluxe Couple
         cabinClass = "Deluxe Couples";
      else if(layout == 'C') // Couple
         cabinClass = "Couples";
      else // F = family
         cabinClass = "Family";
   
      event = createSection(lineName, tripID, deck, numberOfRooms, layout, price, cabinClass);
   
      return event;
   }
   
   private boolean createSection(String lineName, String tripID, int deck, int numberOfRooms, char layout, int price, String cabinSection)
   {
      boolean event = false;
   
      if(deck < 0 || numberOfRooms < 0)
         System.out.println("Negative number passed in.");
      else
      {
         if(hasCruiseline(lineName))
         {
            if(hasCruise(tripID))
            {
               if(!hasCabinClass(lineName, tripID, cabinSection))
               {
                  cabinClass = new CabinClass(lineName, tripID, numberOfRooms, deck, layout, price, cabinSection);
                  getCruise(tripID).setClass(cabinSection, cabinClass);
                  event = true;
               }
               else
                  System.out.println("The Cabin Class is already listed.");
            }
            else
               System.out.println("Invalid Curise.");
         }
         else
            System.out.println("Invalid Curiseline.");
      }
   
      return event;
   }
   
   public boolean bookPlacement(String lineName, String tripID, String placementClass, int spot, char place)
   {
      return true;
   }
   
   public boolean bookPreferredPlacement(String lineName, String tripID, String placementClass, String placementType)
   {
      return true;
   }
   
   public boolean bookAnyAvailablePlacement(String lineName, String tripID)
   {
      return true;
   }
   
   public boolean findAvailableTrips(String orig, String dest, DateStamp date)
   {
      return true;
   }
   
   public boolean findAvailablePlacement(String orig, String dest, String placementClass, DateStamp date)
   {
      return true;
   }
   
   public boolean changeClassPrice(String lineName, String tripID, String placementClass, int newPrice)
   {
      boolean event = false;
      Line temp = new Cruiseline();
         
      if(hasCruiseline(lineName))
      {
         if(hasCruise(tripID))
         {
            temp = getCruiseline(lineName);
         }
         else
            System.out.println("Invalid Curise.");
      }
      else
         System.out.println("Invalid Curiseline.");
         
      event = temp.setTripClassPrice(tripID, placementClass, newPrice);
      
      return event;
   }
   
   public boolean changeLineClassPrice(String lineName, String orig, String dest, String placementClass, int newPrice)
   {
      boolean event = false;
      Line temp = new Cruiseline();
         
      if(hasCruiseline(lineName))
      {
         if(hasCurise(orig, dest))
         {
            temp = getCruiseline(lineName);
         }
         else
            System.out.println("Invalid Curise.");
      }
      else
         System.out.println("Invalid Curiseline.");
         
      event = temp.setTripClassPrice(orig, dest, placementClass, newPrice);
   
      
      return event;
   }
   
   public void displaySystemDetails()
   {
      System.out.println("\nSystem Details, Booked cabins are marked as X");
      System.out.println((seaports.size() == 1 ? "\nSeaport:" : "\nSeaports:"));
      for(Port s : seaports)
         System.out.println(s.getCode());
   
      System.out.println((cruiselines.size() == 1 ? "\nCuriseline:" : "\nCuriselines:"));
      for(Line l : cruiselines)
         System.out.println(l);
   }
   
   public String outToFile()
   {
      return "No Such Function.";
   }
   
   public boolean hasPort(String code)
   {
      return hasSeaport(code);
   }
   
   public Port getPort(String code)
   {
      return getSeaport(code);
   }
   
   private boolean hasSeaport(String code)
   {
      boolean test = false;
   	
      for(int index = 0; index < seaports.size(); index++)
      {
         if(seaports.get(index).getCode().equals(code))
            test = true;
      }
   	
      return test;
   }
   
   private boolean hasCruiseline(String code)
   {
      boolean test = false;
   	
      for(int index = 0; index < cruiselines.size(); index++)
      {
         if(cruiselines.get(index).getLineCode().equals(code))
            test = true;
      }
   	
      return test;
   }
   
   private boolean hasCruise(String id)
   {
      boolean test = false;
   	
      for(int index = 0; index < cruises.size(); index++)
      {
         if(cruises.get(index).getTripNumber().equals(id))
            test = true;
      }
   	
      return test;
   }
   
   private boolean hasCurise(String orig, String dest)
   {
      boolean test = false;
      
      for(int index = 0; index < cruises.size(); index++)
      {
         if(cruises.get(index).getOrig().equals(orig) && cruises.get(index).getDest().equals(dest))
            test = true;
      }
      
      return test;
   }
   
   private boolean hasCabinClass(String lineName, String tripID, String cabinSection)
   {
      boolean test = false;
      
      for(int index = 0; index < cruiselines.size(); index++)
      {
         if(cruiselines.get(index).getTrip(tripID).getClass(cabinSection).equals(cabinSection))
            test = true;
      }
      
      return test;
   }
   
   private Trip getCruise(String id)
   {
      Trip target = new Cruise();
   	
      for(int index = 0; index < cruises.size(); index++)
      {
         String temp = cruises.get(index).getTripNumber();
         if(id.equals(temp))
            target = cruises.get(index);
      }
   	
      return target;
   }
   
   private Line getCruiseline(String lineName)
   {
      Line target = new Cruiseline();
   	
      for(int index = 0; index < cruiselines.size(); index++)
      {
         if(cruiselines.get(index).getLineCode().equals(lineName))
            target = cruiselines.get(index);
      }
   	
      return target;
   }
   
   private Port getSeaport(String code)
   {
      Port target = new Seaport("ZZZ");
      
      for(int index = 0; index < seaports.size(); index++)
      {
         if(seaports.get(index).getCode().equals(code))
            target = seaports.get(index);
      }
   	
      return target;
   }
}