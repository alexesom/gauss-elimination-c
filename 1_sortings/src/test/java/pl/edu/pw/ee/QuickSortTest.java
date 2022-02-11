package pl.edu.pw.ee;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class QuickSortTest {
    @Test
    public void zeroElementArrayTest() {
        double[] array = new double[0];

        double[] sortedArray = new double[0];

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void oneElementArrayTest() {
        double[] array = new double[1];
        array[0] = 2;

        double[] sortedArray = new double[1];
        sortedArray[0] = 2;

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void pessimisticDataTest() { // when pivot is the largest element in the whole array
        double[] array = new double[200];

        for (int j = 0, i = 199; i >= 0; i--, j++) {
            array[j] = i;
        }

        double[] sortedArray = new double[200];

        for (int j = 0, i = 0; i < 200; i++, j++) {
            sortedArray[j] = i;
        }

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void optimisticDataTest() { // when the pivot always goes right smack in the middle
        int countOfElements = 10000;
        Random random = new Random(111);

        Double[] arrayWrapper = new Double[countOfElements];

        for (int i = 0; i < countOfElements; i++) {
            arrayWrapper[i] = Integer.MAX_VALUE * random.nextDouble();
        }

        Double[] sortedArrayWrapper = arrayWrapper.clone();
        double[] sortedArray = new double[countOfElements];
        for (int i = 0; i < countOfElements; i++) {
            sortedArray[i] = sortedArrayWrapper[i];
        }

        Arrays.sort(sortedArrayWrapper);
        Arrays.sort(sortedArray);
        Double middleElement = sortedArrayWrapper[(countOfElements - 1) / 2];

        int indexOfMiddleElement = Arrays.asList(arrayWrapper).indexOf(middleElement);

        double tmp = arrayWrapper[0];
        arrayWrapper[0] = arrayWrapper[indexOfMiddleElement];
        arrayWrapper[indexOfMiddleElement] = tmp;

        double[] array = new double[countOfElements];
        for (int i = 0; i < countOfElements; i++) {
            array[i] = arrayWrapper[i];
        }

        QuickSort obj = new QuickSort();
        obj.sort(array);
        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void sortWithNegativeNumbersTest() {
        double[] array = {3, 5, 7, 10, -5, 4, 2, 6, 9, 8, 1, -12, -1};

        double[] sortedArray = {-12, -5, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }

    @Test
    public void exceptionTest() {
        assertThrows(Exception.class, () -> {
            QuickSort obj = new QuickSort();
            obj.sort(null);
        });
    }

    @Test
    public void nonorderedDataTest() {
        double[] array = {3, 5, 7, 10, 4, 2, 6, 9, 8, 1};

        double[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }


    @Test
    public void randomDataTest() {
        Random generator = new Random(100);
        double[] array = new double[100000];
        double[] sortedArray = new double[100000];

        for (int i = 0; i < 100000; i++) {
            array[i] = generator.nextDouble();
            sortedArray[i] = array[i];
        }

        Arrays.sort(sortedArray);

        QuickSort obj = new QuickSort();
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

            QuickSort obj = new QuickSort();
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

        QuickSort obj = new QuickSort();
        obj.sort(array);

        assertArrayEquals(sortedArray, array, 0);
    }
}
