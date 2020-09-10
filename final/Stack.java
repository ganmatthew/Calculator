import java.util.*;
public class Stack
{
    /*******************************************
    * STACK DATA STRUCTURE
    *******************************************/

    private ArrayList <String> val;

    // Creates a stack arraylist
    public Stack()
    {
        val = new ArrayList <String> ();
    }

    // Removes the topmost element from the stack
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

    // Adds an element onto the top of the stack
    public void push(String s)
    {
        val.add(s);
    }

    // Peeks or returns the token on the top of the stack
    public String peek()
    {
        return val.get(val.size() - 1);
    }

    // Empties the stack
    public boolean isEmpty()
    {
        return 0 == val.size();
    }

    // Returns the size of the stack
    public int getSize()
    {
        return val.size();
    }

    // Retrieves the token of a specific index on the stack
    public String getValue (int index)
    {
        return val.get(index);
    }
}