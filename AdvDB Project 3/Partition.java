// Brad Howard
// Project 3: Partition object

import java.util.*;

public class Partition
{
   private ArrayList<Block> group;
   private int partitionSize;
   
   public Partition(int n)
   {
      partitionSize = n;
      group = new ArrayList<Block>(partitionSize);
   }
   
   public int size()
   {
      return partitionSize;
   }
   
   public int numberOfBlocks()
   {
      return group.size();
   }
   
   public void set(Block b)
   {
      group.add(b);
   }
   
   public void update(int index, int jdex, String r)
   {
      group.get(index).updateRecord(jdex, r);
   }
   
   public Block get(int index)
   {
      return group.get(index);
   }
}