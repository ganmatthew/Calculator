/*
    Queue data structure
*/

typedef struct Queue
{
    Token values[MAXSIZE];
    int front, rear, count;

}Queue;