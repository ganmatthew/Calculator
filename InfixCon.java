/*
  TODO: Port code from using built-in methods since ayaw ni sir :(
*/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InfixCon
{

    static int opValue (String s)
    {
        //System.out.println(s);
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
        /*switch (s)
        {
            case ")":
            case "(":
            i = 6; 
            break;

            case "*":
            case "/":
            case "%":
            i = 5; 
            break;

            case "+":
            case "-":
            i = 4;
            break;

            case "==":
            case "!=":
            i = 3;
            break;

            case ">":
            case ">=":
            case "<":
            case "<=":
            i = 2;
            break;

            case "&&":
            case "||":
            i = 1;
            break;
        }*/
    }
    public static void main(String[] args)
    {
        Stack <String> op_stack = new Stack <String> ();
        Queue <String> in_queue = new LinkedList<>();
        Queue <String> out_queue = new LinkedList<>();

        String test = "5*4%3/2+1-2121";
        String test2 = "(1+2)/(3+4)";

        String p = "([^%<>=&!|()+*/-][&=|]?)|[0-9]+";
        
        Pattern r = Pattern.compile(p);
        Matcher m = r.matcher(test2);

        while (m.find())
        {
            System.out.print("[" + m.group() + "] ");
            in_queue.add(m.group());
        }

        /*
                else if (in_queue.peek().contentEquals("("))
                    {
                        op_stack.push(in_queue.poll());
                    }
                    else if (in_queue.peek().contentEquals(")"))
                    {
                        //in_queue.poll();
                        while (!op_stack.isEmpty() && !(op_stack.peek().contentEquals("(")))
                        {
                            out_queue.add(op_stack.pop());
                        }
                        //op_stack.pop();
                        op_stack.remove(")");
                        op_stack.remove("(");
                    }

        */

        System.out.println("");

        while (!in_queue.isEmpty())
        {
            if (in_queue.peek().contentEquals("("))
            {
                op_stack.push(in_queue.poll());
            }
            else if (in_queue.peek().contentEquals(")"))
            {
                in_queue.poll();
                while (!op_stack.isEmpty() && !(op_stack.peek().contentEquals("(")))
                {
                    out_queue.add(op_stack.pop());
                }
                op_stack.pop();
            }
            else if (opValue(in_queue.peek()) == 0)
            {
                out_queue.add(in_queue.poll());
            }
            else if (opValue(in_queue.peek()) > 0)
            {   
                if (op_stack.isEmpty())
                {
                    op_stack.push(in_queue.poll());
                }
                else
                {
                    if (opValue(in_queue.peek()) > opValue(op_stack.peek()))
                    {
                        op_stack.push(in_queue.poll());
                    }
                    else 
                    {
                        while (!op_stack.isEmpty() && (opValue(in_queue.peek()) <= opValue(op_stack.peek())))
                        {
                            //if (opValue(in_queue.peek()) <= opValue(op_stack.peek()))
                            out_queue.add(op_stack.pop());
                        }
                        op_stack.push(in_queue.poll());
                    }
                }
            }
        }

        while (!op_stack.isEmpty())
        {
            out_queue.add(op_stack.pop());
        }

        while (!out_queue.isEmpty())
        {
            System.out.print(out_queue.poll() + " ");
        }


    }
}