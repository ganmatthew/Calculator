class PostfixEval {
  private static int result;

  public PostfixEval() {};

  private static int getIntegerFromString (String t) {
    return Integer.parseInt(t);
  }

  private static Boolean isOperator(char c) {
    switch (c) {
      case '(':
      case ')':
      case '^':
      case '*':
      case '/':
      case '%':
      case '+':
      case '-':
      case '<':
      case '>':
      case '=':
      case '!':
      case '&':
      case '|':
        return true;
      default:
        return false;
    }
  }
  
  private static int doArithmetic (int operand1, int operand2, char operator) {
    int n, num;
    switch (operator) {
      case '+':
        System.out.println("[Add] " + operand1 + " " + operator + " " + operand2);
        return operand1 + operand2;
      case '-':
        System.out.println("[Subtract] " + operand1 + " " + operator + " " + operand2);
        return operand1 - operand2;
      case '*':
        System.out.println("[Multiply] " + operand1 + " " + operator + " " + operand2);
        return operand1 * operand2;
      case '/':
        System.out.println("[Divide] " + operand1 + " " + operator + " " + operand2);
        return operand1 / operand2;
      case '%':
        System.out.println("[Modulo] " + operand2 + " " + operator + " " + operand1);
        return operand1 % operand2;
      case '^':
        System.out.println("[Exponentiation] " + operand1 + " " + operator + " " + operand2);
        num = operand1;
        for (n = 0; n < operand2 - 1; ++n)
          num *= operand1;
        return num;
      default:
        return 0;
    }
  }

  private static int logic() {
    return 0;
  }

  private static Boolean checkForDivError (String scanToken, String op1, String op2) {
    if ( (getIntegerFromString(op2) == 0) && scanToken.charAt(0) == '/') { // error check 1: check if division operator is in use and if divisor is a zero
      System.out.println("[Error] Division by zero detected!");
      return true;
    }
    return false;
  }

  private static Boolean checkForModError (String scanToken, String op1, String op2) {
    if ( (getIntegerFromString(op2) == 0) && scanToken.charAt(0) == '%') { // error check 1: check if division operator is in use and if divisor is a zero
      System.out.println("[Error] Modulo by zero detected!");
      return true;
    }
    return false;
  }
  
  public static int startEvaluatePostfix (Queue input, Stack output) {
    int i = 0;
    int result = 0;
    Boolean errorCheck = false;

    String scanToken = new String();
    String op1 = new String();
    String op2 = new String();
    String exp = new String();

    System.out.println();
    System.out.println();

    do {
      System.out.println("[Input Queue] " + i + "/" + input.getSize() + ": " + input.getValue(i)); // debugging purposes only
      scanToken = input.getValue(i);

      System.out.println("[scanToken index 0] = " + scanToken.charAt(0) );

      if (!isOperator(scanToken.charAt(0))) { // check if scanned token is an operand
        System.out.println("[Operand " + scanToken + "] " + scanToken);
        output.push(scanToken);
      }
  
      else { // check if scanned token is an operator
        System.out.println("[Operator " + scanToken + "] " + scanToken);
        op2 = output.pop();
        op1 = output.pop();
        System.out.println("[Operand A = " + op1);
        System.out.println("[Operand B = " + op2);

        errorCheck = checkForDivError(scanToken, op1, op2) && checkForModError(scanToken, op1, op2);
        System.out.println("[ErrorCheck] " + errorCheck); // debugging purposes only
        if (!errorCheck) {
          result = doArithmetic(getIntegerFromString(op1), getIntegerFromString(op2), scanToken.charAt(0));
          System.out.println("[Arithmetic Result] = " + result);
          exp = Integer.toString(result);
          output.push(exp);
        }
      }
      
      i++;

    } while (i < input.getSize() && !errorCheck);

    if (errorCheck)
      System.out.println("Division by zero error!");
    return result;
  }
}

/*
1.  Scan the postfix expression from left to right.

2.  If the scanned token is an operand, push it to the stack.

3.  If the scanned token is an operator, pop the top two values in the stack.
    Then, evaluate the operator.
    Finally, push the result back to the stack.

4.  If all tokens are processed, pop the remaining number from the stack. It is the final answer.
*/