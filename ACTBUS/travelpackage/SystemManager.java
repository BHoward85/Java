// Brad Howard
// Project ACTBS

// System Manager - Main Interface
// Ver 0.99

package travelpackage;

import java.util.*;

public interface SystemManager
{
   public boolean createPort(String portCode);
	
   public boolean createLine(String lineCode);
	
   public boolean createTrip(String lineName, String orig, String dest, DateStamp date, String id);
   
   public boolean createTrip(String lineName, String orig, String dest, List<Port> portList, DateStamp date, String id);
	
   public boolean createClass(String lineName, String tripID, int... args);
	
   public boolean bookPlacement(String lineName, String tripID, String placementClass, int spot, char place);
	
   public boolean bookPreferredPlacement(String lineName, String tripID, String placementClass, String placementType);
	
   public boolean bookAnyAvailablePlacement(String lineName, String tripID);
	
   public boolean findAvailableTrips(String orig, String dest, DateStamp date);
	
   public boolean findAvailablePlacement(String orig, String dest, String placementClass, DateStamp date);
	
   public boolean changeClassPrice(String lineName, String tripID, String placementClass, int newPrice);
	
   public boolean changeLineClassPrice(String lineName, String orig, String dest, String placementClass, int newPrice);
   
   public boolean hasPort(String code);
   
   public Port getPort(String code);
	
   public void displaySystemDetails();
	
   public String outToFile();
}