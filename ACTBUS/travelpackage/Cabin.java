// Brad Howard
// Project ACTBS

// Cabin Class
// Ver 0.99

package travelpackage;

import java.util.*;

public class Cabin implements Placement
{
   private int roomNumber;
   private char deck;
   private boolean available;
	
   public Cabin(int roomNumber, char deck)
   {
      this.roomNumber = roomNumber;
      this.deck = deck;
      available = true;
   }
	
   public int getPlacementNumber()
   {
      return roomNumber;
   }
	
   public char getPlacementLocation()
   {
      return deck;
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
         System.out.println("Cabin: room number: " + (roomNumber + 1) + " on deck: " + deck + " is not availble.");
         return false;
      }
   }
	
   public boolean isAvailable()
   {
      return available;
   }
   
   @Override
   public String toString()
   {
      return "Cabin number: " + (roomNumber + 1);
   }
}