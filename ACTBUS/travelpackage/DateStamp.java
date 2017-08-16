// Brad Howard
// Project ACTBS

// Date
// Ver 0.99

package travelpackage;

public interface DateStamp
{
   public boolean isValid();
	
   public int getYear();
	
   public int getMonth();
	
   public int getDay();
	
   public int getHour();
	
   public int getMinute();
	
   public boolean checkDate(int year, int month, int day);
	
   public int checkMonth(int month);
	
   public int checkDay(int day, int month);
   
   public int getEndYear();
	
   public int getEndMonth();
	
   public int getEndDay();
	
   public int getEndHour();
	
   public int getEndMinute();
	
   public int getDuration();
	
   public int findDuration();
	
   public int getNumberEndDays();
	
   public int getNumberStartDays();
}