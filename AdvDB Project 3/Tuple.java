// Brad Howard
// Project 3: Block object

import java.util.*;

public class Tuple implements Comparable<Tuple>
{
   private String[] data;   
   
   public void update(String[] input)
   {
      data = input;
   }
   
   public String[] get()
   {
      return data;
   }
   
   public String get(int index)
   {
      if(index > -1 && index < data.length)
         return data[index];
      else
         return "";
   }
   
   public int numberCols()
   {
      return data.length;
   }
   
   @Override
   public int compareTo(Tuple that)
   {
      return Integer.parseInt(this.data[0]) - Integer.parseInt(that.get()[0]);
   }
   
   @Override
   public String toString()
   {
      String s = "";
      
      for(int i = 0; i < data.length - 1; i++)
         s += data[i] + "\t";
      s += data[data.length - 1];
      
      return s;
   }
}