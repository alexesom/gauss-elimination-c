package pl.edu.pw.ee;

import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithmTest extends AlgorithmTest {
    @Override
    protected MinSpanningTree CreateInstance() {
        return new PrimAlgorithm();
    }
}
