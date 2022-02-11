package pl.edu.pw.ee;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequenceTest {
    LongestCommonSubsequence seq;

    @Test
    public void should_CorrectlyDisplayTable() {
        seq = new LongestCommonSubsequence("LOKOMOTYWA", "KOMPOTY");
        seq.display();
        System.out.println(seq.findLCS());

        System.out.println();

        seq = new LongestCommonSubsequence("abc", "defg");
        seq.display();
        System.out.println(seq.findLCS());

        System.out.println();

        seq = new LongestCommonSubsequence("często_z_odkrywaniem", "rzeczy_nie_trzeba\n_się_spieszyć");
        seq.display();
        System.out.println(seq.findLCS());

        System.out.println();

    }

    @Test
    public void should_CorrectlyFindLongestCommonSubsequence_WhenLongestCommonSubsequenceExists() {
        seq = new LongestCommonSubsequence("LOKOMOTYWA", "KOMPOTY");
        assertEquals("KOMOTY", seq.findLCS());

        seq = new LongestCommonSubsequence("ABCDEFGHIJ", "KLMNOPQRST");
        assertEquals("", seq.findLCS());

        seq = new LongestCommonSubsequence("ABC", "AB");
        assertEquals("AB", seq.findLCS());

        seq = new LongestCommonSubsequence("THAT'S A HARD CHOSE", "");
        assertEquals("", seq.findLCS());
    }

    @Test(expected = NullPointerException.class)
    public void should_ThrowException_WhenFirstStringIsNull() throws NullPointerException{
        seq = new LongestCommonSubsequence("ABCDEFGHIJ", null);
    }

    @Test(expected = NullPointerException.class)
    public void should_ThrowException_WhenSecondStringsAreNull() throws NullPointerException{
        seq = new LongestCommonSubsequence(null, "ADASDASD");
    }

    @Test(expected = NullPointerException.class)
    public void should_ThrowException_WhenTwoStringsAreNull() throws NullPointerException{
        seq = new LongestCommonSubsequence(null, null);
    }
}
