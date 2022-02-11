package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MSTAlgorithm {
    protected List<Edge> nodesList;

    protected List<Edge> readListWithNodes(String pathToFile) throws IOException {
        File file = new File(pathToFile);

        if (!file.isFile())
            throw new IOException("File is not valid");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String newLine = "";
        List<Edge> nodesList = new ArrayList<>();

        while ((newLine = reader.readLine()) != null) {
            ArrayList<Object> splitArray = new ArrayList<>(List.of(newLine.split(" ")));

            if (splitArray.size() != 3)
                throw new IllegalArgumentException("Wrong formatting");

            try {
                splitArray.set(2, Double.parseDouble(String.valueOf(splitArray.get(2))));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The weight couldn't be a string");
            }

            Edge newEdge = new Edge();

            newEdge.firstNode = (String) splitArray.get(0);
            newEdge.secondNode = (String) splitArray.get(1);
            newEdge.weight = (Double) splitArray.get(2);

            if(newEdge.firstNode.equals(newEdge.secondNode))
                throw new IllegalArgumentException("Cannot add edge with the same node name");

            for (Edge edge : nodesList) {
                if (edge.equals(newEdge))
                    throw new IllegalArgumentException("The Data With Duplicates");
            }

            nodesList.add(newEdge);
        }

        return nodesList;
    }

    protected List<String> getUniqueNodesList() {
        List<String> resultList = new ArrayList<>();
        for (Edge edge : nodesList) {
            resultList.add(edge.firstNode);
            resultList.add(edge.secondNode);
        }
        return resultList.stream().distinct().sorted().collect(Collectors.toList());
    }

    protected String getStringFromGraph(List<Edge> minTreeListWithEdges) {
        StringBuilder result = new StringBuilder();
        for (Edge edge : minTreeListWithEdges) {
            result.append(edge.getFirstNode()).append("_").append(edge.getWeight()).append("_").append(edge.getSecondNode()).append(" | ");
        }

        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    protected List<Edge> getAllEdgesWithExactNode(String node) {
        List<Edge> resultList = new ArrayList<>();
        for (Edge edge : nodesList) {
            if (edge.firstNode.equals(node))
                resultList.add(edge);
        }

        for (Edge edge : nodesList) {
            if (edge.secondNode.equals(node))
                resultList.add(new Edge(edge.secondNode, edge.firstNode, edge.weight));
        }

        return resultList;
    }
}
