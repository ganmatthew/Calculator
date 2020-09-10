import java.util.*;
public class Queue
{
    private ArrayList <String> val;

    public Queue()
    {
        val = new ArrayList <String> ();
    }

    public void Enqueue(String s)
    {
        val.add(s);
    }

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

    public String peek()
    {
        return val.get(0);
    }

    public boolean isEmpty()
    {
        return 0 == val.size();
    }

    public int getSize()
    {
        return val.size();
    }

    public String getValue (int index)
    {
        return val.get(index);
    }

}

