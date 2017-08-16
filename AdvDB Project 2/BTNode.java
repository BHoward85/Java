// Brad Howard
// Project 2: BTNode object

public class BTNode
{
   private int ID;
   private int nodeNumber;
   private int numberOfKeys;
   private int tier;
   private int t;
   public String[] dataSet;
   public SpecialSet itemSet;
   private boolean isLeaf;
   
   public BTNode(int h, int t, boolean leaf)
   {
      tier = h;
      numberOfKeys = 0;
      nodeNumber = 0;
      this.t = t;
      isLeaf = leaf;
      dataSet = new String[2 * t - 1];
      	
      if(isLeaf)
      {
         itemSet = new FrequencySet(2 * t - 1);
         itemSet.setEntry();
      }
      else
      {
         itemSet = new ChildSet(2 * t);
      }
      
      ID = 0;
      setData();
   }
   
   public void setNodeNumber(int n)
   {
      nodeNumber = n;
   }
   
   public int getNodeNumber()
   {
      return nodeNumber;
   }
   
   public void setID()
   {
      ID = dataSet[0].hashCode();
   }
   
   public int getID()
   {
      return ID;
   }
   
   public int getTier()
   {
      return tier;
   }
   
   public void setTier(int h)
   {
      tier = h;
   }
   
   public boolean isLeaf()
   {
      return isLeaf;
   }
   
   private void setData()
   {
      for(int index = 0; index < dataSet.length; index++)
      {
         dataSet[index] = "";
      }
   }
   
   public void add(String s)
   {
      if(has(s) && isLeaf())
      {
         itemSet.setEntry(getIndexOf(s));
         return;
      }
      
      for(int index = 0; index < dataSet.length; index++)
      {
         if(dataSet[index] == "" || s.compareTo(dataSet[index]) < 0)
         {
            for(int jdex = dataSet.length - 1; jdex > index; jdex--)
            {
               dataSet[jdex] = dataSet[jdex - 1];
               if(isLeaf())
               {
                  itemSet.setEntry(jdex, getFreq(jdex - 1).intValue());
                  itemSet.setEntry(jdex - 1, 0);
               }
            }
            
            dataSet[index] = s;
            if(isLeaf())
               itemSet.setEntry(getIndexOf(s).intValue());
            numberOfKeys++;
            setID();
            return;
         }
      }
   }
   
   public Integer getIndexOf(String s)
   {
      Integer target = null;
      
      for(int index = 0; index < dataSet.length; index++)
      {
         if(dataSet[index].equals(s))
            target = new Integer(index);
      }
      
      return target;
   }
   
   public String getData(int index)
   {
      return dataSet[index];
   }
   
   public Integer getFreq(int index)
   {
      return (Integer)itemSet.getEntry(index);
   }
   
   public void setFreq(int index, int freq)
   {
      itemSet.setEntry(index, freq);
   }
   
   public BTNode getChild(int index)
   {
      return (BTNode)itemSet.getEntry(index);
   }
   
   public int getNumberOfKeys()
   {
      return numberOfKeys;
   }
   
   public boolean isFull()
   {
      return numberOfKeys == (2 * t - 1);
   }
   
   public boolean find(String s)
   {
      boolean test = false;
      
      if(!isLeaf())
      {
         BTNode node = (BTNode)itemSet.getEntry(0);
         if(node != null && !test)
            test = node.find(s);
      }
      else
      {
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(dataSet[index].equals(s))
            {
               return true;
            }
         }
         BTNode node = (BTNode)itemSet.getNextSet();
         if(node != null && !test)
            test = node.find(s);
      }
      
