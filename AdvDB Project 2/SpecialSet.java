// Brad Howard
// Project 2: Child/Frequency object

public interface SpecialSet<T>
{
	public T getEntry(int index);
	
	public void setEntry(T t);
   
   public void setEntry(int index, int num);
	
   public void setEntry();
   
	public boolean isLeaf();
   
   public void setNextSet(BTNode nextSet);
   
   public BTNode getNextSet();
   
   public int getSize();
}