// Brad Howard
// Project ACTBS

// Placement
// Ver 0.99

package travelpackage;

public interface Placement
{
   public int getPlacementNumber();
	
   public char getPlacementLocation();
	
   public boolean bookPlacement();
	
   public boolean isAvailable();
}