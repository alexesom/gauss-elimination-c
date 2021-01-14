#include "gauss.h"

/**
 * Zwraca 0 - elimnacja zakonczona sukcesem
 * Zwraca 1 - macierz osobliwa - dzielenie przez 0
 */
int eliminate(Matrix *mat, Matrix *b)
{
		int n = mat->r;			
		for(int k = 0 ; k <= n-2; k++)
		{
			if(mat->data[k][k] == 0 || mat->data[k+1][k+1] == 0) return 1;
			for(int i = k+1; i <= n-1; i++)
			{
				if(mat->data[i][k] == 0) continue;
				double fact = mat->data[k][k] / mat->data[i][k];
				for(int j = k; j <= n-1; j++)
				{
					mat->data[i][j] = mat->data[k][j] - mat->data[i][j]*fact;
				}
				b->data[i][0] = b->data[k][0] - b->data[i][0]*fact;				
			}
		}
		return 0;
}

