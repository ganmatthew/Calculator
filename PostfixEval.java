class PostfixEval {
  private static Boolean errorCheck = false;
  private static Boolean checkForNeg = false;

  public PostfixEval() {};

  private static int getIntegerFromString (String t) {
    if (t == null) {
      t = "0";
    }
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
        return operand1 + operand2;
      case '-':
        return operand1 - operand2;
      case '*':
        return operand1 * operand2;
      case '/':
        return operand1 / operand2;
      case '%':
        return operand1 % operand2;
      case '^':
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

  private static Boolean checkForDivError (char operator, int op2) {
    if ( op2 == 0 && operator == '/') { // check if division operator is in use and if divisor is a zero
      return true;
    }
    return false;
  }

  private static Boolean checkForModError (char operator, int op2) {
    if ( op2 == 0 && operator == '%') { // check if modulo operator is in use and if second operand is a zero
      return true;
    }
    return false;
  }

  public static Boolean getErrorType () {
    return errorCheck;
  }
  
  public static int startEvaluatePostfix (Queue input, Stack output) {
    int i = 0;
    int op_1, op_2;
    int result = 0;
    int negCtr = 0;

    errorCheck = false;

    String scanToken = new String();
    String op1 = new String();
    String op2 = new String();
    String exp = new String();

    do {
      scanToken = input.getValue(i);

      if (!isOperator(scanToken.charAt(0))) // check if scanned token is an operand
        output.push(scanToken);
  
      else { // check if scanned token is an operator
        if ( (scanToken.charAt(0) != '!' && scanToken.length() == 1) || checkForNeg ) { // skips this section if it's a negation operator
          op2 = output.pop();
          op1 = output.pop();
          op_1 = getIntegerFromString(op1);
          op_2 = getIntegerFromString(op2);

          errorCheck = (checkForDivError(scanToken.charAt(0), op_2) || checkForModError(scanToken.charAt(0), op_2));
          if (!errorCheck) {
            switch (getPostfixOperatorType(scanToken.charAt(0))) {
              case 1:     // for arithmetic calculations
                result = doArithmetic(op_1, op_2, scanToken.charAt(0));
                break;
              case 2:     // for relational and logical calculations
                if (negCtr % 2 != 0) // negate result for odd-numbered reptitions of negation operator
                  result *= 0;

                if (scanToken.charAt(0) == '!' && scanToken.length() == 1)
                  result = doNegation(op_2);
                else
                  result = doRelLogic(op_1, op_2, scanToken);
              default:
                break;
            }
          }

          exp = Integer.toString(result);
          output.push(exp);
          checkForNeg = false;
        }

        else { // counts how many consecutive negation operators are there
          checkForNeg = true;
          negCtr++;
        }
      }

      if ((errorCheck || checkForNeg) && i == input.getSize() - 1)
        i = 0;
      else
        i++;
      
    } while (i < input.getSize() && !errorCheck || checkForNeg);

    if (errorCheck)
      System.out.println("Division by zero error!");

    return result;
  }
}