package pl.edu.pw.ee.TreeMap;

import java.util.LinkedHashMap;
import java.util.Map;

public class GapIterationListener implements IterationListener{

    private final Map<Integer, Integer> resultsMap = new LinkedHashMap<>();
    private int size;
    private final int gap;
    private int elementCounter;
    private int numberOfIterations;

    public GapIterationListener(){
        gap = 10;
        elementCounter = 0;
    }

    public GapIterationListener(int gapSize){
        if (gapSize < 0) throw new IllegalArgumentException("Gap size cannot be less than 0");
        gap = gapSize;
        elementCounter = 0;
    }
    @Override
    public void incrementNumberOfElements() {
        size++;
        elementCounter++;
    }

    @Override
    public void incrementNumberOfIteration() {
        if(elementCounter < gap){
            return;
        }
        numberOfIterations++;
    }

    @Override
    public void writeNumberOfIteration() {
        if(elementCounter == gap){
            resultsMap.put(size, numberOfIterations);
            numberOfIterations = 0;
            elementCounter = 0;
        }
    }

    @Override
    public Map<Integer, Integer> getResults() {
        return resultsMap;
    }

}
