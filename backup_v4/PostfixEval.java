import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PostfixEval
{
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


    public static String PostFixEvaluation(String s)
    {
        
        String inputstr = new String();

        inputstr = s;

        Queue in = new Queue();
        Stack st = new Stack();

        Pattern r = Pattern.compile("[0-9]+|[%\\^()+*/-]|([=&|])\\1|[!<>]+=?");
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
                            st.push("error");
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
                            st.push("error");
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