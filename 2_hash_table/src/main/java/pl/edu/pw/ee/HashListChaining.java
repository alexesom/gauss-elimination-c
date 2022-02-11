package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {
    private final Elem<T>[] hashElements;
    private int nElem;

    public HashListChaining(int size) {
        hashElements = (Elem<T>[]) new Elem[size];
        initializeHash();
    }

    @Override
    public void add(T value) {
        if (hashElements.length == 0)
            throw new ArrayStoreException("You cannot insert element in hash list with 0 places");
        if (value == null) throw new NullPointerException("You cannot add null");

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> oldElem = hashElements[hashId];
        while (oldElem != null && oldElem.next != null) {
            if (oldElem.value == value)
                return;
            oldElem = oldElem.next;
        }
        if (oldElem == null) {
            hashElements[hashId] = new Elem<>(value, hashElements[hashId]);
        } else {
            if (oldElem.value == value)
                return;
            oldElem.next = new Elem<>(value, null);
        }
        nElem++;
    }

    @Override
    public T get(T value) {
        if (hashElements.length == 0) throw new ArrayStoreException("You cannot get an object from empty hash table");

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElements[hashId];

        while (elem != null && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != null ? elem.value : null;
    }

    public void delete(T value) {
        if (hashElements.length == 0)
            throw new NullPointerException("You cannot delete an object from empty hash table");
        if (value == null)
            throw new ArrayStoreException("You cannot delete a null");

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> currentElem = hashElements[hashId];
        if (currentElem.value == value) {
            hashElements[hashId] = currentElem.next;
        } else {
            while (currentElem.next != null) {
                if (currentElem.next.value == value) {
                    currentElem.next = currentElem.next.next;
                    nElem--;
                }
            }
        }
    }

    public double countLoadFactor() {
        if (hashElements.length == 0) throw new ArrayStoreException("You cannot get load factor of an empty array");
        double size = hashElements.length;
        return nElem / size;
    }

    private void initializeHash() {
        int n = hashElements.length;

        for (int i = 0; i < n; i++) {
            hashElements[i] = null;
        }
    }

    private int countHashId(int hashCode) {
        if (hashElements.length == 0)
            throw new ArrayStoreException("You cannot get a count hash id if the hash table is empty");
        int n = hashElements.length;
        return Math.abs(hashCode) % n;
    }

    private class Elem<V> {
        private V value;
        private Elem<V> next;

        Elem(V value, Elem<V> nextElem) {
            this.value = value;
            this.next = nextElem;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Elem<V> getNext() {
            return next;
        }

        public void setNext(Elem<V> next) {
            this.next = next;
        }
    }

}
