float getFloatFromNumTok (Token t) {
  return strtof(t, NULL);
}

float doArithmetic (float operand1, float operand2, char operator) {
  float i, num;
  int op1, op2;
  switch (operator) {
    case '+':
      printf("[Add] %0.3f %c %0.3f\n", operand1, operator, operand2);
      return operand1 + operand2;
      break;
    case '-':
      printf("[Subtract] %0.3f %c %0.3f\n", operand1, operator, operand2);
      return operand1 - operand2;
      break;
    case '*':
      printf("[Multiply] %0.3f %c %0.3f\n", operand1, operator, operand2);
      return operand1 * operand2;
      break;
    case '/':
      printf("[Divide] %0.3f %c %0.3f\n", operand1, operator, operand2);
      return operand1 / operand2;
      break;
    case '%':
      printf("[Modulo] %0f %c %0f\n", operand2, operator, operand1);
      op1 = (int) operand1;
      op2 = (int) operand2;
      return op1 % op2;
      break;
    case '^':
      printf("[Power] %0.3f %c %0.3f\n", operand1, operator, operand2);
      num = operand1;
      for (i = operand2; i > 0; i--)
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
  int zeroDivError = 0;
  float result;
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
      if ( (getFloatFromNumTok(op2) == 0) && scanToken[0] == '/') { // check if division operator is in use and if divisor is a zero
        zeroDivError = 1;
        printf("[Error] Division by zero detected!\n");
      }
      else {
        result = doArithmetic(getFloatFromNumTok(op1), getFloatFromNumTok(op2), scanToken[0]);
        printf("[Arithmetic Result] = %0.3f\n", result);
        sprintf(optr, "%0.3f", result);
        pushToken(output, optr);
      }
    }
    i++;
  } while (i < input->rear + 1 && !zeroDivError);

  if (!zeroDivError) {
    for (i = 0; i < output->counter + 1; i++) {
      sprintf(output->values[i], "%.0f", getFloatFromNumTok(output->values[i])); // get token and remove decimal places
      printf("[Final Result]: %s\n", output->values[i]); // display final result after decimal place change
    }
  }
  else {
    printf("Division by zero error!\n");
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