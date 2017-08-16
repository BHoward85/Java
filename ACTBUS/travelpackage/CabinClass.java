// Brad Howard
// Project ACTBS

// Cabin Class
// Ver 0.99

package travelpackage;

import java.util.*;

public class CabinClass implements Class
{
   private String cruiseline;
   private String shipName;
   private String cabinClass;
   private int deck;
   private int numberOfRooms;
   private int price;
   private char layout;
   private Placement[] cabinSet;
	
   public CabinClass(String lineName, String tripID, String cabinClass)
   {
      cruiseline = lineName;
      shipName = tripID;
      this.cabinClass = cabinClass;
      cabinSet = new Cabin[20];
      setCabins();
      layout = 'F';
   }
	
   public CabinClass(String lineName, String tripID, int numberOfRooms, int deck, char layout, int price, String cabinClass)
   {
      cruiseline = lineName;
      shipName = tripID;
      this.deck = deck;
      this.numberOfRooms = numberOfRooms;
      this.price = price;
      this.cabinClass = cabinClass;
      this.layout = layout;
      cabinSet = new Cabin[numberOfRooms];
      setCabins();
   }
	
   private void setCabins()
   {
      for(int index = 0; index < cabinSet.length; index++)
      {
         cabinSet[index] = new Cabin(index, (char)('A' + deck));
      }
   }
	
   public boolean testClass(String lineName, String tripID, String cabinClass)
   {
      boolean test = false;
   	
      if(cruiseline.equals(lineName))
         if(shipName.equals(tripID))
            if(this.cabinClass.equals(cabinClass))
               test = true;
   	
      return test;
   }
	
   public boolean bookCabin(int roomNumber, int deck)
   {
      return cabinSet[roomNumber].bookPlacement();
   }
   
   public boolean hasAvailablePlacements()
   {
      boolean test = false;
      
      for(int index = 0; index < cabinSet.length; index++)
      {
         if(!test)
            test = cabinSet[index].isAvailable();
         else
            break;
      }
   
      return test;
   }
   
   public String getLine()
   {
      return cruiseline;
   }
   
   public String getID()
   {
      return shipName;
   }
   
   public char getLayout()
   {
      return layout;
   }
   
   public boolean setPrice(int newPrice)
   {
      this.price = newPrice;
   
      return true;
   }
   
   public String getCabinClass()
   {
      return cabinClass;
   }
   
   public int getPrice()
   {
      return price;
   }
   
   @Override
   public String toString()
   {
      String s = "";
      
      s += "Deck: " + (char)deck + "\n";
      
      for(int index = 0; index < cabinSet.length; index++)
      {
         s += cabinSet[index] + " : " + (cabinSet[index].isAvailable() ? "O" : "X") + "\n";
      }
      
      return s;
   }
   
   //non-functional methods
   public boolean testClass(String lineName, String tripID, SeatClass seatClass)
   {
      return true;
   }
   
   public String getDetails()
   {
      return "No such function";
   }
   
   public SeatClass getSeatClass()
   {
      return SeatClass.FIRST;
   }
   
   public boolean bookAnyPlacement()
   {
      return true;
   }
   
   public boolean bookPreferredPlacement(String placementType)
   {
      return true;
   }
   
   public boolean bookPlacement(int... args)
   {
      return true;
   }
}