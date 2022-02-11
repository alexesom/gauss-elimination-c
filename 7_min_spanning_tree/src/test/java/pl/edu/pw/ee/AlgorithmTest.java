package pl.edu.pw.ee;

import org.junit.Test;
import pl.edu.pw.ee.services.MinSpanningTree;

import java.io.IOException;

public abstract class AlgorithmTest {
    MinSpanningTree algorithm;
    String resourcesPath = "src/test/resources/";

    AlgorithmTest() {
        this.algorithm = CreateInstance();
    }

    protected abstract MinSpanningTree CreateInstance();

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenDataIsDuplicated() throws IOException {
        algorithm.findMST(resourcesPath + "not_valid_data_duplicated_data.txt");

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenDataDoesNotContainsWeights() throws IOException {
        algorithm.findMST(resourcesPath + "not_valid_data_absent_weight.txt");

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenDataContainsNeedlessData() throws IOException {
        algorithm.findMST(resourcesPath + "not_valid_data_something_else.txt");

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenFileWithDataIsEmpty() throws IOException {
        algorithm.findMST(resourcesPath + "not_valid_data_something_else.txt");

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenGraphIsDisconnected() throws IOException {
        algorithm.findMST(resourcesPath + "not_valid_data_graph_is_disconnected.txt");
    }

    @Test
    public void should_PrintResult_WhenDataIsValid() throws IOException {
        String algorithmResult = algorithm.findMST(resourcesPath + "small_data.txt");
        System.out.println(algorithmResult);

        algorithmResult = algorithm.findMST(resourcesPath + "small_data_1.txt");
        System.out.println(algorithmResult);

        algorithmResult = algorithm.findMST(resourcesPath + "small_data_2.txt");
        System.out.println(algorithmResult);
    }

}
