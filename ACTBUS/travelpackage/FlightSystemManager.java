// Brad Howard
// Project ACTBS

// System Manager - Flight
// Ver 0.99

package travelpackage;

import java.util.*;

public class FlightSystemManager implements SystemManager
{
   private List<Port> airport;
   private List<Line> airline;
   private List<Trip> flight;
   private List<Class> section;
   private List<Placement> seat;

   public FlightSystemManager()
   {
      airport = new LinkedList<Port>();
      airline = new LinkedList<Line>();
      flight = new LinkedList<Trip>();
      section = new LinkedList<Class>();
      seat = new LinkedList<Placement>();
   }
	
   public boolean createPort(String portCode)
   {
      boolean event = false;
   
      if(portCode.length() > 3)
         System.out.println("Code passed in is too long.");
      else if(portCode.length() < 3)
         System.out.println("Code passed in is too short.");
      else
      {
         if(!hasAirport(portCode))
         {
            airport.add(new Airport(portCode));
            event = true;
         }
         else
            System.out.println("That Airport already listed.");
      }
   
      return event;
   }
	
   public boolean createLine(String lineCode)
   {
      boolean event = false;
   
      if(lineCode.length() > 6)
         System.out.println("Code passed in is too long.");
      else
      {
         if(!hasAirline(lineCode))
         {
            airline.add(new Airline(lineCode));
            event = true;
         }
         else
            System.out.println("The Airline is already listed.");
      }
   
      return event;
   }
	
   public boolean createTrip(String lineName, String orig, String dest, DateStamp date, String id)
   {
      boolean event = false;
   	
      if(!date.isValid())
         return event;
   
      if(orig.equals(dest))
         System.out.println("The origin and destination are the same.");
      else
      {
         if(hasAirline(lineName))
         {
            if(hasAirport(orig) && hasAirport(dest))
            {
               if(!hasFlight(id))
               {
                  Trip temp = new Flight(lineName, orig, dest, date, id);
                  flight.add(temp);
                  getAirline(lineName).addTrip(temp);
                  event = true;
               }
               else
                  System.out.println("The flight is already listed.");
            }
            else
               System.out.println("Invalid Airport.");
         }
         else
            System.out.println("Invalid airline.");
      }
   
      return event;
   }
   
   public boolean createTrip(String lineName, String orig, String dest, List<Port> portList, DateStamp date, String id)
   {
      System.out.println("Only direct flights.");
      return true;  
   }
	
   public boolean createClass(String lineName, String tripID, int... args)
   {
      boolean event = false;
      int col = 0;
      int row = args[0];
      char layout = (char)args[1];
      int price = args[2];
      char seatClass = (char)args[3];
   
      if(layout == 'S')
         col = 3;
      else if(layout == 'M')
         col = 4;
      else if(layout == 'W')
         col = 10;
      else
         col = 4;
   
      if(seatClass == 'F')
         event = createSection(lineName, tripID, row, col, layout, price, SeatClass.FIRST);
      else if(seatClass == 'B')
         event = createSection(lineName, tripID, row, col, layout, price, SeatClass.BUSINESS);
      else
         event = createSection(lineName, tripID, row, col, layout, price, SeatClass.ECONOMY);
   
      return event;
   }
	
   private boolean createSection(String air, String flID, int row, int col, char layout, int price, SeatClass seatClass)
   {
      boolean event = false;
   
      if(row > 100)
         System.out.println("the row is over the limit.");
      else if(col > 10)
         System.out.println("the col is over the limit.");
      else if(row < 0 || col < 0)
         System.out.println("Negative number passed in.");
      else if(air.length() > 6)
         System.out.println("Code passed in is too long.");
      else
      {
         if(hasAirline(air))
         {
            if(hasFlight(flID))
            {
               if(!hasSection(air, flID, seatClass))
               {
                  Class temp = new Section(air, flID, row, col, layout, price, seatClass);
                  section.add(temp);
                  getFlight(flID).setClass(seatClass, temp);
                  event = true;
               }
               else
                  System.out.println("The section is already listed.");
            }
            else
               System.out.println("Invalid flight.");
         }
         else
            System.out.println("Invalid airline.");
      }
   
      return event;
   }
	
