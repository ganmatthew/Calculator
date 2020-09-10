import java.util.*;
public class Queue
{
    /*******************************************
    * QUEUE DATA STRUCTURE
    *******************************************/

    private ArrayList <String> val;

    // Creates a queue arraylist
    public Queue()
    {
        val = new ArrayList <String> ();
    }

    // Call function to enqueue tokens into the back of the queue
    public void Enqueue(String s)
    {
        val.add(s);
    }

    // Call function to dequeue tokens from the front of the queue
    public String Dequeue()
    {
        String s = null;
        if (!val.isEmpty())
        {
            s = val.get(0);
            val.remove(0);
        }
        return s;
    }

    // Call function to peek or return the value of the frontmost item of the queue
    public String peek()
    {
        return val.get(0);
    }

    // Call function to empty the queue
    public boolean isEmpty()
    {
        return 0 == val.size();
    }

    // Call function to return the size of the queue
    public int getSize()
    {
        return val.size();
    }

    // Call function to return the token located at a specific index in the queue
    public String getValue (int index)
    {
        return val.get(index);
    }

}

