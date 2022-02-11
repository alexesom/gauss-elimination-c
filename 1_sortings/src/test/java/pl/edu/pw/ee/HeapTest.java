package pl.edu.pw.ee;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertThrows;

public class HeapTest {
    @Test
    public void heapPutTest() {
        Heap<Double> heap = new Heap<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10000; j++) {
                Random random = new Random(111);
                heap.put(random.nextDouble());
            }
        }
    }

    @Test
    public void heapPopEmptyTest() {
        Heap<Double> heap = new Heap<>();
        assertThrows(Exception.class, heap::pop);
    }

    @Test
    public void heapPopTest() {
        Heap<Double> heap = new Heap<>();
        for (int j = 0; j < 10000; j++) {
            Random random = new Random(111);
            heap.put(random.nextDouble());
        }
        for (int j = 0; j < 10000; j++) {
            Random random = new Random(111);
            heap.pop();
        }
    }

}
