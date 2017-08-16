// Brad Howard
// Project 3: Block object

import java.util.*;

public class Block
{
	private int size;
	private int BID;
	private ArrayList<Tuple> records;
	
	public Block(int size)
	{
		this.size = size;
		records = new ArrayList<Tuple>(size);
	}
   
   public int size()
   {
      return size;
   }
   
   public int numberOfRecords()
   {
      return records.size();
   }
   
   public void setBID(int id)
   {
      BID = id;
   }
	
	public void addRecord(String r)
	{
		String[] data = r.split("\t");
      Tuple tuple = new Tuple();
      
      tuple.update(data);
      
      if(size != records.size())
			records.add(tuple);
	}
   
   public void updateRecord(int index, String r)
   {
      String[] data = r.split("\t");
      Tuple tuple = new Tuple();
      
      tuple.update(data);
      
      records.set(index, tuple);
   }
	
	public Tuple getRecord(int index)
	{
		return records.get(index);
	}
   
   public void sortRecords()
   {
      Collections.sort(records);
   }
}