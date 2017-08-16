// Brad Howard
// Project ACTBS

// Airline
// Ver 0.99

package travelpackage;

import java.util.*;

public class Airline implements Line
{
   private String airlineCode;
   private List<Trip> flightList = new LinkedList<Trip>();
	
   public Airline()
   {
      airlineCode = "ZZZ";
   }
   
   public Airline(String code)
   {
      airlineCode = code;
   }
	
   public String getLineCode()
   {	
      return airlineCode;
   }
	
   public void addTrip(Trip trip)
   {
      if(!trip.getTripNumber().equals(getTrip(trip.getTripNumber())))
         flightList.add(trip);
   }
	
   public Trip getTrip(String tripNumber)
   {
      Trip target = new Flight();
   	
      for(int index = 0; index < flightList.size(); index++)
      {
         if(tripNumber.equals(flightList.get(index).getTripNumber()))
            target = flightList.get(index);
      }
   	
      return target;
   }
   
   public Trip getTrip(String orig, String dest)
   {
      Trip target = new Flight();
   	
      for(int index = 0; index < flightList.size(); index++)
      {
         if(orig.equals(flightList.get(index).getOrig()) && dest.equals(flightList.get(index).getDest()))
            target = flightList.get(index);
      }
      
      return target;
   }
   
   public Trip getTrip(int index)
   {
      return flightList.get(index);
   }
   
   public int getTripListSize()
   {
      return flightList.size();
   }
   
   public boolean setTripClassPrice(String tripID, SeatClass seatClass, int newPrice)
   {
      boolean event = false;
      
      event = getTrip(tripID).setClassPrice(seatClass, newPrice);
      
      return event;
   }
   
   public boolean setTripClassPrice(String orig, String dest, SeatClass seatClass, int newPrice)
   {
      boolean event = false;
      
      for(int index = 0; index < flightList.size(); index++)
      {
         if(flightList.get(index).getOrig().equals(orig) && flightList.get(index).getDest().equals(dest))
            event = flightList.get(index).setClassPrice(seatClass, newPrice);
      }
      
      return event;
   }
   
   public boolean setTripClassPrice(String TripID, String cabinClass, int newPrice)
   {
      return true;
   }
   
   public boolean setTripClassPrice(String orig, String dest, String cabinClass, int newPrice)
   {
      return true;
   }
   
   @Override
   public String toString()
   {
      String s = airlineCode;
      
      s += "\nFlight" + (flightList.size() == 1 ? ":" : "s:");
      
      for(Trip t : flightList)
      {
         s += "\n" + t;
      }
      
      return s;
   }
}