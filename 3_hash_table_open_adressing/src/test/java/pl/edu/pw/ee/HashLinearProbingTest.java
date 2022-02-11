package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingTest extends HashProbingTest {
    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance() {
        return new HashLinearProbing<>();
    }

    @Override
    protected HashTable<? extends Comparable<?>> CreateInstance(int size) {
        return new HashLinearProbing<>(size);
    }

    @Override
    protected String getTableName() {
        return "hashLinearProbing";
    }
}
