// Brad Howard
// Project 1: FileIndex Object

public class FileIndex
{
   private int pageNumber;
   private int frameNumber;
	
   public FileIndex(int pageNumber, int frameNumber)
   {
      this.pageNumber = pageNumber;
      this.frameNumber = frameNumber;
   }
	
   public int getPage()
   {
      return pageNumber;
   }
	
   public int getFrameNumber()
   {
      return frameNumber;
   }
   
   @Override
   public String toString()
   {
      return "Page: " + pageNumber + " Frame: " + frameNumber;
   }
}