import java.util.*;
public class Stack
{
    private ArrayList <String> val;

    public Stack()
    {
        val = new ArrayList <String> ();
    }

    public String pop()
    {
        String s = null;
        if (!val.isEmpty())
        {
            s = val.get(val.size() - 1);
            val.remove(val.size() - 1);
        }
        return s;
    }

    public void push(String s)
    {
        val.add(s);
    }

    public String peek()
    {
        return val.get(val.size());
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