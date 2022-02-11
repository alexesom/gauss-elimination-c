package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HeapInterface;

import java.util.ArrayList;
import java.util.List;

public class Heap<T extends Comparable<T>> implements HeapInterface<T> {
    private final List<T> heapElements;
    private int heapSize = 0;

    public Heap() {
        heapElements = new ArrayList<>();
    }

    @Override
    public void put(T item) {
        heapSize++;
        heapElements.add(item);
        heapUp(heapSize - 1);
    }

    @Override
    public T pop() {
        if (heapSize == 0) {
            throw new ArrayIndexOutOfBoundsException("Heap is empty, what are you doing?!");
        }

        T returningValue = heapElements.get(0);
        swap(0, heapSize - 1);
        heapElements.remove(heapSize - 1);
        heapSize--;
        heapDown(0);


        return returningValue;
    }

    private void heapUp(int indexOfElement) {
        if (heapSize != 0) {
            while (heapElements.get(indexOfElement).compareTo(heapElements.get((indexOfElement - 1) / 2)) >= 0 &&
                    (indexOfElement - 1 >= 0)) {
                swap(indexOfElement, ((indexOfElement - 1) / 2));
                indexOfElement = (indexOfElement - 1) / 2;
            }
        }
    }

    private void heapDown(int indexOfElement) {
        if (heapSize != 0) {
            while (true) {
                if (2 * indexOfElement + 2 <= heapSize - 1) {
                    if (heapElements.get(2 * indexOfElement + 1).compareTo(heapElements.get(2 * indexOfElement + 2)) >= 0 &&
                            heapElements.get(indexOfElement).compareTo(heapElements.get(2 * indexOfElement + 1)) < 0) {
                        swap(indexOfElement, 2 * indexOfElement + 1);
                        indexOfElement = 2 * indexOfElement + 1;
                    } else if (heapElements.get(2 * indexOfElement + 1).compareTo(heapElements.get(2 * indexOfElement + 2)) < 0 &&
                            heapElements.get(indexOfElement).compareTo(heapElements.get(2 * indexOfElement + 2)) < 0) {
                        swap(indexOfElement, 2 * indexOfElement + 2);
                        indexOfElement = 2 * indexOfElement + 2;
                    } else break;
                } else if (2 * indexOfElement + 1 <= heapSize - 1) {
                    if (heapElements.get(indexOfElement).compareTo(heapElements.get(2 * indexOfElement + 1)) < 0) {
                        swap(indexOfElement, 2 * indexOfElement + 1);
                        indexOfElement = 2 * indexOfElement + 1;
                    } else break;
                } else break;
            }
        }
        }

        private void swap ( int firstPosition, int secondPosition) {
            T tmp;
            tmp = heapElements.get(firstPosition);
            heapElements.set(firstPosition, heapElements.get(secondPosition));
            heapElements.set(secondPosition, tmp);
        }
}