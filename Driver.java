import java.util.Scanner;

import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        String in;
        String PostFixStr;
        String PostFixVal;

        do
        {
            in = s.nextLine();
            if (!in.contentEquals("QUIT"))
            {
                System.out.println(in);
                PostFixStr = InfixCon.ConvertInfixToPostFix(in);
                System.out.println(PostFixStr);
                PostFixVal = PostfixEval.PostFixEvaluation(PostFixStr);
                System.out.println(PostFixVal + "\n");
            }
        }
        while(!in.contentEquals("QUIT"));

        System.out.println("ok");
    }


}