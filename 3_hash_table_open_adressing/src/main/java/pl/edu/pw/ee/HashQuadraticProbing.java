package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAddressing<T> {
    private final int a;
    private final int b;

    HashQuadraticProbing() {
        super();
        this.a = 123;
        this.b = 321;
    }

    HashQuadraticProbing(int size) {
        super(size);
        this.a = 123;
        this.b = 321;
    }

    HashQuadraticProbing(int size, int a, int b) {
        super(size);
        this.a = a;
        this.b = b;
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();

        if (m == 0) throw new IllegalArgumentException("You couldn't get a hash if size is zero");

        int hash = (key % m + a * i + b * i * i) % m;

        hash = hash < 0 ? -hash : hash;

        return hash;
    }
}
