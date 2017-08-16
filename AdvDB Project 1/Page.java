// Brad Howard
// Project 1: Page Object

public class Page
{
	private int pageNumber;
	private String pageContents;
	
	public Page(int pageNumber)
	{
		this.pageNumber = pageNumber;
		pageContents = "This is page " + pageNumber;
	}
	
	public String getContents()
	{
		return pageContents;
	}
	
	public void setContents(String s)
	{
		pageContents += s;
	}
	
	public int getPageNumber()
	{
		return pageNumber;
	}
	
	@Override
	public String toString()
	{
		return pageContents;
	}
}