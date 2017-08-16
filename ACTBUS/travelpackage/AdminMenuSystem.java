// Brad Howard
// ACTBS

// Admin Menu System
// Ver 0.99

package travelpackage;

public interface AdminMenuSystem extends MenuSystem
{
   public void makePort();
	
   public void makeLine();
	
   public void makeTrip();
	
   public void makeClass();
	
   public void makePlacement();
	
   public void display();
   
   public void dumpToFile();
   
   public void changeClassPrice();
   
   public void changeLineClassPrice();
}