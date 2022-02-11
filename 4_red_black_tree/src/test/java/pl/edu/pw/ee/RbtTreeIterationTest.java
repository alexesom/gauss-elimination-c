package pl.edu.pw.ee;

import org.junit.Test;

import java.io.*;
import java.util.Map;

import static org.junit.Assert.fail;

public class RbtTreeIterationTest {

    @Test
    public void should_ReturnCorrectIterationTest_WhenNotExist() {
        RedBlackTree<String, String> rbt = new RedBlackTree<>();
        rbt.addListener(new GapIterationListener(200));

        try (BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            String word = "";
            while ((word = reader.readLine()) != null) {
                for (int i = 1; i <= 10; i++) {
                    rbt.put(word + i, word + i);
                }
            }
            IterationListener iterationListener = rbt.getIterationListener();
            Map<Integer, Integer> results = iterationListener.getResults();


            results.forEach((size, iter) -> {
                try {
                    writer.write(String.format("%s %12s\n", size, iter));
                } catch (IOException e) {
                    fail(e.getMessage());
                }
            });

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}