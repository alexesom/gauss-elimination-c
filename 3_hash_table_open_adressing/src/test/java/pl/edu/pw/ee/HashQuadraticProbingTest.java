package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HashQuadraticProbingTest extends HashProbingTest {
    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance() {
        return new HashQuadraticProbing<>();
    }

    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance(int size) {
        return new HashQuadraticProbing<>(size);
    }

    @Override
    protected String getTableName() {
        return "hashQuadraticProbing";
    }

    @Test
    public void createComparingTables() {
        List<Long> listOfTimePutting = new ArrayList<>();
        List<Long> listOfTimeGetting = new ArrayList<>();
        List<Integer> constantsAList = new ArrayList<>();
        List<Integer> constantsBList = new ArrayList<>();
        List<Long> putResults = new ArrayList<>();
        List<Long> getResults = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.100000.txt"));
             BufferedWriter fileWrite = new BufferedWriter(new FileWriter(getTableName() + ".txt"))) {

            List<String> readWords = new ArrayList<>();
            String newReadLine = "";
            while ((newReadLine = reader.readLine()) != null) {
                readWords.add(newReadLine);
            }
            for (int indexConst = 0; indexConst < 10; indexConst++) {
                constantsAList.add((int) Math.pow(2, indexConst));
                constantsBList.add((int) Math.pow(3, indexConst + 1));
            }


            fileWrite.write(String.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                    "Lp.", "Start hashsize", "Avg.time put", "", "", "", "", "", "", "", "", "", "Avg.time get", "", "", "", "", "", "", "", "", ""));

            fileWrite.write(String.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                    "", "",
                    "a=" + constantsAList.get(0) + " b=" + constantsBList.get(0),
                    "a=" + constantsAList.get(1) + " b=" + constantsBList.get(1),
                    "a=" + constantsAList.get(2) + " b=" + constantsBList.get(2),
                    "a=" + constantsAList.get(3) + " b=" + constantsBList.get(3),
                    "a=" + constantsAList.get(4) + " b=" + constantsBList.get(4),
                    "a=" + constantsAList.get(5) + " b=" + constantsBList.get(5),
                    "a=" + constantsAList.get(6) + " b=" + constantsBList.get(6),
                    "a=" + constantsAList.get(7) + " b=" + constantsBList.get(7),
                    "a=" + constantsAList.get(8) + " b=" + constantsBList.get(8),
                    "a=" + constantsAList.get(9) + " b=" + constantsBList.get(9),

                    "a=" + constantsAList.get(0) + " b=" + constantsBList.get(0),
                    "a=" + constantsAList.get(1) + " b=" + constantsBList.get(1),
                    "a=" + constantsAList.get(2) + " b=" + constantsBList.get(2),
                    "a=" + constantsAList.get(3) + " b=" + constantsBList.get(3),
                    "a=" + constantsAList.get(4) + " b=" + constantsBList.get(4),
                    "a=" + constantsAList.get(5) + " b=" + constantsBList.get(5),
                    "a=" + constantsAList.get(6) + " b=" + constantsBList.get(6),
                    "a=" + constantsAList.get(7) + " b=" + constantsBList.get(7),
                    "a=" + constantsAList.get(8) + " b=" + constantsBList.get(8),
                    "a=" + constantsAList.get(9) + " b=" + constantsBList.get(9)
            ));

            for (int sizeIndex = 1, size = 512; sizeIndex <= 10; sizeIndex++, size = size * 2) {
                fileWrite.write(String.format("%-5s %-15s ", sizeIndex, size));

                for (int indexConst = 0; indexConst < 10; indexConst++) {
                    HashTable<String> hashTable = new HashQuadraticProbing<String>(size,
                            constantsAList.get(indexConst), constantsBList.get(indexConst));

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
                    putResults.add(putAverage);
                    getResults.add(getAverage);
                }
                fileWrite.write(String.format("%-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d %-15d\n",
                        putResults.get(0), putResults.get(1), putResults.get(2), putResults.get(3), putResults.get(4),
                        putResults.get(5), putResults.get(6), putResults.get(7), putResults.get(8), putResults.get(9),

                        getResults.get(0), getResults.get(1), getResults.get(2), getResults.get(3), getResults.get(4),
                        getResults.get(5), getResults.get(6), getResults.get(7), getResults.get(8), getResults.get(9)
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
