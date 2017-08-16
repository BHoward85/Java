// Brad Howard
// Project ACTBS

// Seat
// Ver 0.99

// bottom of the Airline-Flight-Section-Seat hierarchy.

package travelpackage;

import java.util.*;

public class Seat implements Placement
{
   private int row;
   private char col;
   private boolean available;
	
   public Seat(int row, char col)
   {
      this.row = row;
      this.col = col;
      this.available = true;
   }
	
   public int getPlacementNumber()
   {	
      return row;
   }
	
   public char getPlacementLocation()
   {	
      return col;
   }
	
   public boolean bookPlacement()
   {
      if(available)
      {
         available = false;
         return true;
      }
      else
      {
         System.out.println("Seat in row: " + (row + 1) + ", col: " + col + " is not available.");
         return false;
      }
   }
	
   public boolean isAvailable()
   {	
      return available;
   }
}