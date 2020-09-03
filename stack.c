/*
    Stack-related Functions
*/

void pushToken(Stack * s, Token t)
{
    if (s->counter != MAXSIZE)
    {
        s->counter++;
        printf("[pushToken] t = %s\n", s->values[s->counter]);
        strcpy(s->values[s->counter], t);    
    }
    else
    {
        printf("Stack is full!\n");
    }
    
}


int popToken(Stack * s, Token t)
{
    if (s->counter != -1)
    {
        strcpy(t, s->values[s->counter]);
        s->counter--;
        printf("[popToken] t = %s\n", s->values[s->counter]);
        return 1;
    }
    else
    {
        printf("Stack is empty!\n");
        return 0;
    }
    
}
