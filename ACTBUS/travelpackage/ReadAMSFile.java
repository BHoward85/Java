// Brad Howard
// Project ACTBS

// Read AMS File
// Ver 0.99

// Note was going to refactor the readInAirlines,
// but don't have the time to do so

package travelpackage;

import java.util.*;
import java.io.*;

public class ReadAMSFile implements ReadFile
{
   private FileInSystem FIS;
   private SystemManager sysMan;
	
   public ReadAMSFile(FileInSystem FIS)
   {
      this.FIS = FIS;
      sysMan = new FlightSystemManager();
   }
   
   public ReadAMSFile(FileInSystem FIS, SystemManager sm)
   {
      this.FIS = FIS;
      sysMan = sm;
   }
   
   public void readFile()
   {
      readInAirports();
   }
	
   public void readInAirports()
   {
      char tempChar;
      String tempStr = "";
      int n = 0;
   	
      while(FIS.hasNext())
      {
         tempChar = FIS.read();
      	
         if(tempChar == '[')
         {
            while(tempChar != ']')
            {
               tempChar = FIS.read();
               if(tempChar != ','  && tempChar != ' ')
               {
                  tempStr += tempChar;
                  n++;
                  if(n == 3)
                     sysMan.createPort(tempStr);
               }
               else
               {
                  n = 0;
                  tempStr = "";
                  tempChar = FIS.read(); // push to next char
               }
            }
         }
         
         if(tempChar == ']')
            readInAirlines(tempChar);
      }
   }
   
   public void readInAirlines(char temp)
   {
      char tempChar = temp;
      String tempStr = "";
      String airline = "";
      String flightID = "";
      String orig = "";
      String dest = "";
      int day;
      int month;
      int year;
      int hour;
      int min;
      char seatClass;
      char layout;
      int row;
      int cost;
      int n = 0;
      
      while(FIS.hasNext())
      {
         tempChar = FIS.read();
         
         if(tempChar == '{')
         {
            while(tempChar != '}')
            {
               //tempChar = FIS.read();
               
               while(tempChar != '[') // airline
               {
                  tempChar = FIS.read();
                  if(tempChar != '[' && tempChar != ' ')
                     airline += tempChar;
               }
               sysMan.createLine(airline);
               //tempChar = FIS.read();
               
               if(tempChar == '[') // flight
               {
                  while(tempChar != ']')
                  {
                     tempChar = FIS.read();
                     while(tempChar != '|')
                     {
                        if(tempChar != '|')
                        {
                           if(tempChar != ' ')
                              flightID += tempChar;
                           tempChar = FIS.read();
                        }
                     }
                     
                     tempChar = FIS.read();
                     while(tempChar != ',')
                     {
                        if(tempChar != ',')
                        {
                           if(tempChar != ' ')
                              tempStr += tempChar;
                           tempChar = FIS.read();
                        }
                     }
                     year = Integer.parseInt(tempStr);
                     tempStr = "";
                     
                     tempChar = FIS.read();
                     while(tempChar != ',')
                     {
                        tempChar = FIS.read();
                        if(tempChar != ',' && tempChar != ' ')
                           tempStr += tempChar;
                     }
                     month = Integer.parseInt(tempStr);
                     tempStr = "";
                     
                     tempChar = FIS.read();
                     while(tempChar != ',')
                     {
                        tempChar = FIS.read();
                        if(tempChar != ',' && tempChar != ' ')
                           tempStr += tempChar;
                     }
                     day = Integer.parseInt(tempStr);
                     tempStr = "";
                     
                     tempChar = FIS.read();
                     while(tempChar != ',')
                     {
                        tempChar = FIS.read();
                        if(tempChar != ',' && tempChar != ' ')
                           tempStr += tempChar;
                     }
                     hour = Integer.parseInt(tempStr);
                     tempStr = "";
                     
                     tempChar = FIS.read();
                     while(tempChar != '|')
                     {
                        tempChar = FIS.read();
                        if(tempChar != '|' && tempChar != ' ')
                           tempStr += tempChar;
                     }
                     min = Integer.parseInt(tempStr);
                     tempStr = "";
                     
                     tempChar = FIS.read();
                     while(tempChar != '|')
                     {
                        if(tempChar != '|')
                        {
                           if(tempChar != ' ')
                              orig += tempChar;
                           tempChar = FIS.read();
                        }
                     }
                     
                     tempChar = FIS.read();
                     while(tempChar != '[')
                     {;
                        if(tempChar != '[')
                        {
                           if(tempChar != ' ')
                              dest += tempChar;
                           tempChar = FIS.read();
                        }
                     }
                     
                     sysMan.createTrip(airline, orig, dest, new FlightDate(year, month, day, hour, min), flightID);
                     
                     if(tempChar == '[') // Section
                     {
                        while(tempChar != ']')
                        {
                           seatClass = FIS.read();
                           FIS.read();
                              
                           while(tempChar != ':')
                           {
                              tempChar = FIS.read();
                              if(tempChar != ':' && tempChar != ' ')
                                 tempStr += tempChar;
                           }
                           cost = Integer.parseInt(tempStr);
                           tempStr = "";
                              
                           layout = FIS.read();
                           FIS.read();
                              
                           while(tempChar != ',' && tempChar != ']')
                           {
                              tempChar = FIS.read();
                              if(tempChar != ',' && tempChar != ']' && tempChar != ' ')
                                 tempStr += tempChar;
                           }
                           row = Integer.parseInt(tempStr);
                           tempStr = "";
                              
                           sysMan.createClass(airline, flightID, row, layout, cost, seatClass);
                           if(tempChar != ']' && tempChar == ',')
                              FIS.read();
                        }// end while section
                        tempChar = FIS.read();
                        flightID = "";
                        orig = "";
                        dest = "";  
                     }
                  } // end while
               } // end flight
               airline = "";
               tempChar = FIS.read();
               if(tempChar != '}')
                  tempChar = FIS.read();
            }
         }
      }
   }
}