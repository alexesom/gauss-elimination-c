package pl.edu.pw.ee.tables;

import org.junit.Test;
import pl.edu.pw.ee.HashListChaining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class CreateHashListChainingTables {
    @Test
    public void hashListChainingCreateTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.100000.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            String[] readWords = new String[100000];
            for (int i = 0; i < 100000; i++) {
                readWords[i] = reader.readLine();
            }
            for (int i = 12, counter = 1; i < 19; i++, counter++) {
                int sizeOfHashTable = (int) Math.pow(2, i);
                HashListChaining<String> hashTable = new HashListChaining<>(sizeOfHashTable);
                long[] timeFindArray = new long[30];
                for (int testI = 0; testI < 30; testI++) {
                    for (int j = 0; j < 100000; j++) {
                        hashTable.add(readWords[j]);
                    }
                    long start;
                    long end;

                    start = System.nanoTime();
                    for (String word : readWords) {
                        hashTable.get(word);
                    }
                    end = System.nanoTime();

                    timeFindArray[testI] = end - start;
                }

                Arrays.sort(timeFindArray);

                long averageTime = 0;
                for (int j = 10; j < 20; j++) {
                    averageTime += timeFindArray[j];
                }
                averageTime /= 10;

                writer.write(String.format("%-8d %-8d %-10d\n", counter, sizeOfHashTable, averageTime));
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}