// Brad Howard
// Project ACTBS

// Line
// Ver 0.99

package travelpackage;

public interface Line
{
   public String getLineCode();
	
   public void addTrip(Trip trip);
	
   public Trip getTrip(String tripNumber);
	
   public Trip getTrip(String orig, String dest);
	
   public Trip getTrip(int index);
	
   public int getTripListSize();
	
   public boolean setTripClassPrice(String TripID, SeatClass seatClass, int newPrice);
	
   public boolean setTripClassPrice(String TripID, String cabinClass, int newPrice);
	
   public boolean setTripClassPrice(String orig, String dest, SeatClass seatClass, int newPrice);
	
   public boolean setTripClassPrice(String orig, String dest, String cabinClass, int newPrice);
}