#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define INF 99

void
arrayReverse(int*, int );

double
dijkstra(double**, int, int, int);

void showMatrix(double** , int );

int main(int argc, char* argv[])
{
    int numberOfEdges;
    int numberOfNodes;
    int fromPoint, toPoint;


    FILE* file = argc == 2 ? fopen(argv[1], "r") : stdin;
    srand(time(NULL));

    printf("Number of nodes : \n");
    fscanf(file, "%d", &numberOfNodes);
    printf("Number of edges : \n");
    fscanf(file, "%d", &numberOfEdges);

    double** adjacencyMatrix = (double**)malloc(numberOfNodes * sizeof(double*));

    for (int i = 0; i < numberOfNodes; i++)
    {
        adjacencyMatrix[i] = (double*)malloc(numberOfNodes * sizeof(double));
        for (int j = 0; j < numberOfNodes; j++)
            adjacencyMatrix[i][j] = INF;

    }

    printf("Type from-point and to-point for every edge: \n");

    for (int i = 0; i < numberOfEdges; i++)
    {
        fromPoint = toPoint = 0;
        fscanf(file, "%d %d", &fromPoint, &toPoint);
        if(fromPoint == toPoint - 1 || fromPoint == toPoint + 1 || fromPoint == toPoint + 5 || fromPoint == toPoint - 5)
            adjacencyMatrix[fromPoint - 1][toPoint - 1] = ((double)rand() / RAND_MAX) * 10;
        else
            return -1;
    }

    fromPoint = toPoint = 0;
    printf("\nEnter the source: ");
    scanf("%d", &fromPoint);
    printf("\nEnter the target: ");
    scanf("%d", &toPoint);
    printf("\nThe Shortest Path: %lg", dijkstra(adjacencyMatrix, fromPoint , toPoint , numberOfNodes));
    showMatrix(adjacencyMatrix, numberOfNodes);
    free(adjacencyMatrix);
}




void
showMatrix(double** arr, int nodeNum ){
    printf("\n\n");
    for (int i = 0; i < nodeNum; i++)
    {
        printf("%d: [", i + 1);

        for (int j = 0; j < nodeNum; j++)
        {
            if (j + 1 == nodeNum)
            {
                printf("%2.5lg", arr[i][j]);
            }
            else
                printf("%2.5lg, ", arr[i][j]);
        }
        printf("]\n");
    }
}

double
dijkstra(double** matrix, int source, int target, int numOfNodes)
{
    source--;
    target--;
    double result = 0.0;
    double* distanceToEachNode = (double*)malloc(numOfNodes * sizeof(double));
    int* selectedNode    = (int*)malloc(numOfNodes * sizeof(int));
    int* previousNode = (int*)malloc(numOfNodes * sizeof(int));
    int start = 0;
    double d = 0;
    int j = 0;
    int m = 0;
    double min = 0;
    int *path = (int*)malloc(numOfNodes * sizeof(int));

    for (int i = 0; i < numOfNodes; i++)
    {
        distanceToEachNode[i] = INF;
        previousNode[i] = -1;
        selectedNode[i] = 0;
    }

    start = source;
    selectedNode[start] = 1;
    distanceToEachNode[start] = 0;
    while (selectedNode[target] == 0)
    {
        min = INF;
        m = 0;

        for (int i = 0; i < numOfNodes; i++)
        {
            d = distanceToEachNode[start] + matrix[start][i];
            if (d < (double)distanceToEachNode[i] && selectedNode[i] == 0)
            {
                distanceToEachNode[i] = d;
                previousNode[i] = start;
            }
            if (min > (double)distanceToEachNode[i] && selectedNode[i] == 0)
            {
                min = distanceToEachNode[i];
                m = i;
            }
        }
        start = m;
        selectedNode[start] = 1;
    }
    start = target;
    j = 0;
    path[0] = '\0';
    while (start != -1)
    {
        path[j++] = start+1;
        start = previousNode[start];
    }
    arrayReverse(path , numOfNodes);
    printf("\nThe path to the target :" );

    for(int i = 0 ; i < numOfNodes ; i++)
        if (i+1 == numOfNodes && path[i] != 0)
            printf(" %d " , path[i]);
        else if(path[i] != 0)
                printf(" %d ->" , path[i]);


    result = distanceToEachNode[target];
    free(distanceToEachNode);
    free(path);
    free(previousNode);
    free(selectedNode);
    return result;
}

void
arrayReverse(int *arr, int size)
{
    for (int i = 0; i < size/2; i++)
    {
        int temp = arr[i];
        arr[i] = arr[size - 1 - i];
        arr[size - 1 - i] = temp;
    }
}