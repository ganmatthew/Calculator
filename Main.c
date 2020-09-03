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
  Queue infix, postfix;
  Stack result;
  Token equation;

  do {
    printf("\n");
    scanf("%s", equation);

    startInfixToPostfix(&infix, &postfix, equation);
    startEvaluatePostfix(&postfix, &result);

  } while (strcmp(equation, "QUIT") != 0);

  return 0;
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