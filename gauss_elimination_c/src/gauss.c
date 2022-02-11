#include "gauss.h"

/**
 * Zwraca 0 - elimnacja zakonczona sukcesem
 * Zwraca 1 - macierz osobliwa - dzielenie przez 0
 */
int eliminate(Matrix *mat, Matrix *b)
{
	
		int n = mat->r;			
		for(int k = 0 ; k < n-1; k++)
		{
			int index_elem_max = k;
			double elem_max = fabs(mat->data[k][k]);
			for (int m = k+1; m <= n-1; m++)
			{
				if (fabs(mat->data[m][k]) > elem_max)
				{
				elem_max = fabs(mat->data[m][k]);
				index_elem_max = m;
				}
			}
			if(elem_max == 0) return 1;

			if(index_elem_max != k)
			{
				double *arr = mat->data[k];
				double *tmp = b->data[k];

				mat->data[k] = mat->data[index_elem_max];
				mat->data[index_elem_max] = arr;

				b->data[k] = b->data[index_elem_max];
				b->data[index_elem_max] = tmp;
			}

			for(int i = k+1; i < n; i++)
			{
				if(mat->data[i][k] == 0) continue;

				double fact = mat->data[k][k] / mat->data[i][k];

				for(int j = k; j < n; j++)
				{
					mat->data[i][j] = mat->data[k][j] - mat->data[i][j]*fact;
				}
				b->data[i][0] = b->data[k][0] - b->data[i][0]*fact;				
			}
			if(mat->data[k][k] == 0) return 1;
		}
		return 0;
}
/*



			

*/
