// Brad Howard
// Project 1: Buffer Hash Table Object

import java.util.*;

public class BufHashTbl
{
   private List<FileIndex>[] hashTable;
   private int size;
	
   @SuppressWarnings("unchecked")
   public BufHashTbl()
   {
      hashTable = new LinkedList[6];
      
      for(int index = 0; index < hashTable.length; index++)
      {
         hashTable[index] = new LinkedList<FileIndex>();
      }
   	
      size = 0;
   }
	
   public int hashCode(int pageNumber, int frameNumber)
   {
      int hCode = 0;
   	
      hCode = pageNumber * frameNumber;
   	
      return hCode;
   }
	
   public int HashFunction(FileIndex item)
   {
      return hashCode(item.getPage(), item.getFrameNumber()) % hashTable.length;
   }
	
   public void insert(int pageNumber, int frameNumber)
   {
      FileIndex item = new FileIndex(pageNumber, frameNumber);
      int index = HashFunction(item);
   	
      hashTable[index].add(item);
      size++;
   }
	
	//lookup
   public int lookup(int pageNumber)
   {
      for(int index = 0; index < hashTable.length; index++)
      {
         for(int jdex = 0; jdex < hashTable[index].size(); jdex++)
         {
            if(hashTable[index].get(jdex).getPage() == pageNumber)
               return hashTable[index].get(jdex).getFrameNumber();
         }
      }
      return -1;
   }
	
	//remove
   public void remove(int pageNumber, int frameNumber)
   {
      for(int index = 0; index < hashTable.length; index++)
      {
         for(int jdex = 0; jdex < hashTable[index].size(); jdex++)
         {
            if(hashTable[index].get(jdex).getPage() == pageNumber && hashTable[index].get(jdex).getFrameNumber() == frameNumber)
            {
               hashTable[index].remove(jdex);
               size--;
            }
         }
      }
   }
	
   public int getSize()
   {
      return size;
   }
}