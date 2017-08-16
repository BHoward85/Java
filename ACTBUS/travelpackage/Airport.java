// Brad Howard
// Project ACTBS

// Airport
// Ver 0.99

package travelpackage;

public class Airport implements Port
{
   private String airportCode;
	
   public Airport(String code)
   {
      airportCode = code;
   }
	
   public String getCode()
   {
      return airportCode;
   }
   
   @Override
   public String toString()
   {
      return airportCode;
   }
}