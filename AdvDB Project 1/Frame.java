// Brad Howard
// Project 1: Frame Object

public class Frame
{
   private int pin;
   private int pageNumber;
   private int frameNumber;
   private boolean dirty;
   private String contents;
	
   public Frame(String s)
   {
      pin = 0;
      dirty = false;
      contents = s;
      frameNumber = -1;
      pageNumber = 0;
   }
	
   public void upPinCount()
   {
      pin++;
   }
	
   public void downPinCount()
   {
      if(pin > 0)
         pin--;
      else
         System.out.println("Pin count is 0.");
   }
   
   public void addToContents(String s)
   {
      contents += s;
   }
   
   public int getPinCount()
   {
      return pin;
   }
	
   public void mark()
   {
      if(!dirty)
         dirty = true;
   }
	
   public boolean isDirty()
   {
      return dirty;
   }
   
   public void setFrameNumber(int number)
   {
      frameNumber = number;
   }
   
   public void setPageNumber(int number)
   {
      pageNumber = number;
   }
   
   public int getFrameNumber()
   {
      return frameNumber;
   }
   
   public int getPageNumber()
   {
      return pageNumber;
   }
	
   @Override
   public String toString()
   {
      return contents;
   }
}