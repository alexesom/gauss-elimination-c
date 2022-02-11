package pl.edu.pw.ee;

public class HashLinearProbing<T extends Comparable<T>> extends HashOpenAddressing<T> {

    public HashLinearProbing() {
        super();
    }

    public HashLinearProbing(int size) {
        super(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        if (m == 0) throw new IllegalArgumentException("You couldn't get a hash if size is zero");

        int hash = (key % m + i) % m;

        hash = hash < 0 ? -hash : hash;

        return hash;
    }
}