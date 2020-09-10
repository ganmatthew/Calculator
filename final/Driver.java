import java.util.Scanner;

public class Driver
{
    private static Scanner s = new Scanner(System.in);
    public static void main(String[] args)
    {
        String input;
        String PostFixStr;
        String PostFixVal;

        do
        {
            Queue in = new Queue();  // queue used during conversion from infix to postfix
            Stack op = new Stack();  // stack used during 
            Queue out = new Queue();
            input = s.nextLine();

            if (!input.equalsIgnoreCase("QUIT"))
            {
                System.out.println(input); // note: it displays the user input so that the inputted expression is shown on the output stream during input redirection
                PostFixStr = ConAndEval.ConvertInfixToPostFix(in, out, op, input); 
                System.out.println(PostFixStr);
                PostFixVal = ConAndEval.PostFixEvaluation(out, op, PostFixStr);
                System.out.println(PostFixVal + "\n");
            }
        }
        while(!input.equalsIgnoreCase("QUIT"));

        s.close();
    }


}