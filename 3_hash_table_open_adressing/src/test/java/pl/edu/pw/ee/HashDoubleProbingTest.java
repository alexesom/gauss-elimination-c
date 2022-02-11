package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashDoubleProbingTest extends HashProbingTest {
    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance() {
        return new HashDoubleProbing<>();
    }

    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance(int size) {
        return new HashDoubleProbing<>(size);
    }

    @Override
    protected String getTableName() {
        return "hashDoubleProbing";
    }
}
