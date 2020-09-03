int getIntFromNumTok (Token t) {
  int i, num = 0;
    for (i = 0; i < strlen(t); i++) {
      num = num * 10 + (t[i] - 48);
    }
    printf("\n[getIntFromNumTok] string %s -> int %d\n", t, num);
    return num;
}

int arithmetic (int operand1, int operand2, char operator) {
  int i;
  printf("[Arithmetic] %d %c %d\n", operand1, operator, operand2);
  switch (operator) {
    case '+':
      return operand1 + operand2;
      break;
    case '-':
      return operand1 - operand2;
      break;
    case '*':
      return operand1 * operand2;
      break;
    case '/':
      return operand1 / operand2;
      break;
    case '%':
      return operand1 % operand2;
      break;
    case '^':
      for (i = 0; i < operand2; i++)
        operand1 *= operand1;
      return operand1;
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
  int i;
  int zeroDivError = 0;
  Token scanned, op1, op2, temp;

  output->counter = -1;
  
  printf("\n\n");
  for (i = 0; i < input->rear + 1; i++) {
    printf("[Input Queue] %d/%d: %s\n", i, input->count, input->values[i]); // debugging purposes only
    strcpy(scanned, input->values[i]);

    if (!isOperator(scanned[0])) { // check if scanned token is an operand
      printf("[Operand] %c is an operand\n\n", scanned[0]);
      pushToken(output, scanned);
    }

    else { // check if scanned token is an operator
      printf("[Operator] %c is an operator\n\n", scanned[0]);
      popToken(output, op2);
      popToken(output, op1);
      if ( (getIntFromNumTok(op2) == 0) && scanned[0] == '/') {
        zeroDivError = 1;
        printf("[Error] Division by zero detected!\n");
      }
      else {
        printf("[Arithmetic Result] = %d\n", arithmetic(getIntFromNumTok(op1), getIntFromNumTok(op2), scanned[0]));
        sprintf(temp, "%d", arithmetic(getIntFromNumTok(op2), getIntFromNumTok(op1), scanned[0]));
        pushToken(output, temp);
      }
      
    }
  }

  if (!zeroDivError) {
    for (i = 0; i < output->counter + 1; i++)
      printf("[Final Result]: %s\n", output->values[i]); // debugging purposes only
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