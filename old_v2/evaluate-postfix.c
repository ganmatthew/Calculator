int getIntFromStrTok (Token t) {
  float n = strtof(t, NULL);
  return (int) n;
}

int doArithmetic (int operand1, int operand2, char operator) {
  int i, num;
  switch (operator) {
    case '+':
      printf("[Add] %d %c %d\n", operand1, operator, operand2);
      return operand1 + operand2;
      break;
    case '-':
      printf("[Subtract] %d %c %0d\n", operand1, operator, operand2);
      return operand1 - operand2;
      break;
    case '*':
      printf("[Multiply] %d %c %d\n", operand1, operator, operand2);
      return operand1 * operand2;
      break;
    case '/':
      printf("[Divide] %d %c %d\n", operand1, operator, operand2);
      return operand1 / operand2;
      break;
    case '%':
      printf("[Modulo] %d %c %d\n", operand2, operator, operand1);
      return operand1 % operand2;
      break;
    case '^':
      printf("[Power] %d %c %d\n", operand1, operator, operand2);
      num = operand1;
      for (i = 0; i < operand2 - 1; ++i)
        num *= operand1;
      return num;
      break;
    default:
      return 0;
      break;
  }
}

int logic() {
  return 0;
}

void startEvaluatePostfix (Queue * input, Stack * output) {
  int i = 0;
  int errorCheck = 0, result;
  Token scanToken, op1, op2, optr;

  output->counter = -1; // initialize the stack as empty
  
  printf("\n\n");
  do {
    printf("[Input Queue] %d/%d: %s\n", i, input->count, input->values[i]); // debugging purposes only
    strcpy(scanToken, input->values[i]);

    if (!isOperator(scanToken[0])) { // check if scanned token is an operand
      printf("[Operand %s] %c is an operand\n\n", scanToken, scanToken[0]);
      pushToken(output, scanToken);
    }

    else { // check if scanned token is an operator
      printf("[Operator %s] %c is an operator\n\n", scanToken, scanToken[0]);
      popToken(output, op2);
      popToken(output, op1);
      printf("[Operator #1 = %s]\n", op1);
      printf("[Operator #2 = %s]\n", op2);
      if ( (getIntFromStrTok(op2) == 0) && scanToken[0] == '/') { // error check 1: check if division operator is in use and if divisor is a zero
        errorCheck = 1;
        printf("[Error] Division by zero detected!\n");
      }
      else if ( (getIntFromStrTok(op2) == 0) && scanToken[0] == '%') { // error check 2: check if modulo operator is in use and if second number is a zero
        errorCheck = 2;
        printf("[Error] Modulo by zero detected!\n");
      }
      else {
        result = doArithmetic(getIntFromStrTok(op1), getIntFromStrTok(op2), scanToken[0]);
        printf("[Arithmetic Result] = %d\n", result);
        sprintf(optr, "%d", result);
        pushToken(output, optr);
      }
    }
    i++;
  } while (i < input->rear + 1 && errorCheck == 0);

  switch (errorCheck) {
    case 1: // error 1 or error 2
    case 2:
      printf("Division by zero error!\n");
      break;
    default: // no error
      for (i = 0; i < output->counter + 1; i++) {
        sprintf(output->values[i], "%d", getIntFromStrTok(output->values[i])); // get token and remove decimal places
       printf("[Final Result]: %d\n", getIntFromStrTok(output->values[i])); // display final result after decimal place change
    }
    break;
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