   public boolean bookPlacement(String lineName, String tripID, String placementClass, int spot, char place)
   {
      boolean event = false;
      Trip temp = new Flight();
      SeatClass seatClass;
   	
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
   
      if(hasAirline(lineName))
      {
         if(hasFlight(tripID))
         {
            if(hasSection(lineName, tripID, seatClass))
            {
               temp = getAirline(lineName).getTrip(tripID);
               temp.bookPlacement(seatClass, spot, place);
               event = true;
            }
            else
               System.out.println("Invalid Section.");
         }
         else
            System.out.println("Invalid Flight.");
      }
      else
         System.out.println("Invalid Airline.");
   
      return event;
   }
	
   public boolean bookPreferredPlacement(String lineName, String tripID, String placementClass, String placementType)
   {
      boolean event = false;
      SeatClass seatClass;
      Trip temp = new Flight();
      
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
      
      if(hasAirline(lineName))
      {
         if(hasFlight(tripID))
         {
            temp = getAirline(lineName).getTrip(tripID);
         }
         else
            System.out.println("Invalid Flight.");
      }
      else
         System.out.println("Invalid Airline.");
      
      if(temp.hasAvailablePlacements())
      {
         event = temp.bookPreferredPlacement(seatClass, placementType);
         
         if(!event)
            event = temp.bookAnyPlacement();
      }
      else
         System.out.println("There are no available seats for this flight.");
      
      return event;
   }
	
   public boolean bookAnyAvailablePlacement(String lineName, String tripID)
   {
      boolean event = false;
      Trip temp = new Flight();
      
      if(hasAirline(lineName))
      {
         if(hasFlight(tripID))
         {
            temp = getAirline(lineName).getTrip(tripID);
         }
         else
            System.out.println("Invalid Flight.");
      }
      else
         System.out.println("Invalid Airline.");
      
      if(temp.hasAvailablePlacements())
      {
         event = temp.bookAnyPlacement();
      }
      
      return event;
   }
	
   public boolean findAvailableTrips(String orig, String dest, DateStamp date)
   {
      boolean event = false;
      
      System.out.println("\nDisplaying available flights with Origin: " + orig + " and Destination: " + dest);
      System.out.println("Available seats are maked as O, and booked seat are marked as X.\n");
   
      for(int index = 0; index < flight.size(); index++)
      {
         if(orig.equals(flight.get(index).getOrig()) && dest.equals(flight.get(index).getDest()) && flight.get(index).hasAvailablePlacements() && date.equals(flight.get(index).getDate()))
         {
            System.out.println("Available flight(s) from " + flight.get(index).getLineName() + " on given date " + date);
            System.out.println(flight.get(index));
            event = true;
         }
      }
   
      if(!event)
      {
         System.out.println("There is no flights available.");
         event = true;
      }
   
      return event;
   }
	
   public boolean findAvailablePlacement(String orig, String dest, String placementClass, DateStamp date)
   {
      boolean event = false;
      SeatClass seatClass;
   	
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
      
      System.out.println("\nDisplaying available flights with Origin: " + orig + " and Destination: " + dest);
      System.out.println("Available seats are maked as O, and booked seat are marked as X.\n");
   
      for(int index = 0; index < flight.size(); index++)
      {
         if(orig.equals(flight.get(index).getOrig()) && dest.equals(flight.get(index).getDest()) && flight.get(index).hasAvailablePlacements() && date.equals(flight.get(index).getDate()))
         {
            System.out.println("Available flight(s) from " + flight.get(index).getLineName() + " on given date " + date);
            System.out.println(flight.get(index).getClass(seatClass));
            event = true;
         }
      }
   
      if(!event)
      {
         System.out.println("There is no flights available.");
         event = true;
      }
   
      
      return event;
   }
   
   public boolean changeClassPrice(String lineName, String tripID, String placementClass, int newPrice)
   {
      boolean event = false;
      Line temp = new Airline();
      SeatClass seatClass;
   	
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
         
      if(hasAirline(lineName))
      {
         if(hasFlight(tripID))
         {
            temp = getAirline(lineName);
         }
         else
            System.out.println("Invalid Flight.");
      }
      else
         System.out.println("Invalid Airline.");
         
      event = temp.setTripClassPrice(tripID, seatClass, newPrice);
      
      return event;
   }
   
   public boolean changeLineClassPrice(String lineName, String orig, String dest, String placementClass, int newPrice)
   {
      boolean event = false;
      
      Line temp = new Airline();
      SeatClass seatClass;
   	
      if(placementClass.equals("First"))
         seatClass = SeatClass.FIRST;
      else if(placementClass.equals("Business"))
         seatClass = SeatClass.BUSINESS;
      else
         seatClass = SeatClass.ECONOMY;
         
      if(hasAirline(lineName))
      {
         if(hasFlight(orig, dest))
         {
            temp = getAirline(lineName);
         }
         else
            System.out.println("Invalid Flight.");
      }
      else
         System.out.println("Invalid Airline.");
         
      event = temp.setTripClassPrice(orig, dest, seatClass, newPrice);
      
      return event;
   }
	
