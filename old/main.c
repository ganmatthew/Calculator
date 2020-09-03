#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAXSIZE 256

typedef char String[11];

typedef struct Stack
{
    String values[MAXSIZE]; 
    int counter;

}Stack;

typedef struct Queue
{
    String values[MAXSIZE];
    int front, rear;

}Queue;

int operatorValue(String t)
{
    if (strcmp(t, "^") == 0 || strcmp(t, "!"))
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


void InititalizeStack(Stack * s)
{
    s->counter = -1;
}

void InitializeQueue(Queue * q)
{
    q->rear = -1;
    q->front = 0;
}

void PushStack(Stack * s, String t)
{
    if (s->counter != MAXSIZE)
    {
        s->counter++;
        strcpy(s->values[s->counter], t);
    }
}

void Enqueue(Queue * q, String s)
{
    if (q->rear != MAXSIZE)
    {
        q->rear++;
        strcpy(q->values[q->rear], s);
        
    }
}

void Dequeue(Queue * q)
{
    if (q->rear != q->front - 1)
    {
        q->front++;
    }
}

void PopStack(Stack * s)
{
    if (s->counter != -1)
    {
        s->counter--;
    }
}

//out = output queue
//s = string array containing the tokens
//start = starting index
//end = ending index
void PostFix2(Queue * out, String * s, int start, int end)
{
    Stack op;
    InititalizeStack(&op);

    int j;
    int i;

    //printf("hi");

    for (i = start; i <= end; i++)
    {
        if (isOperand(s[i][0]) == 0)
        {
            Enqueue(out, s[i]);
        }
        else
        {
            //printf("%s", s[i]);
            if (strcmp(s[i], "(") == 0)
            {
                j = end; 
                while (strcmp(s[j], ")") != 0) j--;
                //printf("- %s %s -", s[i + 1], s[j - 1]);
                PostFix2(out, s, i + 1, j - 1);
                i = j;
            }
            else if (strcmp(s[i], ")") != 0)
            {

                while (operatorValue(s[i]) <= operatorValue(op.values[op.counter]) && op.counter != -1)
                {
                    Enqueue(out, op.values[op.counter]);
                    PopStack(&op);
                }

                PushStack(&op, s[i]);

               
            }

        }
        
    }

    while (op.counter != -1)
    {
        //printf("%s", op.values[op.counter]);
        Enqueue(out, op.values[op.counter]);
        PopStack(&op);
    }
}

void PostFix(String inputStr)
{   
    
    Queue out;
    Queue in;

    InitializeQueue(&out);
    InitializeQueue(&in);

    int i;

    char * c1;
    char * c2;
    char temp;

    c1 = &inputStr[0];

    //tokenize each operator/operand in the input string
    

    while (*c1 != '\0')
    {
        if (isOperand(*c1) == 0) // if the element is a number
        {
            c2 = c1;
            while (isOperand(*c2) == 0 && *c2 != '\0') c2++; //c2 should point to the next actual operand
                        
            temp = *c2; //store it to a temp;
            *c2 = '\0'; //null

            //printf("%s", c1);
            Enqueue(&in, c1);
            *c2 = temp;
        
            c1 = c2;
        }
        else
        {
            c2 = c1;
            while (isOperand(*c2) == 1 && *c2 != '\0' && *c2 == *c1) c2++;
            //special cases (!= <= >=)
            if ((*c1 == '!' && *c2 == '=') || (*c1 == '<' && *c2 == '=') || (*c1 == '>' && *c2 == '='))
                c2++;

            temp = *c2;
            *c2 = '\0';
        
            Enqueue(&in, c1);
            *c2 = temp;
            c1 = c2;
        }
    }

    //printf("%s", in.values[0]);
    //then convert to postfix
    PostFix2(&out, in.values, in.front, in.rear);

    for (i = 0; i <= out.rear; i++)
    {
        printf("%s ", out.values[i]);
    }

}

int main()
{
    char InputEquation[MAXSIZE];
    
    do
    {   
        scanf("%s", InputEquation);
        PostFix(InputEquation);
        printf("\n\n");
    }
    while (strcmp(InputEquation, "QUIT") != 0);


    return 0;
}