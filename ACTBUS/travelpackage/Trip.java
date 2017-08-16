// Brad Howard
// Project ACTBS

// Trip
// Ver 0.99

package travelpackage;

public interface Trip
{
   public String getClassDetails();
	
   public String getDest();
	
   public String getOrig();
	
   public String getLineName();
	
   public DateStamp getDate();
	
   public String getTripNumber();
	
   public boolean bookAnyPlacement();
	
   public boolean hasAvailablePlacements();
	
   public boolean setClassPrice(SeatClass seatClass, int newPrice);
	
   public boolean setClassPrice(String cabinClass, int newPrice);
	
   public String getClass(SeatClass seatClass);
	
   public String getClass(String cabinClass);
	
   public boolean bookPlacement(SeatClass seatClass, int... args);
	
   public boolean bookPlacement(String cabinClass, int... args);
	
   public void setClass(SeatClass seatClass, Class section);
	
   public void setClass(String cabinClass, Class section);
	
   public boolean bookPreferredPlacement(SeatClass seatClass, String placementClass);
	
   public void addPort(Port port);
	
   public String getListOfPorts();
}