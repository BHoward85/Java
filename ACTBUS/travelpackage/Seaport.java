// Brad Howard
// Project ACTBS

// Seaport
// Ver 0.99

package travelpackage;

public class Seaport implements Port
{
   private String name;
	
   public Seaport(String code)
   {
      name = code;
   }
	
   public String getCode()
   {
      return name;
   }
	
   @Override
   public String toString()
   {
      return name;
   }
}