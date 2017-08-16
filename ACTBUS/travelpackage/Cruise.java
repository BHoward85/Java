// Brad Howard
// Project ACTBS

// Curise
// Ver 0.99

package travelpackage;

import java.util.*;

public class Cruise implements Trip
{
   private String shipName;
   private String dest;
   private String orig;
   private String curiselineName;
   private DateStamp date;
   private List<Port> ports;
   private Class[] classSet = new CabinClass[4];

   public Cruise()
   {
      shipName = "SS Null";
      dest = "";
      orig = "";
      date = null;
   }

   public Cruise(String lineName, String orig, String dest, List<Port> ports, DateStamp date, String id)
   {
      shipName = id;
      this.dest = dest;
      this.orig = orig;
      this.date = date;
      
      if(ports != null)
         this.ports = ports;
      else
         this.ports = new LinkedList<Port>();
         
      curiselineName = lineName;
      classSet[0] = new CabinClass(lineName, id, "Family");
      classSet[1] = new CabinClass(lineName, id, "Deluxe Family");
      classSet[2] = new CabinClass(lineName, id, "Couples");
      classSet[3] = new CabinClass(lineName, id, "Deluxe Couples");
   }
	
   public void setClass(String cabinClass, Class section)
   {
      int sdex = findCabinClass(cabinClass);
   	
      classSet[sdex] = section;
   }
   
   public void addPort(Port port)
   {
      ports.add(port);
   }
	
   public String getDest()	
   {	
      return dest;
   }

   public String getOrig()
   {	
      return orig;
   }

   public String getLineName()
   {	
      return curiselineName;
   }

   public DateStamp getDate()
   {	
      return date;
   }

   public String getTripNumber()
   {	
      return shipName;
   }
   
   public String getListOfPorts()
   {
      String s = "";
      
      for(Port p : ports)
         s += "" + p.getCode() + "\n";
      
      return s;
   }
   
   public String getClass(String cabinClass)
   {
      return "" + classSet[findCabinClass(cabinClass)];
   }
	
   public boolean bookPlacement(String cabinClass, int... args)
   {
      boolean event = false;
      int sdex = findCabinClass(cabinClass);
      int roomNumber = args[0];
      char deck = (char)args[1];
      
      event = classSet[sdex].bookPlacement(roomNumber - 1, deck - 'A');
   	
      return event;
   }
   
   public boolean hasAvailablePlacements()
   {
      boolean test = false;
   
      for(int index = 0; index < 3; index++)
      {
         test = classSet[index].hasAvailablePlacements();
      }
   
      return test;
   }
   
   public boolean setClassPrice(String cabinClass, int newPrice)
   {
      boolean event = false;
      int sdex = findCabinClass(cabinClass);
      
      event = classSet[sdex].setPrice(newPrice);
      
      return event;
   }
	
   private int findCabinClass(String cabinClass)
   {
      int sdex = 0;
      
      for(int index = 0; index < 4; index++)
      {
         if(classSet[index].getCabinClass().equals(cabinClass))
            sdex = index;
      }
   	
      return sdex;
   }
   
   @Override
   public String toString()
   {
      String s = "";
      s += "Ship name: " + shipName;
      s += "\nOrigin: " + orig;
      s += "\nDestination: " + dest;
      s += "\nList of ports on trip:\n" + getListOfPorts();
      s += "\nCurise Line Name: " + curiselineName;
      s += "\nCurise start date: " + date.getYear() + "/" + date.getMonth() + "/" + date.getDay();
      s += "\nCurise start time: " + date.getHour() + ":" + (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute());
      s += "\nCurise end date: " + date.getEndYear() + "/" + date.getEndMonth() + "/" + date.getEndDay();
      s += "\nCurise end time: " + date.getEndHour() + ":" + (date.getEndMinute() < 10 ? "0" + date.getEndMinute() : date.getEndMinute());
      s += "\nCabin Classes:";
      s += "\nFamily cabin price: " + classSet[0].getPrice() + "\n" + classSet[0];
      s += "\nDeluxe Family cabin price: " + classSet[1].getPrice() + "\n" + classSet[1];
      s += "\nCouples cabin price: " + classSet[2].getPrice() + "\n" + classSet[2];
      s += "\nDeluxe Couples cabin price: " + classSet[3].getPrice() + "\n" + classSet[3];
      
      return s;
   }
   
   //non-functional methods
   public boolean bookPreferredPlacement(SeatClass seatClass, String placementClass)
   {
      return true;
   }
   
   public void setClass(SeatClass seatClass, Class section)
   {
      System.out.println("No such Function");
   }
   
   public boolean bookPlacement(SeatClass seatClass, int... args)
   {
      return true; 
   }
   
   public String getClass(SeatClass seatClass)
   {
      return "No such Function";
   }
   
   public boolean setClassPrice(SeatClass seatClass, int newPrice)
   {
      return true;
   }
   
   public boolean bookAnyPlacement()
   {
      return true;
   }
   
   public String getClassDetails()
   {
      return "No such function";
   }
}