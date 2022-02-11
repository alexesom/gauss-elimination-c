package pl.edu.pw.ee;

import pl.edu.pw.ee.services.MinSpanningTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm extends MSTAlgorithm implements MinSpanningTree {
    List<List<String>> grove;
    List<Edge> resultEdges;

    public static void main(String[] args) throws IOException {
        KruskalAlgorithm k = new KruskalAlgorithm();
        String resourcesPath = "src/test/resources/";

        k.findMST(resourcesPath + "small_data.txt");
    }

    @SafeVarargs
    public static <T> List<T> asForest(T... items) {
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, items);

        return list;
    }

    @Override
    public String findMST(String pathToFile) throws IOException {
        nodesList = readListWithNodes(pathToFile);

        Collections.sort(nodesList);

        grove = new ArrayList<>();
        resultEdges = new ArrayList<>();

        List<String> uniqueNodes = getUniqueNodesList();

        GraphConnectivity graphConnectivity = new GraphConnectivity(nodesList, uniqueNodes);
        if(!graphConnectivity.checkConnectivity())
            throw new IllegalArgumentException("Graph is disconnected");

        for (String tree : uniqueNodes) {
            grove.add(asForest(tree));
        }

        for (Edge currentEdge : nodesList) {
            List<String> firstForest = findForestWithTree(currentEdge.firstNode);
            List<String> secondForest = findForestWithTree(currentEdge.secondNode);

            if (firstForest == null || secondForest == null)
                throw new IllegalArgumentException("One forest is null");

            if (firstForest != secondForest) {
                List<String> newForest = new ArrayList<>();

                newForest.addAll(firstForest);
                newForest.addAll(secondForest);

                grove.remove(firstForest);
                grove.remove(secondForest);

                grove.add(newForest);
                resultEdges.add(currentEdge);
            }
        }


        return getStringFromGraph(resultEdges);
    }

    private List<String> findForestWithTree(String searchTree) {
        for (List<String> forest : grove) {
            for (String tree : forest) {
                if (tree.equals(searchTree))
                    return forest;
            }
        }

        return null;
    }
}
