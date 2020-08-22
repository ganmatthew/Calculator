#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAXSIZE 256 
typedef char Token[11];

typedef struct Stack
{
    Token values[MAXSIZE]; 
    int counter;

}Stack;

typedef struct Queue
{
    Token values[MAXSIZE];
    int front, rear, count;

}Queue;

void pushToken(Stack * s, Token t)
{
    if (s->counter != MAXSIZE)
    {
        s->counter++;
        strcpy(s->values[s->counter], t);    
    }
    else
    {
        printf("Full!\n");
    }
    
}

void popToken(Stack * s)
{
    if (s->counter != -1)
    {
        s->counter--;
    }
    
}

void enqueue(Queue * q, Token t)
{
    if (q->rear != MAXSIZE)
    {
        q->rear++;
        q->count++;
        strcpy(q->values[q->rear], t);
    }
}

void dequeue(Queue * q, int i)
{
    if (q->rear != -1)
    {
        q->rear--;
        q->count--;
        q->front++;
    }
}

int operatorValue(Token t)
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


int isOperand(char c)
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



void InfixToPostfix(Token * t, int start, int end, Queue * output) //token array in infix //output is th output queue 
{
    Stack op;

    int j;
    int i;

    Token * tp;

    op.counter = -1;

    //printf("e");

    for (i = start; i < end; i++)
    {
        //printf("%s", t[i]);

        if (isOperand(t[i][0]) == 0)
        {
            enqueue(output, t[i]);
        }
        else
        {
            
            if (strcmp(t[i], "(") == 0)
            {
                j = i; 
                while (strcmp(t[j], ")") != 0) j++;
                //printf("- %s %s -", t[i + 1], t[j - 1]);
                InfixToPostfix(t, i + 1, j, output);
                i = j;
            }
            else if (strcmp(t[i], ")") != 0)
            {

                while (operatorValue(t[i]) <= operatorValue(op.values[op.counter]) && op.counter != -1)
                {
                    enqueue(output, op.values[op.counter]);
                    op.counter--;
                }

                pushToken(&op, t[i]);

               
            }

        


        }
    }

    //printf("remaining: \n\n");

    while (op.counter != -1)
    {
        //printf("%s", op.values[op.counter]);
        enqueue(output, op.values[op.counter]);
        op.counter--;
    }
}

int main()
{
    char equation[] = "2^6/(5*4)+10";
    char * c1;
    char * c2;
    char tempc;
    int i;

    Token tempToken;

    Queue input;
    Queue output;

    input.front = 0;
    input.rear = -1;
    input.count = 0;

    output.front = 0;
    output.rear = -1;
    output.count = 0;

    c1 = &equation[0];
    
    while (*c1 != '\0')
    {
        if (isOperand(*c1) == 0) // if the element is a number
        {
            c2 = c1;
            while (isOperand(*c2) == 0 && *c2 != '\0') c2++; //c2 should point to the next actual operand
            
            
            tempc = *c2; //store it to a temp;
            *c2 = '\0'; //null

            //printf("%s  ", c1);

            strcpy(tempToken, c1);
            *c2 = tempc;
        
            c1 = c2;
        }
        else
        {
            c2 = c1;
            while (isOperand(*c2) == 1 && *c2 != '\0' && *c2 == *c1) c2++;
            //special cases (!= <= >=)
            if ((*c1 == '!' && *c2 == '=') || (*c1 == '<' && *c2 == '=') || (*c1 == '>' && *c2 == '='))
                c2++;

            tempc = *c2;
            *c2 = '\0';
            
            strcpy(tempToken, c1);
            *c2 = tempc;
            c1 = c2;
        }
        enqueue(&input, tempToken);
    }

    

    InfixToPostfix(input.values, input.front, input.count, &output);

    printf("\n\n");

    for (i = 0; i < output.rear + 1; i++)
    {
       printf("%s ", output.values[i]);
    }

    return 0;
}