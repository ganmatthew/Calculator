#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXSIZE 256 
typedef char Token[11];

#include "stack.h"
#include "stack.c"

#include "queue.h"
#include "queue.c"

int getOperatorPrecedence(Token t);
int isOperator(char c);

#include "infix-to-postfix.c"
#include "evaluate-postfix.c"

int main () {
  int menuLoop = 1;

  do {
    Queue infix, postfix;
    Stack result;
    Token equation;
    
    printf("\n");
    scanf("%s", equation);

    if (strcmp(equation, "QUIT") != 0 && strcmp(equation, "quit") != 0) {
      startInfixToPostfix(&infix, &postfix, equation);
      startEvaluatePostfix(&postfix, &result);
    }
    else
      return menuLoop = 0;

  } while (menuLoop);
}

int getOperatorPrecedence(Token t)
{
    if (strcmp(t, "^") == 0)
        return 5;
    if (strcmp(t, "*") == 0 || strcmp(t, "/") == 0 || strcmp(t , "%%") == 0)
        return 4;
    if (strcmp(t, "+") == 0 || strcmp(t, "-") == 0)
        return 3;
    if (strcmp(t, "<") == 0 || strcmp(t, "<=") == 0 || strcmp(t, ">") == 0 || strcmp(t, "<="))
        return 2; 
    if (strcmp(t, "==") == 0 || strcmp(t, "!=") == 0)
        return 1;
    if (strcmp(t, "&&") == 0 || strcmp(t, "||") == 0)
        return 0;
    return -1;
}


int isOperator(char c)
{
    switch (c)
    {
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
            return 1;
    }

    return 0;
}