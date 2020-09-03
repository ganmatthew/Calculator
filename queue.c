/*
    Queue-related Functions
*/

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