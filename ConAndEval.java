import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConAndEval
{
    // Accepts an operator string to determine its level of operator precedence
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
    
    /*******************************************
    * CONVERSION FROM INFIX TO POSTFIX
    *******************************************/

    // Converts an infix expression string to a postfix expression string. Returns the result as a string.
    public static String ConvertInfixToPostFix(Queue in, Queue out, Stack op, String s)
    {
        String output = ""; // initialize empty string
        Pattern r = Pattern.compile("[0-9]+|[%\\^()+*/-]|([=&|])\\1|[!<>]+=?"); // regex pattern to tokenize the expression
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

        /*
        while (!out.isEmpty());
        {
            output += out.Dequeue();
            output += " ";
        }
        */
        
        int i;
        for (i = 0; i < out.getSize(); i++) {
          output += out.getValue(i);
          output += " ";
        }
        

        return output;
    }

    /******************************************
    * EVALUATING POSTFIX EXPRESSIONS
    *******************************************/

    // Accepts two numerical strings and returns true (1) if the first number is less than the second number
    static String LessThan(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return sB > sA ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns true (1) if the first number is greater than the second number
    static String GreaterThan(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return sB < sA ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns true (1) if the first number is greater than or equal to the second number
    static String GreaterThanOrEqual(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return sB <= sA ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns true (1) if the first number is less than or equal to the second number
    static String LessThanOrEqual(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return sB >= sA ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two binary strings and returns true (1) or false (0) depending on the AND gate result
    static String BinaryAnd(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return (sB == 1) && (sA == 1) ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    static String Negation(String a)
    {
        return a.contentEquals("0") ? "1" : "0";
    }

    // Accepts two binary strings and returns true (1) or false (0) depending on the OR gate result
    static String BinaryOr(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return (sB == 1) || (sA == 1) ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two binary strings and returns true (1) if both strings are equal to each other, false (0) if otherwise
    static String EqualTo(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return sB == sA ? "1" : "0";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two binary strings and returns true (1) if both strings are not equal to each other, false (0) if otherwise
    static String NotEqualTo(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            System.out.println(sA + " != " + sB);

            return sB == sA ? "0" : "1";
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the sum
    static String Addition(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return Integer.toString(sA + sB);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the difference
    static String Subtraction(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            //System.out.println(a + "-" + b);

            return Integer.toString(sB - sA);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the product
    static String Multiplication(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return Integer.toString(sA * sB);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the quotient
    static String Division(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return Integer.toString(sB / sA);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the modulo result
    static String Modulo(String a, String b)
    {
        try
        {
            int sA = Integer.valueOf(a);
            int sB = Integer.valueOf(b);

            return Integer.toString(sB % sA);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Accepts two numerical strings and returns the exponent result
    static String Exponent(String a, String b)
    {
        try
        {
            double sA = Double.valueOf(a);
            double sB = Double.valueOf(b);

            double res = Math.pow(sB, sA);

            int retval = (int)res;

            return Integer.toString(retval);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    // Evaluates a postfix expression by performing calculations. Returns the result as an integer.
    public static String PostFixEvaluation(Queue in , Stack st, String s)
    {
        
        String inputstr = new String();

        inputstr = s;

        Pattern r = Pattern.compile("[0-9]+|[%\\^()+*/-]|([=&|])\\1|[!<>]+=?"); // regex expression to tokenize the postfix expression
        Matcher m = r.matcher(inputstr);

        while (m.find())
        {
            in.Enqueue(m.group());
        }

        //while (!in.isEmpty()) System.out.println(in.Dequeue());

        while (!in.isEmpty())
        {
            if (opValue(in.peek()) == 0) //number
            {
                st.push(in.Dequeue());
            }
            else //operand
            {
                switch(in.Dequeue())
                {
                    case ">":
                        st.push(LessThan(st.pop(), st.pop()));
                        break;
                    case "<":
                        st.push(GreaterThan(st.pop(), st.pop()));
                        break;
                    case "&&":
                        st.push(BinaryAnd(st.pop(), st.pop()));
                        break;
                    case "!":
                        st.push(Negation(st.pop()));
                        break;
                    case "||":
                        st.push(BinaryOr(st.pop(), st.pop()));
                        break;
                    case ">=":
                        st.push(LessThanOrEqual(st.pop(), st.pop()));
                        break;
                    case "<=":
                        st.push(GreaterThanOrEqual(st.pop(), st.pop()));
                        break;
                    case "==":
                        st.push(EqualTo(st.pop(), st.pop()));
                        break;
                    case "!=":
                        st.push(NotEqualTo(st.pop(), st.pop()));
                        break;
                    case "+":
                        st.push(Addition(st.pop(), st.pop()));
                        break;
                    case "-":
                        st.push(Subtraction(st.pop(), st.pop()));
                        break;
                    case "*":
                        st.push(Multiplication(st.pop(), st.pop()));
                        break;
                    case "/":
                        if (st.peek().contentEquals("0"))
                        {
                            while (!in.isEmpty()) in.Dequeue();
                            while (!st.isEmpty()) st.pop();
                            st.push("Division by zero error!");
                        }
                        else
                        {
                            st.push(Division(st.pop(), st.pop()));
                            break;
                        }
                        break;
                    case "%":
                        if (st.peek().contentEquals("0"))
                        {
                            while (!in.isEmpty()) in.Dequeue();
                            while (!st.isEmpty()) st.pop();
                            st.push("Division by zero error!");
                        }
                        else
                        {
                            st.push(Modulo(st.pop(), st.pop()));
                            break;
                        }
                        break;
                    case "^":
                        st.push(Exponent(st.pop(), st.pop()));
                        break;
                };
                
            }
        }

        return st.peek();
    }
}