// Brad Howard
// Project 2: Child/Frequency object

public class FrequencySet implements SpecialSet<Integer>
{
	private int[] frequencySet;
   private BTNode nextSet;
	private int size;
	
	public FrequencySet(int size)
	{
		this.size = size;
		frequencySet = new int[size];
	}
   
   public boolean isLeaf()
   {
      return true;
   }
   
   public int getSize()
   {
      return size;
   }
   
   public void setEntry()
   {
      for(int index = 0; index < size; index++)
      {
         frequencySet[index] = 0;
      }
   }
   
   public void setEntry(Integer n)
   {
      int index = n.intValue();
      
      if(index >= 0 && index < size)      
         frequencySet[index]++;
      else
         System.out.println("No such element");
   }
   
   public void setEntry(int index, int freq)
   {
      frequencySet[index] = freq;
   }
   
   public Integer getEntry(int index)
   {
      return new Integer(frequencySet[index]);
   }
   
   public void setNextSet(BTNode nextNode)
   {
      nextSet = nextNode;
   }
   
   public BTNode getNextSet()
   {
      return nextSet;
   }
}