      return test;
   }
   
   public void resetTiers()
   {
      resetTiers(1);
   }
   
   private void resetTiers(int h)
   {
      tier = h;
      
      if(!isLeaf())
      {
         for(int index = 0; index < itemSet.getSize(); index++)
         {
            if(itemSet.getEntry(index) != null)
            {
               BTNode node = (BTNode)itemSet.getEntry(index);
               node.resetTiers(h + 1);
            }
         }
      }
   }
   
   public void printAll()
   {
      String s = "";
      
      for(int index = 1; index < tier; index++)
      {
         s += "\t";
      }
      
      s += "" + getNodeNumber();
      
      System.out.println(s);
      
      if(!isLeaf())
      {
         for(int index = 0; index < itemSet.getSize(); index++)
         {
            if(itemSet.getEntry(index) != null)
            {
               BTNode node = (BTNode)itemSet.getEntry(index);
               node.printAll();
            }
         }
      }
   }
   
   public BTNode findPrevNode(int ID)
   {
      if(!isLeaf())
      {
         BTNode node = (BTNode)itemSet.getEntry(0);
         if(node != null)
            return node.findPrevNode(ID);
      }
      else
      {
         if(itemSet.getNextSet() != null)
         {
            if(itemSet.getNextSet().getID() == ID)
               return this;
            else
               return this.itemSet.getNextSet().findPrevNode(ID);
         }
      }
      return null;
   }
   
   public void increasesFreq(String s)
   {
      if(!isLeaf())
      {
         BTNode node = (BTNode)itemSet.getEntry(0);
         if(node != null)
            node.increasesFreq(s);
      }
      else
      {
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(dataSet[index].equals(s))
            {
               itemSet.setEntry(index);
               return;
            }
         }
         BTNode node = (BTNode)itemSet.getNextSet();
         if(node != null)
            node.increasesFreq(s);
      }
   }
   
   public boolean has(String s)
   {
      for(int index = 0; index < dataSet.length; index++)
         if(dataSet[index] != "" && dataSet[index].compareTo(s) == 0)
            return true;
            
      return false;
   }
   
   public void setEntry(BTNode node)
   {
      if(itemSet.getClass().getSimpleName().equals("ChildSet"))
         itemSet.setEntry(node);
      else if(itemSet.getClass().getSimpleName().equals("FrequencySet"))
         itemSet.setNextSet(node);
      else
         throw new UnsupportedOperationException("There was an error");
   }
   
   public boolean printNode(int ID)
   {
      boolean test = false;
      
      if(this.nodeNumber == ID)
      {
         for(int index = 0; index < numberOfKeys; index++)
         {
            System.out.print(dataSet[index]);
            if(isLeaf())
               System.out.print(" : " + itemSet.getEntry(index));
            if(index < numberOfKeys - 1)
               System.out.print(",\t");
            else
               System.out.println();
            
         }
         return true;
      }
      else
      {
         if(!isLeaf())
         {
            for(int index = 0; index < (2 * t - 1); index++)
            {
               if(itemSet.getEntry(index) != null && !test)
               {
                  BTNode node = (BTNode) itemSet.getEntry(index);
                  test = node.printNode(ID);
               }
            }
         }
      }
      return test;
   }
   
   public boolean printEntry(String s)
   {
      if(!isLeaf())
      {
         BTNode node;
         
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(!dataSet[index].equals(""))
            {
               if(index == 0 && s.compareTo(dataSet[index]) < 0)
               {
                  node = (BTNode)itemSet.getEntry(index);
                  if(node != null)
                     return node.printEntry(s);
               }
               else if(index != 0 && s.compareTo(dataSet[index - 1]) >= 0 && s.compareTo(dataSet[index]) < 0)
               {
                  node = (BTNode)itemSet.getEntry(index + 1);
                  if(node != null)
                     return node.printEntry(s);
               }
               else if(s.compareTo(dataSet[index]) >= 0)
               {
                  node = (BTNode)itemSet.getEntry(index + 1);
                  if(node != null)
                    return node.printEntry(s);
               }
            }
         }
      }
      else
      {
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(dataSet[index].equals(s))
            {
               System.out.println(dataSet[index]);
               System.out.println(itemSet.getEntry(index));
               return true;
            }
         }
         BTNode node = (BTNode)itemSet.getNextSet();
         if(node != null)
            return node.printEntry(s);
      }
      return false;
   }
   
   public void printRange(String s, String r)
   {
      if(s.compareTo(r) > 0)
         return;
      
      if(!isLeaf())
      {
         BTNode node;
         
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(!dataSet[index].equals(""))
            {
               if(index == 0 && s.compareTo(dataSet[index]) < 0)
               {
                  node = (BTNode)itemSet.getEntry(index);
                  if(node != null)
                     node.printRange(s, r);
                  return;
               }
               else if(index != 0 && s.compareTo(dataSet[index - 1]) >= 0 && s.compareTo(dataSet[index]) < 0)
               {
                  node = (BTNode)itemSet.getEntry(index + 1);
                  if(node != null)
                     node.printRange(s, r);
                  return;
               }
               else if(s.compareTo(dataSet[index]) >= 0)
               {
                  node = (BTNode)itemSet.getEntry(index + 1);
                  if(node != null)
                     node.printRange(s, r);
                  return;
               }
            }
         }
      }
      else
      {
         for(int index = 0; index < (2 * t - 1); index++)
         {
            if(dataSet[index].compareTo(s) >= 0 && dataSet[index].compareTo(r) <= 0)
               System.out.print(dataSet[index] + "\n");
         }
         BTNode node = (BTNode)itemSet.getNextSet();
         if(node != null)
            node.printRange(s, r);
      }
   }
   
   public void printAllData()
   {
      if(!isLeaf())
      {
         BTNode node = (BTNode)itemSet.getEntry(0);
         if(node != null)
            node.printAllData();
      }
      else
      {
         for(int index = 0; index < numberOfKeys; index++)
         {
            System.out.print(dataSet[index] + "\n");
         }
         BTNode node = (BTNode)itemSet.getNextSet();
         if(node != null)
            node.printAllData();
      }
   }
}