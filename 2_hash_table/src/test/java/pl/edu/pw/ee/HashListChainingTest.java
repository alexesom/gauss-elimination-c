package pl.edu.pw.ee;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashListChainingTest {
    @Test
    public void addToEmptyHashExceptionTest() {
        assertThrows(ArrayStoreException.class, () -> {
            HashListChaining<String> list = new HashListChaining<>(0);
            list.add("some string");
        });
    }

    @Test
    public void addToHashNullExceptionTest() {
        assertThrows(NullPointerException.class, () -> {
            HashListChaining<String> list = new HashListChaining<>(10);
            list.add(null);
        });
    }

    @Test
    public void getFromEmptyHashExceptionTest() {
        assertThrows(ArrayStoreException.class, () -> {
            HashListChaining<String> list = new HashListChaining<>(0);
            list.get("some string");
        });
    }

    @Test
    public void countLoadFactorEmptyHashExceptionTest() {
        assertThrows(ArrayStoreException.class, () -> {
            HashListChaining<String> list = new HashListChaining<>(0);
            list.countLoadFactor();
        });
    }

    @Test
    public void hashListChainingInsertingTest() {
        HashListChaining<String> hashList = new HashListChaining<>(10);
        for (int i = 0; i < 1000; i++) {
            hashList.add(new String(new char[i]).replace("\0", "a"));
        }
    }

    @Test
    public void hashListChainingNullInsertingTest() {
        assertThrows(NullPointerException.class, () -> {
            HashListChaining<String> hashList = new HashListChaining<>(10);
            hashList.add(null);
        });
    }

    @Test
    public void hashListChainingEmptyDeleteExceptionTest() {
        assertThrows(NullPointerException.class, () -> {
            HashListChaining<Integer> hashList = new HashListChaining<>(0);
            hashList.delete(1);
        });
    }

    @Test
    public void hashListChainingNullDeleteExceptionTest() {
        assertThrows(ArrayStoreException.class, () -> {
            HashListChaining<String> hashList = new HashListChaining<>(10);
            for (int i = 0; i < 100; i++) {
                hashList.add(new String(new char[i]).replace("\0", "a"));
            }
            hashList.delete(null);
        });
    }

    @Test
    public void hashListChainingSearchingTest() {
        HashListChaining<String> hashList = new HashListChaining<>(100);
        String[] searchArray = new String[1000];
        String[] searchArrayExpected = new String[1000];
        for (int i = 0; i < 1000; i++) {
            hashList.add(new String(new char[i]).replace("\0", "a"));
            searchArrayExpected[i] = new String(new char[i]).replace("\0", "a");
        }

        for (int i = 0; i < 1000; i++) {
            String searchString = new String(new char[i]).replace("\0", "a");
            searchArray[i] = hashList.get(searchString);
        }
        assertArrayEquals(searchArrayExpected, searchArray);
    }
}