   public void displaySystemDetails()
   {
      System.out.println("\nSystem Details, Booked seat are marked as X");
      System.out.println((airport.size() == 1 ? "\nAirport:" : "\nAirports:"));
      for(Port a : airport)
         System.out.println(a.getCode());
   		
      System.out.println((airline.size() == 1 ? "\nAirline:" : "\nAirlines:"));
      for(Line l : airline)
         System.out.println(l);
   }
   
   public String outToFile()
   {
      String s = "[";
      
      for(int index = 0; index < airport.size(); index++)
      {
         if(index < airport.size() - 1)
            s += airport.get(index).getCode() + ", ";
         else
            s += airport.get(index).getCode() + "]{";
      }
      
      for(int index = 0; index < airline.size(); index++)
      {
         s += airline.get(index).getLineCode() + "[";
         for(int jdex = 0; jdex < airline.get(index).getTripListSize(); jdex++)
         {
            s += airline.get(index).getTrip(jdex).getTripNumber() + "|";
            s += airline.get(index).getTrip(jdex).getDate().getYear() + ", ";
            s += airline.get(index).getTrip(jdex).getDate().getMonth() + ", ";
            s += airline.get(index).getTrip(jdex).getDate().getDay() + ", ";
            s += airline.get(index).getTrip(jdex).getDate().getHour() + ", ";
            s += airline.get(index).getTrip(jdex).getDate().getMinute() + "|";
            s += airline.get(index).getTrip(jdex).getOrig() + "|";
            s += airline.get(index).getTrip(jdex).getDest() + "[";
            s += airline.get(index).getTrip(jdex).getClassDetails() + "]";
            if(jdex < airline.get(index).getTripListSize() - 1)
               s += ", ";
            else
               s += "]";
         }
         if(index < airline.size() - 1)
            s += ", ";
      }
      s += "}";
      
      return s;
   }
   
   public boolean hasPort(String code)
   {
      return hasAirport(code);
   }
   
   public Port getPort(String code)
   {
      return getAirport(code);
   }
   
   private boolean hasAirport(String code)
   {
      boolean test = false;
   	
      for(int index = 0; index < airport.size(); index++)
      {
         if(airport.get(index).getCode().equals(code))
            test = true;
      }
   	
      return test;
   }
	
   private boolean hasAirline(String code)
   {
      boolean test = false;
   	
      for(int index = 0; index < airline.size(); index++)
      {
         if(airline.get(index).getLineCode().equals(code))
            test = true;
      }
   	
      return test;
   }
	
   private boolean hasFlight(String id)
   {
      boolean test = false;
   	
      for(int index = 0; index < flight.size(); index++)
      {
         if(flight.get(index).getTripNumber().equals(id))
            test = true;
      }
   	
      return test;
   }
   
   private boolean hasFlight(String orig, String dest)
   {
      boolean test = false;
      
      for(int index = 0; index < flight.size(); index++)
      {
         if(flight.get(index).getOrig().equals(orig) && flight.get(index).getDest().equals(dest))
            test = true;
      }
      
      return test;
   }
	
   private boolean hasSection(String air, String flID, SeatClass seatClass)
   {
      boolean test = false;
   	     
      for(int index = 0; index < section.size(); index++)
      {
         if(!test)
            test = section.get(index).testClass(air, flID, seatClass);
      }
   	
      return test;
   }
	
   private Trip getFlight(String id)
   {
      Trip target = new Flight();
   	
      for(int index = 0; index < flight.size(); index++)
      {
         String temp = flight.get(index).getTripNumber();
         if(id.equals(temp))
            target = flight.get(index);
      }
   	
      return target;
   }
	
   private Line getAirline(String aname)
   {
      Line target = new Airline("ZZZ");
   	
      for(int index = 0; index < airline.size(); index++)
      {
         if(airline.get(index).getLineCode().equals(aname))
            target = airline.get(index);
      }
   	
      return target;
   }
   
   private Port getAirport(String code)
   {
      Port target = new Airport("ZZZ");
      
      for(int index = 0; index < airport.size(); index++)
      {
         if(airport.get(index).getCode().equals(code))
            target = airport.get(index);
      }
   	
      return target;
   }
}