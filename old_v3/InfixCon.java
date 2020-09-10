import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InfixCon
{
    static StringBuilder sb = new StringBuilder();
    public InfixCon() {}

    static int opValue (String s)
    {
        //System.out.println(s);
        if (s.contains("!"))
            return 7;
        if (s.contains("^"))
            return 6;
        if (s.contains("*") || s.contains("/") || s.contains("%"))
            return 5;
        if (s.contains("+") || s.contains("-"))
            return 4;
        if (s.contains("==") || s.contains("!="))
            return 3;
        if (s.contains("<") || s.contains("<=") || s.contains(">") || s.contains(">="))
            return 2;
        if (s.contains("&&") || s.contains("||"))
            return 1;
        return 0;
    }

    public static String ConvertInfixToPostFix(String s, Stack op, Queue in, Queue out)
    {
        int i;
        String output = "";

        Pattern r = Pattern.compile("[0-9]+|[%\\^()+*/-]|([=&|])\\1|[!<>]+=?");
        Matcher m = r.matcher(s);

        while (m.find())
        {
            in.Enqueue(m.group());
        }

        while (!in.isEmpty())
        {
            if (in.peek().contentEquals("("))
            {
                op.push(in.Dequeue());
            }
            else if (in.peek().contentEquals(")"))
            {
                in.Dequeue();
                while (!op.isEmpty() && !(op.peek().contentEquals("(")))
                {
                    out.Enqueue(op.pop());
                }
                op.pop();
            }
            else if (opValue(in.peek()) == 0)
            {
                out.Enqueue(in.Dequeue());
            }
            else if (opValue(in.peek()) > 0)
            {
                if (op.isEmpty())
                {
                    op.push(in.Dequeue());
                }
                else
                {
                    if (opValue(in.peek()) > opValue(op.peek()))
                    {
                        op.push(in.Dequeue());
                    }
                    else
                    {
                        while (!op.isEmpty() && opValue(in.peek()) <= opValue(op.peek()))
                        {
                            out.Enqueue(op.pop());
                        }
                        op.push(in.Dequeue());
                    }
                }
            }        
        }

        while (!op.isEmpty())
        {
            out.Enqueue(op.pop());
        }

        for (i = 0; i < out.getSize(); i++)
        {
            output += out.getValue(i);
            output += " ";
        }
        
        /*
        int i;
        for (i = 0; i < out.getSize(); i++) {
          output += out.getValue(i);
        }
        */

        return output;

    }
}