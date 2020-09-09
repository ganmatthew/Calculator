class PostfixEval {
  private static Boolean errorCheck = false;

  public PostfixEval() {};

  private static int getIntegerFromString (String t) {
    if (t == null) {
      System.out.println("[getIntegerFromString] Converting null to 0");
      t = "0";
    }
    System.out.println("[getIntegerFromString] Converting " + t  + " into integer format");
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

  private static int getPostfixOperatorType (char c) {
    switch (c) {
      case '^':
      case '*':
      case '/':
      case '%':
      case '+':
      case '-':
        return 1;
      case '<':
      case '>':
      case '=':
      case '&':
      case '|':
      case '!':
        return 2;
      default:
        return 0;
    }
  }
   
  private static int doArithmetic (int operand1, int operand2, char operator) {
    int n, num;
    switch (operator) {
      case '+':
        //System.out.println("[Add] " + operand1 + " " + operator + " " + operand2);
        return operand1 + operand2;
      case '-':
        //System.out.println("[Subtract] " + operand1 + " " + operator + " " + operand2);
        return operand1 - operand2;
      case '*':
        //System.out.println("[Multiply] " + operand1 + " " + operator + " " + operand2);
        return operand1 * operand2;
      case '/':
        //System.out.println("[Divide] " + operand1 + " " + operator + " " + operand2);
        return operand1 / operand2;
      case '%':
        //System.out.println("[Modulo] " + operand2 + " " + operator + " " + operand1);
        return operand1 % operand2;
      case '^':
        //System.out.println("[Exponentiation] " + operand1 + " " + operator + " " + operand2);
        if (operand1 < 1) // if base is 0 or negative, return 0
          return 0;
        if (operand2 < 1) // if exponent is 0 or negative, return 1
          return 1;
        num = operand1;  
        for (n = 0; n < operand2 - 1; ++n)
          num *= operand1;
        return num;
      default:
        return 0;
    }
  }

  private static int doRelLogic (int operand1, int operand2, String operator) {
    int value = Integer.compare(operand1, operand2);
    Boolean op1 = (operand1 > 0) ? true : false;
    Boolean op2 = (operand2 > 0) ? true : false;

    switch (operator) {
      case ">":
        return (value > 0) ? 1 : 0;     // true if result is positive (greater than)
      case ">=":
        return (value >= 0) ? 1 : 0;    // true if result is 0 or positive (greater than equals)
      case "!=":
        return (op1 != op2) ? 1 : 0;    // true if result is 0 (not equals)
      case "==":
        return (value == 0) ? 1 : 0;    // true if result is 0 (equals)
      case "<=":
        return (value <= 0) ? 1 : 0;    // true if result is 0 or negative (less than equals)
      case "<":
        return (value < 0) ? 1 : 0;     // true if result is negative (less than)

      case "&&":
        return (op1 && op2) ? 1 : 0;    // true based on result after conjunction
      case "||":
        return (op1 || op2) ? 1 : 0;    // true based on result after disjunction
      default:
        return 0;
        
    }
  }

  private static int doNegation (int operand) {
    Boolean op = (operand > 0) ? false : true;
    return (op) ? 1 : 0;
  }

  private static Boolean checkForDivError (char operator, String op1, String op2) {
    if ( (getIntegerFromString(op2) == 0) && operator == '/') { // error check 1: check if division operator is in use and if divisor is a zero
      //System.out.println("[Error] Division by zero detected!");
      return true;
    }
    return false;
  }

  private static Boolean checkForModError (char operator, String op1, String op2) {
    if ( (getIntegerFromString(op2) == 0) && operator == '%') { // error check 1: check if division operator is in use and if divisor is a zero
      //System.out.println("[Error] Modulo by zero detected!");
      return true;
    }
    return false;
  }

  public static Boolean getErrorType () {
    return errorCheck;
  }
  
  public static int startEvaluatePostfix (Queue input, Stack output) {
    int i = 0;
    int result = 0;
    errorCheck = false;

    String scanToken = new String();
    String op1 = new String();
    String op2 = new String();
    String exp = new String();

    do {
      System.out.println("[Input Queue] " + i + "/" + input.getSize() + ": " + input.getValue(i)); // debugging purposes only
      scanToken = input.getValue(i);

      System.out.println("[scanToken index 0] = " + scanToken.charAt(0) );

      if (!isOperator(scanToken.charAt(0)) && getPostfixOperatorType(scanToken.charAt(0)) != '!') { // check if scanned token is an operand
        System.out.println("[Operand " + scanToken + "] " + scanToken);
        output.push(scanToken);
      }
  
      else { // check if scanned token is an operator
        System.out.println("[Operator " + scanToken + "] " + scanToken);
        op2 = output.pop();
        op1 = output.pop();

        if (getPostfixOperatorType(scanToken.charAt(0)) == '!' && scanToken.length() == 1 && op1 == null || op2 == null) {
          op1 = "0";
          op2 = "0";
        }

        System.out.println("[Operand A = " + op1);
        System.out.println("[Operand B = " + op2);

        errorCheck = (checkForDivError(scanToken.charAt(0), op1, op2) || checkForModError(scanToken.charAt(0), op1, op2));
        System.out.println("[ErrorCheck] " + errorCheck); // debugging purposes only
        if (!errorCheck) {
          switch (getPostfixOperatorType(scanToken.charAt(0))) {
            case 1:     // for arithmetic calculations
              result = doArithmetic(getIntegerFromString(op1), getIntegerFromString(op2), scanToken.charAt(0));
              break;
            case 2:     // for relational and logical calculations
              if (scanToken.charAt(0) == '!' && scanToken.length() == 1)
                result = doNegation(getIntegerFromString(op2));
              else
                result = doRelLogic(getIntegerFromString(op1), getIntegerFromString(op2), scanToken);
            default:
              break;
          }
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