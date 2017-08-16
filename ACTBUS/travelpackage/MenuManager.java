// Brad Howard
// ACTBS

// Menu Manager
// Ver 0.99

package travelpackage;

import java.util.*;

public class MenuManager
{
   private List<MenuSystem> menuSet;
	
	
   public MenuManager()
   {
      menuSet = new LinkedList<MenuSystem>();
   }
	
   public void addMenu(MenuSystem menu)
   {
      menuSet.add(menu);
   }
	
   public MenuSystem getMenu(String menuType)
   {
      MenuSystem temp = null;
   	
      for(int index = 0; index < menuSet.size(); index++)
      {
         if(menuSet.get(index).menuType().equals(menuType))
            temp = menuSet.get(index);
      }
   	
      return temp;
   }
}