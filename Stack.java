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
        if (!val.isEmpty())
        {
            String s;
            s = val.get(val.size());
            val.remove(val.size());
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
}