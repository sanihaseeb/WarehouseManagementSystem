//Name: Sani Haseeb

package assignment2;

public class Shelf
{
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength)
	{
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear()
	{
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print()
	{
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null)
		{
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b)
	{
		//ADD YOUR CODE HERE
	
		if (lastBox==null) 
	{
		lastBox=b;
		firstBox = lastBox;
	}
		else
		{
		b.previous = lastBox;
		lastBox.next = b;
		lastBox	= b;
		}
		availableLength = availableLength-b.length;
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier)
	{
		//ADD YOUR CODE HERE
		if(firstBox==null)
		{
			return null;
		}
		if(identifier.equals(firstBox.id))
		{
			Box top = firstBox;
			firstBox = firstBox.next;
			if(firstBox !=null)
			{
				firstBox.previous=null;
			}
			if(top.next==null)
			{
				lastBox=firstBox;
			}
			
			availableLength = availableLength + top.length;
			top.next=top.previous=null;
			return top;
		}
		if (identifier.equals(lastBox.id))
		{
			Box bottom = lastBox;
			lastBox = lastBox.previous;
			if(lastBox !=null) 
			{
				lastBox.next = null;
			}
			if(bottom.previous==null)
			{
				firstBox=lastBox;
			}
			availableLength = availableLength + bottom.length;
			bottom.next = bottom.previous=null;
			return bottom;
		}
	

 
           Box current = firstBox;


         while(current !=null && !current.id.equals(identifier)) 
         {
	
	      current = current.next;
         }

         if(current == null) 
         {
	      return null;
         }
         Box bx= current;
          current.previous.next=current.next;
         current.next.previous=current.previous;

           bx.next=bx.previous=null;
           availableLength = availableLength + bx.length;
           //System.out.println(bx.id);
           return bx;
  }
}





	




