// Test hash table

public class Tester
{
   private static final int POOLSIZE = 5;
   private static final int NUMBEROFPAGES = 12;
   
   public static void main(String[] args)
   {
      BufHashTbl hashTable = new BufHashTbl();
      BufMgr bm = new BufMgr(POOLSIZE);
      
      // Hash Table tests
      hashTable.insert(1, 10);
      hashTable.insert(2, 12);
      hashTable.insert(3, 20);
      hashTable.insert(4, 4);
      hashTable.insert(5, 2);
      hashTable.insert(6, 1);
      hashTable.insert(7, 25);
      
      System.out.println(hashTable.lookup(5));
      
      hashTable.remove(5, 2);
      
      System.out.println(hashTable.lookup(5));
      
      // Buffer manager tests
      for(int index = 0; index < NUMBEROFPAGES; index++)
      {
         bm.createPage(index);
      }
      
      //bm.readPage(0);
      bm.readPage(1);
      bm.readPage(2);
      bm.readPage(3);
      bm.readPage(4);
      bm.pin(0);
      bm.pin(1);
      bm.pin(2);
      bm.pin(3);
      bm.pin(4);
      bm.addToPage(0);
      bm.addToPage(4);
      bm.unpin(3);
      bm.unpin(4);
      bm.unpin(0);
      bm.readPage(8);
      bm.readPage(9);
      bm.pin(8);
      bm.pin(9);
      bm.unpin(9);
      bm.pin(9);
      bm.unpin(1);
      bm.unpin(1);
      bm.pin(1);
      bm.addToPage(1);
      bm.unpin(1);
      bm.readPage(7);
      bm.pin(7);
      bm.addToPage(7);
      bm.addToPage(7);
      bm.unpin(7);
      bm.readPage(6);
      bm.readPage(6);
      bm.pin(6);
      bm.readPage(11);
      bm.pin(11);
      bm.readPage(11);
      bm.readPage(10);
   }
}