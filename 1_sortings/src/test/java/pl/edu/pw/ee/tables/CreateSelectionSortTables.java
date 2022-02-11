package pl.edu.pw.ee.tables;

import org.junit.Test;
import pl.edu.pw.ee.QuickSort;
import pl.edu.pw.ee.SelectionSort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class CreateSelectionSortTables {
    @Test
    public void createOptimisticDataTable() {
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/SelectionSortOptimistic.txt"))) {
            int degreeOfTwo = 0;
            Random random = new Random(111);

            for (int j = 0; j < 20; j++) {
                System.out.println(j);
                degreeOfTwo = (int) Math.pow(2, j);
                double[] array = new double[degreeOfTwo];

                for (int i = 0; i < degreeOfTwo; i++) {
                    array[i] = i;
                }

                double[] sortedArray = array.clone();
                Arrays.sort(sortedArray);

                SelectionSort obj = new SelectionSort();
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
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/SelectionSortPessimistic.txt"))) {
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


                SelectionSort obj = new SelectionSort();
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
        try (BufferedWriter fileWrite = new BufferedWriter(new FileWriter("testsData/SelectionSortRandom.txt"))) {
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


                SelectionSort obj = new SelectionSort();
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
