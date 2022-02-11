package pl.edu.pw.ee;

import pl.edu.pw.ee.services.MinSpanningTree;

public class KruskalAlgorithmTest extends AlgorithmTest{
    @Override
    protected MinSpanningTree CreateInstance() {
        return new KruskalAlgorithm();
    }
}
