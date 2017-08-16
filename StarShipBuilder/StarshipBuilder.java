// Brad Howard
// Assignment 5: Starship Builder

// Note the out put has lines with \r

import java.util.*;
import java.io.*;

public class StarshipBuilder
{
   public static void main(String[] args)
   {
   	// ship input list
      String fileName = "buildList.txt";
      String outFile = "Ships.txt";
   	
   	// input charts
      String inputWeaponChart = null;
      String inputDefensiveChart = null;
      String inputPowerChart = null;
      String inputOtherChart = null;
   	
   	// file in/out
      Scanner fileIn = null;
      Scanner weaponChartIn = null;
      Scanner defensiveChartIn = null;
      Scanner powerChartIn = null;
      Scanner otherChartIn = null;
      PrintStream fileOut = null;
   	
   	// Ship data
      String shipName;
      String shipClass;
      String viability;
      double shieldCost;
      double movementCost;
      double lifeSupportCost;
      int classValue = 0;
      int crew;
      int massLimit;
      int massTotal;
      int powerTotal;
      int powerLimit;
      int hullPoints;
      int totalDefencePoints;
      int numberOfShips;
   	
   	// Temp data set
      String temp;
      String[] tempAra;
   	
   	// ship printable charts
      String weaponChart = null;
      String defensiveChart = null;
      String powerChart = null;
      String otherChart = null;
   	
   	// file handling
      fileIn = fileOpen(new File(fileName));
      try
      {
         fileOut = new PrintStream(outFile);
      }
      catch(IOException fnf)
      {
         System.out.println("Error 01");
         return;
      }
   	
      numberOfShips = fileIn.nextInt();
      fileIn.nextLine();
   	
   	// main loop
      while(numberOfShips > 0)
      {
         fileIn.nextLine();
      	// Ship name and class
         shipName = fileIn.nextLine();
         shipClass = fileIn.nextLine();
      	
      	// crew size
         temp = fileIn.nextLine();
         tempAra = temp.split("([: ])");
         crew = Integer.parseInt(tempAra[2]);
         classValue = findClassValue(shipClass);
      	
      	// mass limit
         temp = fileIn.nextLine();
         tempAra = temp.split("([: ])");
         massLimit = Integer.parseInt(tempAra[3]);
         
      	// weapon set
         temp = fileIn.nextLine();
         tempAra = temp.split("([- ])");
         inputWeaponChart = tempAra[3];
         
      	// defensive set
         temp = fileIn.nextLine();
         tempAra = temp.split("([- ])");
         inputDefensiveChart = tempAra[3];
         
      	// power set
         temp = fileIn.nextLine();
         tempAra = temp.split("([- ])");
         inputPowerChart = tempAra[3];
         
      	// other set
         temp = fileIn.nextLine();
         tempAra = temp.split("([- ])");
         inputOtherChart = tempAra[3];
         
         // print ship name and class
         fileOut.println(shipName);
         fileOut.println(shipClass);
            
         // file in for all charts
         weaponChartIn = getChart(inputWeaponChart, "Weapons", fileOut);
         defensiveChartIn = getChart(inputDefensiveChart, "Defensive", fileOut);
         powerChartIn = getChart(inputPowerChart, "Power", fileOut);
         otherChartIn = getChart(inputOtherChart, "Other", fileOut);
         
         if(weaponChartIn != null && defensiveChartIn != null && powerChartIn != null && otherChartIn != null)
         {      
         	// find mass total
            massTotal = findTotalMass(weaponChartIn, defensiveChartIn, powerChartIn, otherChartIn);
            
         	// find power limit
            powerLimit = findPowerLimit(powerChartIn);
            
         	// find power cost
            powerTotal = findPowerCost(weaponChartIn, defensiveChartIn, otherChartIn);
            
         	// build printable charts
            weaponChart = buildChart(weaponChartIn);
            defensiveChart = buildChart(defensiveChartIn);
            powerChart = buildChart(powerChartIn);
            otherChart = buildChart(otherChartIn);
         
         	// find the total defence points to find the total hull points
            totalDefencePoints = findTotalDefencePoint(defensiveChart);
            
         	// find the Life support cost
            lifeSupportCost = findLSC(crew, massLimit);
            
         	// find the movement cost
            movementCost = findMovementCost(massLimit, classValue);
            
         	// find the shield cost
            shieldCost = findShieldCost(powerLimit, massLimit);
            
         	// find the total hull point
            hullPoints = findHullPoint(massLimit, totalDefencePoints);
            
            // find the viability of the ship
            viability = findOutIfShipIsOK(massTotal, massLimit, powerTotal, powerLimit);
            
            // output the ship chart
            fileOut.println("Crew: " + crew);
            fileOut.printf("LSC: %2.2f\n", lifeSupportCost);
            fileOut.println("Mass total/limit: " + massTotal + "/" + massLimit);
            fileOut.println("Power cost/limit: " + powerTotal + "/" + powerLimit + "\n");
            
            fileOut.println("Weapons:");
            fileOut.println(weaponChart);
            
            fileOut.println("Defensive Systems:");
            fileOut.println("Hull Points: " + hullPoints);
            fileOut.printf("Shield cost: %2.2f\n", shieldCost);
            fileOut.println(defensiveChart);
            
            fileOut.println("Power Systems:");
            fileOut.printf("Movement Cost: %2.2f\n", movementCost);
            fileOut.println(powerChart);
            
            fileOut.println("Other Systems:");
            fileOut.println(otherChart);
            
            fileOut.println(viability + "\n");
         }
         else // if there is one chart input was a type mismatch
            fileOut.println("Ship is incomplete design\n");
         
         numberOfShips--;
      }
   }
	
