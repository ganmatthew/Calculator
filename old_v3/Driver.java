import java.util.Scanner;

public class Driver {
  private static Scanner kb = new Scanner (System.in);
  private static Boolean logsEnabled = false;

  private static String LOG_COMMAND = "logs";
  private static String QUIT_COMMAND = "quit";

  private static void manualQueueing (String exp, Queue queue) {
    int i = 0;
    String[] splitArray = exp.split(" ");
    String str;

    do {
      System.out.print((logsEnabled) ? i + "/" + splitArray.length + "\n" : 0);
      str = splitArray[i];
      queue.Enqueue(str);
      i++;
    } while (i < splitArray.length);

  }

  private static void displayProgram (Scanner kb, Boolean menuLoop) {
    do {
      int result = 0;

      Queue infixQueue = new Queue();
      Queue postfixQueue = new Queue();
      Stack conStack = new Stack();
      Stack evalStack = new Stack();

      String infixExp = new String();
      String postfixExp = new String();

      System.out.println();
      infixExp = kb.nextLine();
      //postfixExp = kb.nextLine();

      if (infixExp.equalsIgnoreCase(LOG_COMMAND)) {
        logsEnabled = !logsEnabled;
        infixExp = kb.nextLine();
      }

      if (!infixExp.equalsIgnoreCase(QUIT_COMMAND)) {
      //if (!postfixExp.equalsIgnoreCase(QUIT_COMMAND))
        postfixExp = InfixCon.ConvertInfixToPostFix(infixExp, conStack, infixQueue, postfixQueue);
        //manualQueueing (postfixExp, postfixQueue);

        System.out.println(postfixExp);

        result = PostfixEval.startEvaluatePostfix(postfixQueue, evalStack, logsEnabled);

        if (!PostfixEval.getErrorType())
          System.out.println(result);
      }
      else {
        menuLoop = false;
        kb.close();
      }

    }
    while (menuLoop);
  }

  public static void main(String[] args) {
    Boolean menuLoop = true;
    displayProgram(kb, menuLoop);
  }
}
