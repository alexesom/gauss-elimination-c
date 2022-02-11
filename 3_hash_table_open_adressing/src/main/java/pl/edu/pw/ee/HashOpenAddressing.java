package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

import java.lang.reflect.Array;

public abstract class HashOpenAddressing<T extends Comparable<T>> implements HashTable<T> {

    private final double correctLoadFactor;
    private int size;
    private int nElems;
    private Elem[] hashElems;

    HashOpenAddressing() {
        this(2027); // initial size as random prime number
    }

    HashOpenAddressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (Elem[]) Array.newInstance(Elem.class, size);
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int hashId = findFreeBucketHashTable(newElem, this.hashElems, this.size);

        if (hashId == -1) {
            resizeHashTable();
            hashId = findFreeBucketHashTable(newElem, this.hashElems, this.size);
        }

        if (hashId == -1)
            throw new IllegalStateException("Your function is unsuitable for this algorithm");

        if (hashElems[hashId] == null || hashElems[hashId].isDeleted()) {
            hashElems[hashId] = new Elem(newElem);
            nElems++;
        }

        if (hashElems[hashId] != null && hashElems[hashId].value.equals(newElem))
            hashElems[hashId].value = newElem;
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int hashId = findingElementInHashTable(elem);

        if (hashId != -1 && !(hashElems[hashId].isDeleted()) && hashElems[hashId].value.equals(elem)) {
            return hashElems[hashId].value;
        }

        return null;
    }

    @Override
    public void delete(T elem) {
        if (elem == null) throw new IllegalArgumentException("Your cannot delete null");
        if (this.get(elem) == null) throw new NullPointerException("Element you're searching for is absent");

        int hashId = findingElementInHashTable(elem);

        if (hashElems[hashId].value.equals(elem)) {
            hashElems[hashId].isDeleted = true;
            nElems--;
        }
    }

    private int findingElementInHashTable(T elem) {
        validateInputElem(elem);
        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int counter = 0;
        boolean isFound = false;

        while (counter <= size - 1) {
            if (hashElems[hashId] != null && hashElems[hashId].value.equals(elem)) {
                isFound = true;
                break;
            }
            i = (i + 1) % size;
            counter++;
            hashId = hashFunc(key, i);
        }

        return isFound ? hashId : -1;
    }

    private int findFreeBucketHashTable(T newElem, Elem[] arrayOfElems, int sizeOfArray) {
        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        boolean resizeNeeded = false;

        while (arrayOfElems[hashId] != null
                && !(arrayOfElems[hashId].isDeleted())
                && !arrayOfElems[hashId].value.equals(newElem)) {
            if ((i + 1 == sizeOfArray)) {
                resizeNeeded = true;
                break;
            }
            i++;
            hashId = hashFunc(key, i);
        }
        if (resizeNeeded)
            return -1;

        return hashId;
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    public int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            resizeHashTable();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void resizeHashTable() {
        //resize hash table with the first prime number from range (size*2, size*4),
        // there always be a number in this range

        this.size = firstPrimeNumberFromRange(size * 2, size * 4);
        Elem[] newHashElems = (Elem[]) Array.newInstance(Elem.class, this.size);
        nElems = 0;

        for (Elem elem : this.hashElems) {
            if (elem != null && !(elem.isDeleted))
                putNotInInnerHashTable(newHashElems, elem.value, this.size);
        }


        this.hashElems = newHashElems;
    }

    private void putNotInInnerHashTable(Elem[] newHashElems, T newElem, int sizeOfArray) {
        int hashId = findFreeBucketHashTable(newElem, newHashElems, sizeOfArray);

        if (hashId == -1)
            throw new IllegalStateException("Your function is unsuitable for this algorithm");

        if (newHashElems[hashId] == null || newHashElems[hashId].isDeleted) {
            newHashElems[hashId] = new Elem(newElem);
            nElems++;
        }

        if (newHashElems[hashId].value.equals(newElem))
            newHashElems[hashId].value = newElem;
    }

    private int firstPrimeNumberFromRange(int lower, int upper) { //
        if (lower < 0) throw new IllegalArgumentException();
        int i, j, flag;

        for (i = lower; i <= upper; i++) {

            if (i == 1 || i == 0)
                continue;

            flag = 1;

            for (j = 2; j <= i / 2; ++j) {
                if (i % j == 0) {
                    flag = 0;
                    break;
                }
            }

            if (flag == 1)
                return i;

        }

        return 0;
    }

    private class Elem {

        T value;
        boolean isDeleted = false;

        Elem(T value) {
            this.value = value;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public T getValue() {
            return value;
        }
    }
}