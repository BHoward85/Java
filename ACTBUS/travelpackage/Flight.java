// Brad Howard
// Project ACTBS

// Flight
// Ver 0.99

package travelpackage;

import java.util.*;

public class Flight implements Trip
{
   private String flightNumber;
   private String dest;
   private String orig;
   private String airlineName;
   private DateStamp date;
   private Class[] sectionSet = new Section[3];

   public Flight()
   {
      flightNumber = "0";
      dest = "";
      orig = "";
      date = null;
   }

   public Flight(String aname, String orig, String dest, DateStamp date, String id)
   {
      flightNumber = id;
      this.dest = dest;
      this.orig = orig;
      this.date = date;
      airlineName = aname;
      sectionSet[0] = new Section(aname, id, SeatClass.FIRST);
      sectionSet[1] = new Section(aname, id, SeatClass.BUSINESS);
      sectionSet[2] = new Section(aname, id, SeatClass.ECONOMY);
   }
	
   public void setClass(SeatClass seatClass, Class section)
   {
      int sdex = findSeatClass(seatClass);
   	
      sectionSet[sdex] = section;
   }
   
   public String getClassDetails()
   {
      String s = "";
      
      for(int index = 0; index < 3; index++)
      {
         if(!sectionSet[index].getDetails().equals("") && sectionSet[index].getPrice() != 0 && index == 0)
         {
            s += sectionSet[index].getDetails();
            if(sectionSet[index + 1].getPrice() != 0 || sectionSet[index + 2].getPrice() != 0)
               s += ", ";
         }
         else if(!sectionSet[index].getDetails().equals("") && sectionSet[index].getPrice() != 0 && index == 1)
         {
            s += sectionSet[index].getDetails();
            if(sectionSet[index + 1].getPrice() != 0)
               s += ", ";
         }
         else if(!sectionSet[index].getDetails().equals("") && sectionSet[index].getPrice() != 0)
            s += sectionSet[index].getDetails();
      }
      
      return s;
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
      return airlineName;
   }

   public DateStamp getDate()
   {	
      return date;
   }

   public String getTripNumber()
   {	
      return flightNumber;
   }
   
   public String getClass(SeatClass seatClass)
   {
      return "" + sectionSet[findSeatClass(seatClass)];
   }
	
   public boolean bookPlacement(SeatClass seatClass, int... args)
   {
      boolean event = false;
      int sdex = findSeatClass(seatClass);
      int row = args[0];
      char col = (char)args[1];
      
      event = sectionSet[sdex].bookPlacement(row - 1, col - 'A');
   	
      return event;
   }
   
   public boolean bookPreferredPlacement(SeatClass seatClass, String seatType)
   {
      boolean event = false;
      int sdex = findSeatClass(seatClass);
      
      event = sectionSet[sdex].bookPreferredPlacement(seatType);
      
      return event;
   }
   
   public boolean bookAnyPlacement()
   {
      boolean event = false;
      
      for(int index = 0; index < 3; index++)
      {
         if(!event)
            event = sectionSet[index].bookAnyPlacement();
      }
      
      return event;
   }
	
   public boolean hasAvailablePlacements()
   {
      boolean test = false;
   
      for(int index = 0; index < 3; index++)
      {
         test = sectionSet[index].hasAvailablePlacements();
      }
   
      return test;
   }
   
   public boolean setClassPrice(SeatClass seatClass, int newPrice)
   {
      boolean event = false;
      int sdex = findSeatClass(seatClass);
      
      event = sectionSet[sdex].setPrice(newPrice);
      
      return event;
   }
	
   private int findSeatClass(SeatClass seatClass)
   {
      int sdex = 0;
      
      for(int index = 0; index < 3; index++)
      {
         if(sectionSet[index].getSeatClass() == seatClass)
            sdex = index;
      }
   	
      return sdex;
   }
   
   public String getListOfPorts()
   {
      return "No Such function";
   }
   
   public void addPort(Port port)
   {
      System.out.println("No Such function");
   }
   
   public void setClass(String cabinClass, Class section)
   {
      System.out.println("No Such function");
   }
   
   public boolean bookPlacement(String cabinClass, int... args)
   {
      System.out.println("No Such function");
      return true;
   }
   
   public String getClass(String cabinClass)
   {
      return "No Such function";
   }
   
   public boolean setClassPrice(String cabinClass, int newPrice)
   {
      return true;
   }
   
   @Override
   public String toString()
   {
      String s = "";
      s += "Flight Number: " + flightNumber;
      s += "\nOrigin: " + orig;
      s += "\nDestination: " + dest;
      s += "\nAirline Name: " + airlineName;
      s += "\nFlight date: " + date.getYear() + "/" + date.getMonth() + "/" + date.getDay();
      s += "\nFlight time: " + date.getHour() + ":" + (date.getMinute() < 10 ? "0" + date.getMinute() : date.getMinute());
      s += "\nSections:";
      s += "\nFirst class: seat price: " + sectionSet[0].getPrice() + "\n" + sectionSet[0];
      s += "\nBusiness class: seat price: " + sectionSet[1].getPrice() + "\n" + sectionSet[1];
      s += "\nEconomy class: seat price: " + sectionSet[2].getPrice() + "\n" + sectionSet[2];
      
      return s;
   }
}