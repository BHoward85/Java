// Brad Howard
// Project ACTBS

// Class
// Ver 0.99

package travelpackage;

public interface Class
{
   public boolean bookPlacement(int... args);
	
   public boolean bookPreferredPlacement(String placementType);
	
   public boolean bookAnyPlacement();
		
   public SeatClass getSeatClass();
	
   public String getCabinClass();
	
   public String getLine();
	
   public char getLayout();
	
   public String getID();
	
   public int getPrice();
	
   public boolean setPrice(int newPrice);
	
   public boolean hasAvailablePlacements();
	
   public String getDetails();
	
   public boolean testClass(String lineName, String tripID, SeatClass seatClass);
	
   public boolean testClass(String lineName, String tripID, String cabinClass);
}