#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAXSIZE 256 
//typedef char Token[11];

typedef struct Stack
{
    char * values[MAXSIZE]; 
    int counter;

}Stack;

int main()
{
    Stack s;
    s.counter = -1;
    int size;
    int i;

    char equation[] = "2^6/(5*4)+10";

    size = strlen(equation);

    for (i = 0; i < MAXSIZE; i++)
    {
        s.values[i] = malloc(size);
        if (s.values[i] == NULL)
        {
            exit(1);
        }
    }

}