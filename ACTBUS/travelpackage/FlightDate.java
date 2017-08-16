// Brad Howard
// Project ACTBS

// Date
// Ver 0.99

package travelpackage;

public class FlightDate implements DateStamp
{
   private int day;
   private int month;
   private int year;
   private int hour;
   private int minute;
   boolean valid;
   
   public FlightDate()
   {
      this.day = 0;
      this.month = 0;
      this.year = 0;
      this.hour = 0;
      this.minute = 0;
      valid = false;
   }

   public FlightDate(int year, int month, int day, int hour, int minute)
   {
      if(checkDate(year, month, day) && hour > -1 && hour < 24 && minute > -1 && minute < 60)
      {
         this.day = day;
         this.month = month;
         this.year = year;
         this.hour = hour;
         this.minute = minute;
         valid = true;
      }
      else
      {
         this.day = 0;
         this.month = 0;
         this.year = 0;
         this.hour = 0;
         this.minute = 0;
         System.out.println("Invalid date.");
         valid = false;
      }
   }
	
   public int getDay()
   {
      return day;
   }
	
   public int getMonth()
   {
      return month;
   }
	
   public int getYear()
   {
      return year;
   }
   
   public int getHour()
   {
      return hour;
   }
   
   public int getMinute()
   {
      return minute;
   }
   
   public boolean isValid()
   {
      return valid;
   }
   
   @Override
   public boolean equals(Object obj)
   {
      if(obj.getClass().getSimpleName().equals(this.getClass().getSimpleName()))
      {
         DateStamp that = (DateStamp)obj;
      	
         if(this.getYear() == that.getYear())
            if(this.getMonth() == that.getMonth())
               if(this.getDay() == that.getDay())
                  if(this.getHour() == that.getHour())
                     if(this.getMinute() == that.getMinute())
                        return true;
      }
      return false;
   }


   public boolean checkDate(int year, int month, int day)
   {
      boolean test = false;
   
      if(checkMonth(month) != 0)
         if(checkDay(day, month) != 0)
            if(year > 0)
               test = true;
   
      return test;
   }

   public int checkMonth(int month)
   {
      if(month > 0 && month < 13)
         return month;
   
      System.out.println("Invalid month.");
      return 0;
   }

   public int checkDay(int day, int month)
   {
      switch(month)
      {
         case 2:
            if(day > 0 && day < 29)
               return day;
         case 4:
         case 6:
         case 9:
         case 11:
            if(day > 0 && day < 31)
               return day;
         case 1:
         case 3:
         case 5:
         case 7:
         case 8:
         case 10:
         case 12:
            if(day > 0 && day < 32)
               return day;
         default:
            System.out.println("Invalid day.");
            return 0;
      }
   }
   
   @Override
   public String toString()
   {
      return "" + year + "/" + month + "/" + day + " @ " + hour + ":" + (minute < 10 ? "0" + minute : minute);
   }
   
   public int getEndYear()
   {
      return 0;
   }
	
   public int getEndMonth()
   {
      return 0;
   }
	
   public int getEndDay()
   {
      return 0;
   }
	
   public int getEndHour()
   {
      return 0;
   }
	
   public int getEndMinute()
   {
      return 0;
   }
	
   public int getDuration()
   {
      return 0;
   }
	
   public int findDuration()
   {
      return 0;
   }
	
   public int getNumberEndDays()
   {
      return 0;
   }
	
   public int getNumberStartDays()
   {
      return 0;
   }
}