// Brad Howard
// ACTBS

// Main Menu for ACTBS
// Ver 0.99

//Extra Credit: Flight Customer UI only.

import java.util.*;
import travelpackage.*;

public class ACTBS
{
   private static Scanner kb = new Scanner(System.in);
   private static MenuManager menuMan = new MenuManager();
   private static SystemManager[] sysManSet = {new CruiseSystemManager(), new FlightSystemManager()};
   private static String menuName = "";
   
   public static void main(String... args)
   {
      System.out.println("Starting systems");
      
      menuMan.addMenu(new CruiseAdminMenuSystem(sysManSet[0], kb));
      menuMan.addMenu(new FlightCustomerMenuSystem(sysManSet[1], kb));
      menuMan.addMenu(new FlightAdminMenuSystem(sysManSet[1], kb));
      
      System.out.println("Menus loaded, runing main menu.");
      
      runMainMenu();
   }
   
   private static void runMainMenu()
   {
      
      menuMan.getMenu("Flight Customer").readFromFile("flightData.txt");
      menuMan.getMenu("Flight Admin").readFromFile("flightData.txt");
      sysManSet[1] = menuMan.getMenu("Flight Customer").getSystemManager();
      
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("~ ~                  Welcome to ACTBS                     ~ ~");
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Ver 0.99, sorry the Customer menu for Cruise is not abailble.");
      
      while(true)
      {
         System.out.println("\nMenus:");
         System.out.println("Cruise Admin, Cruise Customer");
         System.out.println("Flight Admin, Flight Customer");
         System.out.println("or type exit to end the system");
         System.out.print("Options: ");
         menuName = kb.nextLine();
            
         try
         {
            switch(menuName)
            {
               case "Cruise Admin":
                  sysManSet[0] = menuMan.getMenu(menuName).runMenu(sysManSet[0]);
                  break;
               case "Cruise Customer":
                  System.out.println("Sorry, menu is not availble.");
                  break;
               case "Flight Admin":
                  sysManSet[1] = menuMan.getMenu(menuName).runMenu(sysManSet[1]);
                  break;
               case "Flight Customer":
                  sysManSet[1] = menuMan.getMenu(menuName).runMenu(sysManSet[1]);
                  break;
               case "exit":
                  System.out.println("Have a nice day.");
                  return;
               default:
                  System.out.println("Sorry, I cannot find that menu, please reenter you choice. ");
            }
         }
         catch(Exception e)
         {
            System.out.println("System error\nRestarting main menu");
            kb.nextLine();
         }
      }
   }
}