   public static int findClassValue(String shipClass)
   {
      String temp = shipClass;
      String[] tempAra = temp.split("([: ])");
      switch(tempAra[2])
      {
         case "Battleship":
            return 1;
         case "Battlecruiser":
            return 2;
         case "Carrier":
            return 2;
         case "Cruiser":
            return 3;
         case "Destroyer":
            return 4;
         case "Frigate":
            return 5;
         default:
            return -1;
      }
   }
   
   public static Scanner getChart(String chart, String type, PrintStream fileOut)
   {
      Scanner fin = fileOpen(new File("./Charts/" + chart + ".txt"));
      String temp = fin.nextLine();
      String[] tempAra = temp.split("([: ])");
   
   	// checking if the chart is the right type
      if(!tempAra[3].equals(type))
      {
         fileOut.println("input chart for " + type + " systems was a " + tempAra[3] + " system");
         return null;
      }
   
      return fin;
   }
   
   public static Scanner fileOpen(File inputFile)
   {
      Scanner fin;
      
      try
      {
         fin = new Scanner(inputFile);
      }
      catch(IOException ioe)
      {
         System.out.println("File not found, Error 01");
         return null;
      }
      
      return fin;
   }
   
   public static int findTotalMass(Scanner win, Scanner din, Scanner pin, Scanner oin)
   {
      String w = win.nextLine();
      String d = din.nextLine();
      String p = pin.nextLine();
      String o = oin.nextLine();
      String[] wSet = w.split("([: ])");
      String[] dSet = d.split("([: ])");
      String[] pSet = p.split("([: ])");
      String[] oSet = o.split("([: ])");
      
      return Integer.parseInt(wSet[3]) + Integer.parseInt(dSet[3]) + Integer.parseInt(pSet[3]) + Integer.parseInt(oSet[3]);
   }
   
   public static int findPowerLimit(Scanner pin)
   {
      String p = pin.nextLine();
      String[] pSet = p.split("([: ])");
      
      return Integer.parseInt(pSet[3]);
   }
   
   public static int findPowerCost(Scanner win, Scanner din, Scanner oin)
   {
      String w = win.nextLine();
      String d = din.nextLine();
      String o = oin.nextLine();
      String[] wSet = w.split("([: ])");
      String[] dSet = d.split("([: ])");
      String[] oSet = o.split("([: ])");
      
      return Integer.parseInt(wSet[3]) + Integer.parseInt(dSet[3]) +  Integer.parseInt(oSet[3]);
   }
   
   public static String buildChart(Scanner in)
   {
      String temp = "";
      
      while(in.hasNext())
      {
         temp += in.nextLine() + "\n";
      }
      
      return temp;
   }
   
   public static double findLSC(double crew, double massLimit)
   {
      return (crew / (massLimit / 10));
   }
   
   public static double findShieldCost(double totalPower, double massLimit)
   {
      return (totalPower / (massLimit / 100));
   }
   
   public static double findMovementCost(double massLimit, double classValue)
   {
      return ((massLimit / 1000) / classValue);
   }
   
   public static int findHullPoint(int massLimit, int totalDefencePoint)
   {
      return ((massLimit / 10) - totalDefencePoint);
   }
   
   public static int findTotalDefencePoint(String dChart)
   {
      String[] tempAra = dChart.split("([\n])");
      int defex = 0;
      
      for(int index = 0; index < tempAra.length; index++)
      {
         String[] line = tempAra[index].split("([: ])");
         defex += Integer.parseInt(line[3]);
      }
      
      return defex;
   }
   
   public static String findOutIfShipIsOK(int massTotal, int massLimit, int powerTotal, int powerLimit)
   {
      String temp = "The ship is a ";
      
      if(massTotal <= massLimit)
      {
         double mt = (double)massTotal;
         double ml = (double)massLimit;
         double d = mt / ml;
         if(d >= 0.75)
         {
            if(powerTotal <= powerLimit)
            {
               temp += "balanced design";
            }
            else
            {
               temp += "underpower design";
            }
         }
         else if(powerTotal <= powerLimit)
         {
            temp += "under engineered design";
         }
         else // if both under 75% mass limit and underpowered
         {
            temp += "underpower and under engineered design";
         }
      }
      else if(powerTotal <= powerLimit)
      {
         temp += "over loaded design";
      }
      else // if both over loaded and underpowered
      {
         temp += "underpower and over loaded design";
      }
      
      return temp;
   }
}