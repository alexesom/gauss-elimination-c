package pl.edu.pw.ee.tables;

import org.junit.Test;
import org.junit.runner.RunWith;
import pl.edu.pw.ee.QuickSort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class CreateQuickSortTables {

    @Test
    public void createOptimisticDataTable() {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/QuickSortOptimistic.txt"))) {
            int degreeOfTwo = 0;
            Random random = new Random(111);

            for (int j = 0; j < 20; j++) {
                System.out.println(j);
                degreeOfTwo = (int) Math.pow(2, j);
                Double[] arrayWrapper = new Double[degreeOfTwo];

                for (int i = 0; i < degreeOfTwo; i++) {
                    arrayWrapper[i] = Integer.MAX_VALUE * random.nextDouble();
                }

                Double[] sortedArrayWrapper = arrayWrapper.clone();
                double[] sortedArray = new double[degreeOfTwo];
                for (int i = 0; i < degreeOfTwo; i++) {
                    sortedArray[i] = sortedArrayWrapper[i];
                }

                Arrays.sort(sortedArrayWrapper);
                Arrays.sort(sortedArray);
                Double middleElement = sortedArrayWrapper[(degreeOfTwo - 1) / 2];

                int indexOfMiddleElement = Arrays.asList(arrayWrapper).indexOf(middleElement);

                double tmp = arrayWrapper[0];
                arrayWrapper[0] = arrayWrapper[indexOfMiddleElement];
                arrayWrapper[indexOfMiddleElement] = tmp;

                double[] array = new double[degreeOfTwo];
                for (int i = 0; i < degreeOfTwo; i++) {
                    array[i] = arrayWrapper[i];
                }

                QuickSort obj = new QuickSort();
                long startTime = 0;
                long endTime = 0;

                startTime = System.nanoTime();
                obj.sort(array);
                endTime = System.nanoTime();

                fileWrite.write(String.format("%-15d %d\n", degreeOfTwo, endTime-startTime));
                assertArrayEquals(sortedArray, array, 0);
            }
        }
        catch (Exception ex) {
            System.err.println("Error");
        }
    }

    @Test
    public void createPessimisticDataTable() {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/QuickSortPessimistic.txt"))) {
            int degreeOfTwo = 0;

            for (int j = 0; j < 20; j++) {
                System.out.println(j);
                degreeOfTwo = (int) Math.pow(2, j);
                double[] array = new double[degreeOfTwo];

                for (int i = 0; i < degreeOfTwo; i++) {
                    array[i] = degreeOfTwo - i;
                }

                double[] sortedArray = array.clone();

                Arrays.sort(sortedArray);


                QuickSort obj = new QuickSort();
                long startTime = 0;
                long endTime = 0;

                startTime = System.nanoTime();
                obj.sort(array);
                endTime = System.nanoTime();

                fileWrite.write(String.format("%-15d %d\n", degreeOfTwo, endTime-startTime));
                assertArrayEquals(sortedArray, array, 0);
            }
        }
        catch (Exception ex) {
            System.err.println("Error");
        }
    }

    @Test
    public void createRandomDataTable() {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/QuickSortRandom.txt"))) {
            int degreeOfTwo = 0;
            Random random = new Random(111);

            for (int j = 0; j < 20; j++) {
                System.out.println(j);
                degreeOfTwo = (int) Math.pow(2, j);
                double[] array = new double[degreeOfTwo];

                for (int i = 0; i < degreeOfTwo; i++) {
                    array[i] = random.nextDouble();
                }

                double[] sortedArray = array.clone();

                Arrays.sort(sortedArray);


                QuickSort obj = new QuickSort();
                long startTime = 0;
                long endTime = 0;

                startTime = System.nanoTime();
                obj.sort(array);
                endTime = System.nanoTime();

                fileWrite.write(String.format("%-15d %d\n", degreeOfTwo, endTime-startTime));
                assertArrayEquals(sortedArray, array, 0);
            }
        }
        catch (Exception ex) {
            System.err.println("Error");
        }
    }
}