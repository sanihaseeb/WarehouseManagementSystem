//Name: Sani Haseeb


package assignment2;

public class Warehouse
{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths)
	{
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++)
		{
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping()
	{
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null)
		{
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null)
		{
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print()
 	{
 		String result = "";
		for (int i = 0; i < nbShelves; i++)
		{
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear()
 	{
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++)
 		{
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort()
	{
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end)
{
		//ADD YOUR CODE HERE
 if(start<end) 
     {
	 int m = (start+end)/2;
	 mergeSort(start, m);
     mergeSort( m+1, end);

     merge( start, m, end);
     }
}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end)
	{
		//ADD YOUR CODE HERE
		
		int i,j,k ;
        int L = mid - start + 1;
        int R = end - mid;
        
        int [] ArrL = new int[L];
        int [] ArrR = new int[R];
        
        for(i=0;i<L;i++) 
        {
            ArrL[i] = storage[start + i].height;
        }
        for(j=0;j<R;j++) 
        {
            ArrR[j] = storage[mid + 1 + j].height;
        }
        
        
        i=0;
        j=0;
        k=start;
        
        while(i<L && j<R)
        {
            
            if(ArrL[i]<=ArrR[j]) 
            {
                storage[k].height = ArrL[i];
                i++;
            }
            else 
            {
                storage[k].height=ArrR[j];
                j++;
            }
            
            k++;
            
        }
        
        while(i<L) 
        {
            
            storage[k].height=ArrL[i];
            i++;
            k++;
            
        }
        
        while(j<R)
        {
            
            storage[k].height = ArrR[j];
            j++;
            k++;
            
        }
	}
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b)
	{
		if (b.height>storage[(nbShelves)-1].height) 
		{
			return problem;
		}
		

		boolean condition= false;
	
		for(int i =0; (i<nbShelves) && (condition==false); i++)
		{
		
       if (b.length<=storage[i].availableLength && b.height<=storage[i].height)
    	   
       {
    	  storage[i].addBox(b);
          condition=true; 
       }
       
		}
		
		
		if(condition==false) 
		{
	       return problem;
		}
		else
		{
			return noProblem;
		}
		
		
		}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b)
	{
		//ADD YOUR CODE HERE (ADDED)
        if(b instanceof UrgentBox) 
        {
            if (toShipUrgently==null) 
            {
                toShipUrgently = new UrgentBox(b.height, b.length, b.id);
                
            }
            
            
            else
            {
                UrgentBox urg = new UrgentBox(b.height, b.length, b.id);
                urg.next = toShipUrgently;
                toShipUrgently = urg;
            }
            
            return noProblem;
        }
        else if (b instanceof Box){
            
            if(toShip==null)
            {
                toShip = new Box(b.height, b.length, b.id);
                
            }
            
            else
            {
                Box bx = new Box(b.height, b.length, b.id);
                bx.next = toShip;
                toShip = bx;
            }
            
            return noProblem;
        }
        else 
        {
            return problem;
        }
	}
	
		
	
	  
		
		

	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox(String identifier)
	{
		//ADD YOUR CODE HERE
		Box BX = null;
		int i = 0;
		for(; i<nbShelves && BX == null;i++) 
		{
			BX = storage[i].removeBox(identifier);
		}
		
		if (i>=nbShelves) 
		{
			return problem;
		}
		else if(BX == null) 
		{
			return problem;
		}
		else 
		{
		addToShip(BX);
		return noProblem;
		}
	}
	
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position)
	{
		//ADD YOUR CODE HERE
	
		Boolean condition = false;
		for(int i=0; (i<position && condition==false); i++) 
		{
			if ((storage[i].height>=b.height)&&(storage[i].availableLength >= b.length)) 
			{
				storage[i].addBox(b);
				storage[position].removeBox(b.id);
				condition = true;
			}
		}
	}
				
		
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize ()
	{
		//ADD YOUR CODE HERE
		Box c = storage[0].firstBox;
		for(int i=0; i < nbShelves ; i++)
		{
			c = storage[i].firstBox;
			if(c!=null)
			{
				while(c!=null)
				{
					Box pointC= c.next;
					moveOneBox(c,i);
					c = pointC;
				}
			}
		}
		
	}
}
