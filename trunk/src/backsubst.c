#include "backsubst.h"
/**
 * Zwraca 0 - wsteczne podstawienie zakonczone sukcesem
 * Zwraca 1 - błąd dzielenia przez 0 (element na diagonali = 0)
 * Zwraca 2 - błąd nieprawidłowych rozmiarów macierzy
 */
int  backsubst(Matrix *x, Matrix *mat, Matrix *b) 
{	
	if((mat->c != mat->r) || (x->r != mat->r) || (x->c != 1) || (b->r != mat->r) || (b->c != 1)) return 2;

	int n = mat->r;
	

	if(mat->data[n-1][n-1] != 0)
		x->data[n-1][0] = b->data[n-1][0]/mat->data[n-1][n-1];
	else return 1;


	for(int i = n-2; i >= 0; i--)
	{
		if(mat->data[i][i] == 0) return 1;
		double sum = 0;
		for (int j = i+1; j <= n-1; j++) 
		sum += mat->data[i][j] * x->data[j][0];

		x->data[i][0] = (b->data[i][0] - sum)/mat->data[i][i];		
	}
	
	return 0;
}


