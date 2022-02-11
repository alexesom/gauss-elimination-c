package pl.edu.pw.ee;

import org.junit.Test;

import java.util.Arrays;

public class DeterministicFiniteAutomatonTextSearchTest {
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenPatternIsEmpty() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch("AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenPatternIsNull() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch(null);
    }

    @Test
    public void should_ReturnCorrect_WhenPatternDuplicatedInLine() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch("AB");
        int[] arr = a.findAll("ABABAB");
        int[] arrRes = {0, 2, 4};
        assert(Arrays.equals(arr, arrRes));
    }

    @Test
    public void should_ReturnCorrect_WhenPatternInTheEnd() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch("ABCD");
        int res = a.findFirst("ABABABCD");
        assert(res == 5);
    }

    @Test
    public void should_ReturnCorrect_WhenNotExistFindFirst() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch("DS");
        int res = a.findFirst("ABABAB");
        assert(res == -1);
    }


    @Test
    public void should_ReturnCorrect_WhenNotExistFindAll() {
        DeterministicFiniteAutomatonTextSearch a = new DeterministicFiniteAutomatonTextSearch("DS");
        int[] arr = a.findAll("ABABAB");
        int[] arrRes = {};
        assert(Arrays.equals(arr, arrRes));
    }
}
