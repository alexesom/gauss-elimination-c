#include "gauss.h"
#include "backsubst.h"
#include "mat_io.h"
#include <stdlib.h>
#include <stdio.h>


int main(int argc, char ** argv) {
	int res;
	Matrix * A = readFromFile(argv[1]);
	Matrix * b = readFromFile(argv[2]);
	Matrix * x;

	if (A == NULL) return -1;
	if (b == NULL) return -2;
	/*printToScreen(A);
	printToScreen(b);*/

	if((A->c != A->r) || (b->r != A->r) || (b->c != 1))
	{
		fprintf(stderr,"Błąd 2 - błąd nieprawidłowych rozmiarów macierzy\n\n");
		exit(0);
	}
	res = eliminate(A,b);
	if(res)
	{
		fprintf(stderr,"Błąd 1 - macierz osobliwa - dzielenie przez 0\n\n");
		exit(0);
	}

	x = createMatrix(b->r, 1);
	if (x != NULL) {
		res = backsubst(x,A,b);
		if(res == 1) 
		{
			fprintf(stderr,"Błąd 1 - błąd dzielenia przez 0 (element na diagonali = 0)\n\n");
			exit(0);
		}
		if(res == 2) 
		{
			fprintf(stderr,"Błąd 2 - błąd nieprawidłowych rozmiarów macierzy\n\n");
			exit(0);
		}
		/*printf("\nChanged matrices A and b\n");
		printToScreen(A);
		printToScreen(b);
		printf("\n\n");*/
		printf("OK.\n\nX matrix\n");
		printToScreen(x);
		printf("\n");

	  freeMatrix(x);
	} else {
					fprintf(stderr,"Błąd! Nie mogłem utworzyć wektora wynikowego x.\n");
	}

	freeMatrix(A);
	freeMatrix(b);

	return 0;
}
