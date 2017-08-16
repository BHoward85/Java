// Brad Howard
// Project ACTBS

// Cruiseline
// Ver 0.99

package travelpackage;

import java.util.*;

public class Cruiseline implements Line
{
   private String cruiselineName;
   private List<Trip> cruiseList = new LinkedList<Trip>();

   public Cruiseline()
   {
      cruiselineName = "ZZZ";
   }

   public Cruiseline(String code)
   {
      cruiselineName = code;
   }
	
   public String getLineCode()
   {
      return cruiselineName;
   }
	
   public void addTrip(Trip trip)
   {
      if(!trip.getTripNumber().equals(getTrip(trip.getTripNumber())))
         cruiseList.add(trip);
   }
	
   public Trip getTrip(String tripNumber)
   {
      Trip target = new Cruise();
   	
      for(int index = 0; index < cruiseList.size(); index++)
      {
         if(tripNumber.equals(cruiseList.get(index).getTripNumber()))
            target = cruiseList.get(index);
      }
   	
      return target;
   }
	
   public Trip getTrip(String orig, String dest)
   {
      Trip target = new Cruise();
   	
      for(int index = 0; index < cruiseList.size(); index++)
      {
         if(orig.equals(cruiseList.get(index).getOrig()) && dest.equals(cruiseList.get(index).getDest()))
            target = cruiseList.get(index);
      }
   	
      return target;
   }
	
   public Trip getTrip(int index)
   {
      return cruiseList.get(index);
   }
	
   public int getTripListSize()
   {
      return cruiseList.size();
   }
	
   public boolean setTripClassPrice(String TripID, String cabinClass, int newPrice)
   {
      boolean event = false;
      
      event = getTrip(TripID).setClassPrice(cabinClass, newPrice);
   
      return event;
   }
	
   public boolean setTripClassPrice(String orig, String dest, String cabinClass, int newPrice)
   {
      boolean event = false;
   
      for(int index = 0; index < cruiseList.size(); index++)
      {
         if(cruiseList.get(index).getOrig().equals(orig) && cruiseList.get(index).getDest().equals(dest))
            event = cruiseList.get(index).setClassPrice(cabinClass, newPrice);
      }
   
      return event;
   }
	
   @Override
   public String toString()
   {
      String s = cruiselineName;
   
      s += "\nCurise" + (cruiseList.size() == 1 ? ":" : "s:");
   
      for(Trip t : cruiseList)
      {
         s += "\n" + t;
      }
   
      return s;
   }
   
   //Non-functional Methods
   public boolean setTripClassPrice(String orig, String dest, SeatClass seatClass, int newPrice)
   {
      return true;
   }
   
   public boolean setTripClassPrice(String TripID, SeatClass seatClass, int newPrice)
   {
      return true;
   }
   
   
}