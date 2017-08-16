// Brad Howard
// Project 2: Child/Frequency object

public class ChildSet implements SpecialSet<BTNode>
{
	private BTNode[] childSet;
	private int size;
   private int numberOfKids;
	
	public ChildSet(int size)
	{
		this.size = size;
      numberOfKids = 0;
		childSet = new BTNode[size];
	}
   
   public boolean isLeaf()
   {
      return false;
   }
   
   public int getSize()
   {
      return size;
   }
   
   public void setEntry()
   {
      for(int index = 0; index < size; index++)
      {
         childSet[index] = new BTNode(0, size/2 - 1, isLeaf());
      }
   }
   
   public void setEntry(BTNode node)
   {
      BTNode temp;
      
      if(node != null)
      {
         for(int index = 0; index < size; index++)
         {
            if(childSet[index] == null || childSet[index].getID() == node.getID())
            {
               childSet[index] = node;
               numberOfKids = countChildren();
               sortSet();
               return;
            }
         }
      }
   }
   
   private int countChildren()
   {
      int n = 0;
      
      for(int index = 0; index < childSet.length; index++)
      {
         if(childSet[index] != null)
         {
            n++;
         }
      }
      
      return n;
   }
   
   private void sortSet()
   {
      int current, indexSmallest, position;
		BTNode tempHolder;
		
		for(position = 0; position < numberOfKids; position++)
		{
			indexSmallest = position;
		
			for(current = position + 1; current < numberOfKids; current++)
			{
				if(childSet[indexSmallest] != null && childSet[current] != null && childSet[indexSmallest].getData(0).compareTo(childSet[current].getData(0)) > 0)
				{
					indexSmallest = current;
				}
			}
			
			tempHolder = childSet[position];
			childSet[position] = childSet[indexSmallest];
			childSet[indexSmallest] = tempHolder;
		}
   }
   
   public void setEntry(int index, BTNode node)
   {
      childSet[index] = node;
   }
   
   public void setEntry(int index, int num)
   {
      throw new UnsupportedOperationException("This fuction is not supported.");
   }
   
   public BTNode getEntry(int index)
   {
      return childSet[index];
   }
   
   public void setNextSet(BTNode nextNode)
   {
      throw new UnsupportedOperationException("This fuction is not supported.");
   }
   
   public BTNode getNextSet()
   {
      throw new UnsupportedOperationException("This fuction is not supported.");
   }
}