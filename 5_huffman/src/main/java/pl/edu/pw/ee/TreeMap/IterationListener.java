package pl.edu.pw.ee.TreeMap;

import java.util.Map;

public interface IterationListener {
    void incrementNumberOfIteration();

    void incrementNumberOfElements();

    void writeNumberOfIteration();

    Map<Integer, Integer> getResults();
}
