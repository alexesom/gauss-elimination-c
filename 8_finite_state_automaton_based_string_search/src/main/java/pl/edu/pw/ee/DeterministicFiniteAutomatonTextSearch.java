package pl.edu.pw.ee;

import pl.edu.pw.ee.services.PatternSearch;

import java.util.*;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;

public class DeterministicFiniteAutomatonTextSearch implements PatternSearch {

    private String pattern;
    private Map<Key, Integer> transMap;

    public DeterministicFiniteAutomatonTextSearch(String pattern) {
        validateInput(pattern);

        this.pattern = pattern;
        buildTransitionMatrix();
    }

    @Override
    public int findFirst(String text) {
        validateInput(text);
        int n = text.length();
        int acceptedState = pattern.length();
        int result = -1;

        int state = 0;

        for (int i = 0; i < n; i++) {
            if (pattern.contains(String.valueOf(text.charAt(i))))
                try {
                    state = transMap.get(new Key(state, text.charAt(i)));
                } catch (Exception e) {
                    System.out.println("There is no such state for this letter");
                }

            else
                state = 0;

            if (state == acceptedState) {
                result = i - acceptedState + 1;
                break;
            }
        }

        return result;
    }

    @Override
    public int[] findAll(String text) {
        validateInput(text);

        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < text.length() - 1; i++) {
            int temp = findFirst(text.substring(i, text.length()));

            if (temp != -1) {
                list.add(temp+i);
                i += pattern.length() - 1;
            }
        }

        return list.stream().mapToInt(Integer::intValue).distinct().toArray();
    }

    private void validateInput(String txt) {
        if (txt == null || txt.length() == 0) {
            throw new IllegalArgumentException("Input text cannot be null!");
        }
    }

    private void buildTransitionMatrix() {
        transMap = new HashMap<>();

        int m = pattern.length();
        List<Character> alphabet = getAlphabetOfPattern();

        for (int q = 0; q <= m; q++) {
            for (char sign : alphabet) {

                int k = min(m + 1, q + 2);
                k--;

                while (k > 0 && !isSuffixOfPattern(k, q, sign)) {
                    k--;
                }
                System.out.println(String.format("sigma(%d, %c) = %d\n", q, sign, k));
                transMap.put(new Key(q, sign), k);
            }
        }
    }

    private List<Character> getAlphabetOfPattern() {
        List<Character> signs = pattern.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .collect(toList());

        return signs;
    }

    private boolean isSuffixOfPattern(int kIndex, int qIndex, char sign) {
        boolean isSuffix = false;

        if (pattern.charAt(--kIndex) == sign) {
            isSuffix = true;

            while (kIndex > 0) {
                kIndex--;
                qIndex--;

                if (pattern.charAt(kIndex) != pattern.charAt(qIndex)) {
                    isSuffix = false;
                    break;
                }
            }
        }

        return isSuffix;
    }

    private class Key {
        private int state;
        private char sign;

        public Key(int state, char sign) {
            this.state = state;
            this.sign = sign;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Key)) {
                return false;
            }
            Key key = (Key) o;
            return state == key.state && sign == key.sign;
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, sign);
        }
    }

}
