// Brad Howard
// Project ACTBS

// Date
// Ver 0.99

package travelpackage;

public class CruiseDate implements DateStamp
{
   private int sDay;
   private int sMonth;
   private int sYear;
   private int sHour;
   private int sMinute;
   private int eDay;
   private int eMonth;
   private int eYear;
   private int eHour;
   private int eMinute;
   private int duration;
   boolean valid;
   
   public CruiseDate()
   {
      this.sDay = 0;
      this.sMonth = 0;
      this.sYear = 0;
      this.sHour = 0;
      this.sMinute = 0;
      this.eDay = 0;
      this.eMonth = 0;
      this.eYear = 0;
      this.eHour = 0;
      this.eMinute = 0;
      this.duration = 0;
      valid = false;
   }
   
   public CruiseDate(int sYear, int sMonth, int sDay, int sHour, int sMinute, int eYear, int eMonth, int eDay, int eHour, int eMinute)
   {
      if(checkDate(sYear, sMonth, sDay) && checkDate(eYear, eMonth, eDay))
      {
         if(sHour > -1 && sHour < 24 && sMinute > -1 && sMinute < 60 && eHour > -1 && eHour < 24 && eMinute > -1 && eMinute < 60)
         {
            this.sDay = sDay;
            this.sMonth = sMonth;
            this.sYear = sYear;
            this.sHour = sHour;
            this.sMinute = sMinute;
            this.eDay = eDay;
            this.eMonth = eMonth;
            this.eYear = eYear;
            this.eHour = eHour;
            this.eMinute = eMinute;
            this.duration = findDuration();
            
            if(eYear > sYear)
               valid = true;
            else
               valid = false;
               
            if(eYear == sYear)
               if(eMonth > sMonth)
                  valid = true;
               else
                  valid = false;
                  
            if(eYear == sYear)
               if(eMonth == sMonth)
                  if(eDay > sDay)
                     valid = true;
                  else
                     valid = false;
         }
      }
      else
      {
         this.sDay = 0;
         this.sMonth = 0;
         this.sYear = 0;
         this.sHour = 0;
         this.sMinute = 0;
         this.eDay = 0;
         this.eMonth = 0;
         this.eYear = 0;
         this.eHour = 0;
         this.eMinute = 0;
         this.duration = 0;
         System.out.println("Invalid date.");
         valid = false;
      }
   }
	
   public int getDay()
   {
      return sDay;
   }
	
   public int getMonth()
   {
      return sMonth;
   }
	
   public int getYear()
   {
      return sYear;
   }
   
   public int getHour()
   {
      return sHour;
   }
   
   public int getMinute()
   {
      return sMinute;
   }
   
   public int getEndDay()
   {
      return eDay;
   }
	
   public int getEndMonth()
   {
      return eMonth;
   }
	
   public int getEndYear()
   {
      return eYear;
   }
   
   public int getEndHour()
   {
      return eHour;
   }
   
   public int getEndMinute()
   {
      return eMinute;
   }

   public int getDuration()
   {
      return duration;
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
   
   public int findDuration()
   {
      int numberOfEDays;
      int numberOfSDays;
      int dif = 0;
      
      numberOfEDays = getNumberEndDays();
      numberOfSDays = getNumberStartDays();
      
      numberOfEDays += (eYear - 1) * 365;
      numberOfSDays += (sYear - 1) * 365;
      
      dif = numberOfEDays - numberOfSDays;
      
      return dif;
   }
   
   public int getNumberEndDays()
   {
      int numberOfEndDays = 0;
      
      switch(eMonth)
      {
         case 1:
            numberOfEndDays = eDay;
            break;
         case 2:
            numberOfEndDays = 31 + eDay;
            break;
         case 3:
            numberOfEndDays = 59 + eDay;
            break;
         case 4:
            numberOfEndDays = 90 + eDay;
            break;
         case 5:
            numberOfEndDays = 120 + eDay;
            break;
         case 6:
            numberOfEndDays = 151 + eDay;
            break;
         case 7:
            numberOfEndDays = 181 + eDay;
            break;
         case 8:
            numberOfEndDays = 212 + eDay;
            break;
         case 9:
            numberOfEndDays = 243 + eDay;
            break;
         case 10:
            numberOfEndDays = 273 + eDay;
            break;
         case 11:
            numberOfEndDays = 304 + eDay;
            break;
         case 12:
            numberOfEndDays = 334 + eDay;
            break;
      }
      
      return numberOfEndDays;
   }
   
   public int getNumberStartDays()
   {
      int numberOfStartDays = 0;
      
      switch(sMonth)
      {
         case 1:
            numberOfStartDays = sDay;
            break;
         case 2:
            numberOfStartDays = 31 + sDay;
            break;
         case 3:
            numberOfStartDays = 59 + sDay;
            break;
         case 4:
            numberOfStartDays = 90 + sDay;
            break;
         case 5:
            numberOfStartDays = 120 + sDay;
            break;
         case 6:
            numberOfStartDays = 151 + sDay;
            break;
         case 7:
            numberOfStartDays = 181 + sDay;
            break;
         case 8:
            numberOfStartDays = 212 + sDay;
            break;
         case 9:
            numberOfStartDays = 243 + sDay;
            break;
         case 10:
            numberOfStartDays = 273 + sDay;
            break;
         case 11:
            numberOfStartDays = 304 + sDay;
            break;
         case 12:
            numberOfStartDays = 334 + sDay;
            break;
      }
      
      return numberOfStartDays;
   }
   
   @Override
   public String toString()
   {
      return "" + sYear + "/" + sMonth + "/" + sDay + " @ " + sHour + ":" + (sMinute < 10 ? "0" + sMinute : sMinute);
   }
}