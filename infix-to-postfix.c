/*
    Infix to Postfix Converter
*/

void InfixToPostfix(Token * t, int start, int end, Queue * output) //token array in infix //output is th output queue 
{
    Stack op;

    int j;
    int i;

    //Token * tp;

    op.counter = -1;

    //printf("e");

    for (i = start; i < end; i++)
    {
        //printf("%s", t[i]);

        if (isOperator(t[i][0]) == 0)
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

                while (getOperatorPrecedence(t[i]) <= getOperatorPrecedence(op.values[op.counter]) && op.counter != -1)
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


void startInfixToPostfix (Queue *input, Queue *output, Token equation) {
    char * c1;
    char * c2;
    char tempc;
    int i;

    Token tempToken;

    input->front = 0;
    input->rear = -1;
    input->count = 0;

    output->front = 0;
    output->rear = -1;
    output->count = 0;

    c1 = &equation[0];
    
    while (*c1 != '\0')
    {
        if (isOperator(*c1) == 0) // if the element is a number
        {
            c2 = c1;
            while (isOperator(*c2) == 0 && *c2 != '\0') c2++; //c2 should point to the next actual operator
            
            
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
            while (isOperator(*c2) == 1 && *c2 != '\0' && *c2 == *c1) c2++;
            //special cases (!= <= >=)
            if ((*c1 == '!' && *c2 == '=') || (*c1 == '<' && *c2 == '=') || (*c1 == '>' && *c2 == '='))
                c2++;

            tempc = *c2;
            *c2 = '\0';
            
            strcpy(tempToken, c1);
            *c2 = tempc;
            c1 = c2;
        }
        enqueue(input, tempToken);
    }

    InfixToPostfix(input->values, input->front, input->count, output);

    for (i = 0; i < output->rear + 1; i++)
    {
       printf("%s ", output->values[i]);
    }
}