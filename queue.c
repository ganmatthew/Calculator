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
        printf("[Enqueue] t = %s\n", q->values[q->rear]);
    }
}

void dequeue(Queue * q, int i)
{
    if (q->rear != -1)
    {
        printf("[Dequeue] t = %s\n", q->values[q->front]);
        q->rear--;
        q->count--;
        q->front++;
    }
}