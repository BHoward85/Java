// Brad Howard
// Project ACTBS

// Section
// Ver 0.99

// middle of the Airline-Flight-Section-Seat hierarchy.

package travelpackage;

import java.util.*;

public class Section implements Class
{
   private String airline;
   private String flightID;
   private int col;
   private int row;
   private int price;
   private char layout;
   private SeatClass seatClass;
   private Placement[][] seatSet;
	
   public Section(String aname, String id, SeatClass seat)
   {
      airline = aname;
      flightID = id;
      seatSet = new Seat[100][10];
      layout = 'W';
      seatClass = seat;
   }
	
   public Section(String air, String flID, int row, int col, char layout, int price, SeatClass seatClass)
   {
      airline = air;
      flightID = flID;
      this.col = col;
      this.row = row;
      this.layout = layout;
      this.seatClass = seatClass;
      this.price = price;
      seatSet = new Seat[row][col];
      setSeats();
   }
	
   private void setSeats()
   {
      for(int index = 0; index < seatSet.length; index++)
      {
         for(int jdex = 0; jdex < seatSet[index].length; jdex++)
         {
            seatSet[index][jdex] = new Seat(index, (char)('A' + jdex));
         }
      }
   }
	
   public boolean testClass(String air, String flID, SeatClass seatClass)
   {
      boolean test = false;
      
      if(airline.equals(air))
         if(flightID.equals(flID))
            if(this.seatClass == seatClass)
               test = true;
   	
      return test;
   }
   
   public boolean testClass(String air, String flID, String placementClass)
   {
      boolean test = false;
      SeatClass sc = SeatClass.ECONOMY;
      
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
      
      test = testClass(air, flID, sc);
      
      return test;
   }
	
   public boolean bookPlacement(int... args)
   {
      int row = args[0];
      int col = args[1];
      
      return seatSet[row][col].bookPlacement();
   }
   
   public boolean bookPreferredPlacement(String seatType)
   {
      boolean event = false;
      
      if(seatType.equals("Window"))
         event = bookWindowSeat();
      else if(seatType.equals("Aisle"))
         event = bookAisleSeat();
         
      return event;
   }
   
   private boolean bookAisleSeat()
   {
      boolean event = false;
      
      if(layout == 'S')
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][0].isAvailable() && !event)
               event = bookPlacement(index, 0);
            else if(seatSet[index][1].isAvailable() && !event)
               event = bookPlacement(index, 1);
         }
      }
      else if(layout == 'M')
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][1].isAvailable() && !event)
               event = bookPlacement(index, 1);
            else if(seatSet[index][2].isAvailable() && !event)
               event = bookPlacement(index, 2);
         }
      }
      else
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][2].isAvailable() && !event)
               event = bookPlacement(index, 2);
            else if(seatSet[index][3].isAvailable() && !event)
               event = bookPlacement(index, 3);
            else if(seatSet[index][6].isAvailable() && !event)
               event = bookPlacement(index, 6);
            else if(seatSet[index][7].isAvailable() && !event)
               event = bookPlacement(index, 7);
         }
      }
      
      return event;
   }
   
   private boolean bookWindowSeat()
   {
      boolean event = false;
      
      if(layout == 'S')
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][0].isAvailable() && !event)
               event = bookPlacement(index, 0);
            else if(seatSet[index][2].isAvailable() && !event)
               event = bookPlacement(index, 2);
         }
      }
      else if(layout == 'M')
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][0].isAvailable() && !event)
               event = bookPlacement(index, 0);
            else if(seatSet[index][3].isAvailable() && !event)
               event = bookPlacement(index, 3);
         }
      }
      else
      {
         for(int index = 0; index < row; index++)
         {
            if(seatSet[index][0].isAvailable() && !event)
               event = bookPlacement(index, 0);
            else if(seatSet[index][9].isAvailable() && !event)
               event = bookPlacement(index, 9);
         }
      }
      
      return event;
   }
   
   public boolean bookAnyPlacement()
   {
      boolean event = false;
      
      for(int index = 0; index < row; index++)
      {
         for(int jdex = 0; jdex < col; jdex++)
         {
            if(seatSet[index][jdex].isAvailable() && !event)
               event = bookPlacement(index, jdex);
         }
      }
      
      return event;
   }
	
   public SeatClass getSeatClass()	
   {	
      return seatClass;
   }
	
   public String getLine()	
   {	
      return airline;
   }
   
   public char getLayout()
   {
      return layout;
   }
	
   public String getID()	
   {	
      return flightID;
   }
   
   public int getPrice()
   {
      return price;
   }
   
   public boolean setPrice(int newPrice)
   {
      this.price = newPrice;
      
      return true;
   }
	
   public boolean hasAvailablePlacements()
   {
      boolean test = false;
   	
      for(int index = 0; index < row; index++)
      {
         for(int jdex = 0; jdex < col; jdex++)
         {
            if(!test)
               test = seatSet[index][jdex].isAvailable();
            else
               break;
         }
      }
   	
      return test;
   }
   
   public String getDetails()
   {
      String s = "";
      char sClass;
      
      if(seatClass == SeatClass.FIRST)
         sClass = 'F';
      else if(seatClass == SeatClass.BUSINESS)
         sClass = 'B';
      else
         sClass = 'E';
      
      s += sClass + ":" + price + ":" + layout + ":" + row;
      
      return s;
   }
   
   @Override
   public String toString()
   {
      String s = "   ";
      
      for(int index = 0; index < col; index++)
      {
         if(layout == 'S' && index == 1)
            s += "_";
         else if(layout == 'M' && index == 2)
            s += "_";
         else if(layout == 'W' && (index == 3 || index == 7))
            s += "_";
         s += (char)('A' + index);
      }
      
      s += "\n";
      
      for(int index = 0; index < row; index++)
      {
         for(int jdex = 0; jdex < col; jdex++)
         {
            if(jdex == 0)
            {
               if((index + 1) < 10)
                  s += " " + (index + 1) + "(";
               else
                  s += (index + 1) + "(";
            }
            else if(layout == 'S' && jdex == 1)
               s += "_";
            else if(layout == 'M' && jdex == 2)
               s += "_";
            else if(layout == 'W' && (jdex == 3 || jdex == 7))
               s += "_";
            
            s += seatSet[index][jdex].isAvailable() ? "O" : "X";
         }
         s += ")";
         s += "\n";
      }
      
      return s;
   }
   
   //Non-functional method
   public String getCabinClass()
   {
      return "No such function";
   }
}