// Brad Howard
// ACTBS

// Menu System
// Ver 0.99

package travelpackage;

import travelpackage.*;

public interface MenuSystem
{
   public String menuType();

   public SystemManager runMenu(SystemManager sysMan);
   
   public void readFromFile();
   
   public void readFromFile(String file);
   
   public SystemManager getSystemManager();
}