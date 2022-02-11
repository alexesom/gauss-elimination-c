package pl.edu.pw.ee;

import java.util.Map;

public interface IterationListener {
    void incrementNumberOfIteration();

    void incrementNumberOfElements();

    void writeNumberOfIteration();

    Map<Integer, Integer> getResults();
}
