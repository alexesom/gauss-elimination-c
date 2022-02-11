package pl.edu.pw.ee;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class InsertionSortTest {
    @Test
    public void zeroElementArrayTest() {
        double[] array = new double[0];

        double[] sortedArray = new double[0];

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void oneElementArrayTest() {
        double[] array = new double[1];
        array[0] = 2;

        double[] sortedArray = new double[1];
        sortedArray[0] = 2;

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void pessimisticDataTest() { // when algorithm needs to compare each element with each other
        double[] array = new double[200];

        for (int j = 0, i = 199; i >= 0; i--, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[200];

        for (int j = 0, i = 0; i < 200; i++, j++) {
            sortedArray[j] = i;
        }

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void optimisticDataTest() {  // when array is already sorted
        double[] array = new double[200];

        for (int j = 0, i = 0; i < 200; i++, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[200];

        for (int j = 0, i = 0; i < 200; i++, j++) {
            sortedArray[j] = i;
        }

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void sortWithNegativeNumbersTest() {
        double[] array = { 3.1, 5.99, 7, 10, -5, 4, 2, 6, 9, 8, 1.01, -12.5, -1 };

        double[] sortedArray = { -12.5, -5, -1, 1.01, 2, 3.1, 4, 5.99, 6, 7, 8, 9, 10 };

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void exceptionTest() {
        assertThrows(Exception.class, () -> {
            InsertionSort obj = new InsertionSort();
            obj.sort(null);
        });
    }

    @Test
    public void nonorderedDataTest() {
        double[] array = { 3, 5, 7, 10, 4, 2, 6, 9, 8, 1 };

        double[] sortedArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void randomDataTest() {
        Random generator = new Random(555);
        double[] array = new double[100000];
        double[] sortedArray = new double[100000];

        for (int i = 0; i < 100000; i++) {
            array[i] = generator.nextDouble();
            sortedArray[i] = array[i];
        }

        Arrays.sort(sortedArray);

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void bigRandomDataTest() {
        for (int j = 0; j <= 100; j++) {
            long n = System.currentTimeMillis();
            Random generator = new Random(n);
            double[] array = new double[100000];
            double[] sortedArray = new double[100000];

            for (int i = 0; i < 100000; i++) {
                array[i] = generator.nextDouble();
                sortedArray[i] = array[i];
            }

            Arrays.sort(sortedArray);

            InsertionSort obj = new InsertionSort();
            obj.sort(array);

            assertArrayEquals(sortedArray, array, 0);
        }
    }

    @Test
    public void onlyEvenNumbersDecreasingTest() {
        double[] array = new double[10];

        for (int j = 0, i = 20; i >= 2; i -= 2, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[10];

        for (int j = 0, i = 2; i <= 20; i += 2, j++) {
            sortedArray[j] = i;
        }

        InsertionSort obj = new InsertionSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }
}
