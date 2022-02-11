package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class HashProbingTest {
    protected abstract HashTable<? extends Comparable<?>> CreateInstance();

    protected abstract HashTable<? extends Comparable<?>> CreateInstance(int size);

    protected abstract String getTableName();

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> hash = (HashTable<Double>) CreateInstance(initialSize);

        // then
        assert false;
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = (HashTable<String>) CreateInstance();
        String newElement = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newElement);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenExistInHashTable() {
        // given
        HashTable<String> emptyHash = (HashTable<String>) CreateInstance();
        String newElement = "nothing special";
        emptyHash.put(newElement);

        // when
        int nOfElemsBeforeSearch = getNumOfElems(emptyHash);
        emptyHash.put(newElement);
        int nOfElemsAfterSearch = getNumOfElems(emptyHash);

        // then
        assertEquals(1, nOfElemsBeforeSearch);
        assertEquals(1, nOfElemsAfterSearch);
    }

    @Test
    public void nullElementPutExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            HashTable<String> hashList = (HashTable<String>) CreateInstance();
            hashList.put(null);
        });
    }

    @Test
    public void nullElementGetExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            HashTable<String> hashList = (HashTable<String>) CreateInstance();
            hashList.put("Hello, World");
            hashList.get(null);
        });
    }

    @Test
    public void nullElementDeleteExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            HashTable<String> hashList = (HashTable<String>) CreateInstance();
            hashList.put("Hello, World");
            hashList.delete(null);
        });
    }

    @Test
    public void deleteAbsentElementExceptionTest() {
        assertThrows(NullPointerException.class, () -> {
            HashTable<String> hashList = (HashTable<String>) CreateInstance();
            hashList.put("Hello, World");
            hashList.put("Hello, World Alex");
            hashList.put("Hello, World Everyone!!!");
            hashList.delete("sdfsfd");
        });
    }

    @Test
    public void should_CorrectlyDeleteElements_WhenExistInTable() {
        HashTable<Double> hashList = (HashTable<Double>) CreateInstance();
        Random random = new Random(System.nanoTime());
        List<Double> valueList = new ArrayList<>();

        long start = System.nanoTime();
        long end = System.nanoTime();
        for (int i = 0; i < Integer.MAX_VALUE / 1000; i++) {
            Double temp = random.nextDouble();
            hashList.put(temp);
            valueList.add(temp);
        }

        int sizeBeforeDelete = getNumOfElems(hashList);

        for (int i = 0; i < Integer.MAX_VALUE / 1000; i++) {
            hashList.delete(valueList.get(i));
        }

        int sizeAfterDelete = getNumOfElems(hashList);

        assertEquals(Integer.MAX_VALUE / 1000, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }

    @Test
    public void createComparingTables() {
        Random random = new Random(System.nanoTime());
        List<Long> listOfTimePutting = new ArrayList<>();
        List<Long> listOfTimeGetting = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.100000.txt"));
             BufferedWriter fileWrite = new BufferedWriter(new FileWriter(getTableName() + ".txt"))) {
            fileWrite.write(String.format("%-5s %-15s %-15s %s\n", "Lp.", "Start hashsize",
                    "Avg.time put", "Avg.time get"));
            List<String> readWords = new ArrayList<>();
            String newReadLine = "";
            while ((newReadLine = reader.readLine()) != null) {
                readWords.add(newReadLine);
            }

            for (int sizeIndex = 1, size = 512; sizeIndex <= 10; sizeIndex++, size = size * 2) {
                HashTable<String> hashTable = (HashTable<String>) CreateInstance(size);
                for (int i = 0; i < 30; i++) {
                    long start = System.nanoTime();
                    for (String readWord : readWords) {
                        hashTable.put(readWord);
                    }
                    long end = System.nanoTime();

                    listOfTimePutting.add(end - start);

                    start = System.nanoTime();
                    for (String readWord : readWords) {
                        hashTable.get(readWord);
                    }
                    end = System.nanoTime();

                    listOfTimeGetting.add(end - start);
                }

                Collections.sort(listOfTimePutting);
                Collections.sort(listOfTimeGetting);
                long putAverage = listOfTimePutting.subList(9, 19).stream().reduce(0L, Long::sum);
                long getAverage = listOfTimeGetting.subList(9, 19).stream().reduce(0L, Long::sum);
                fileWrite.write(String.format("%-5d %-15d %-15d %d\n", sizeIndex, size, putAverage, getAverage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            return field.getInt(hash);

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
