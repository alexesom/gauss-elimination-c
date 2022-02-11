package pl.edu.pw.ee;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class HeapSortTest {
    HeapSort obj = new HeapSort();

    @Test
    public void zeroElementArrayTest() {
        double[] array = new double[0];

        double[] sortedArray = new double[0];

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void oneElementArrayTest() {
        double[] array = new double[1];
        array[0] = 2;

        double[] sortedArray = new double[1];
        sortedArray[0] = 2;

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void pessimisticDataTest() { // in heap sort pessimistic is O(n*log(n)) and optimistic is O(n*log(n)) too
        double[] array = new double[20000];

        for(int j = 0, i = 19999; i >= 0; i--, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[20000];

        for(int j = 0, i = 0; i < 20000; i++, j++) {
            sortedArray[j] = i;
        }

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void optimisticDataTest() {
        double[] array = new double[20000];

        for(int j = 0, i = 1; i <= 20000; i++, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[20000];

        for(int j = 0, i = 1; i <= 20000; i++, j++) {
            sortedArray[j] = i;
        }

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void sortWithNegativeNumbersTest() {
        double[] array = { 3., 5., 7., 10., -5., 4., 2., 6., 9., 8., 1., -12., -1. };

        double[] sortedArray = {-12., -5., -1., 1., 2., 3., 4., 5., 6., 7., 8., 9., 10. };

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void exceptionTest() {
        assertThrows(Exception.class, () -> {
            obj.sort(null);
        });
    }

    @Test
    public void nonorderedDataTest() {
        double[] array = {3, 5, 7, 10, 4, 2, 6, 9, 8, 1};

        double[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }


    @Test
    public void randomDataTest() {
        Random generator = new Random(100);
        double[] array = new double[100000];
        double[] sortedArray = new double[100000];

        for(int i = 0; i < 100000; i++) {
            array[i] = generator.nextDouble();
            sortedArray[i] = array[i];
        }

        Arrays.sort(sortedArray);


        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void bigRandomDataTest() {
        for( int j = 0; j <= 10000; j++) {
            long n = System.currentTimeMillis();
            Random generator = new Random(n);
            double[] array = new double[10000];
            double[] sortedArray = new double[10000];

            for(int i = 0; i < 10000; i++) {
                array[i] = generator.nextDouble();
                sortedArray[i] = array[i];
            }

            Arrays.sort(sortedArray);

            obj.sort(array);

            assertArrayEquals(sortedArray, array, 0);
        }
    }

    @Test
    public void onlyEvenNumbersDecreasingTest() {
        double[] array = new double[10];

        for(int j = 0, i = 20; i >= 2; i -= 2, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[10];

        for(int j = 0, i = 2; i <= 20; i += 2, j++) {
            sortedArray[j] = i;
        }

        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }
}
