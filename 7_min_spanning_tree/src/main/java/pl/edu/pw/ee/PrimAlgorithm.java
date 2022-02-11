package pl.edu.pw.ee;

import pl.edu.pw.ee.services.MinSpanningTree;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimAlgorithm extends MSTAlgorithm implements MinSpanningTree {

    public String findMST(String pathToFile) throws IOException, IllegalArgumentException {
        nodesList = readListWithNodes(pathToFile);

        if (nodesList.size() < 1) throw new IllegalArgumentException("Size cannot be lower than 1");

        Edge currentEdge = nodesList.get(0);
        String currentNode = currentEdge.firstNode;
        List<String> connectedNodesShouldBe = getUniqueNodesList();
        List<String> connectedNodesInResultGraph = new ArrayList<>();
        List<Edge> minTreeListWithEdges = new ArrayList<>();
        List<Edge> queueOfEdges = new ArrayList<>();

        connectedNodesInResultGraph.add(currentNode);

        GraphConnectivity graphConnectivity = new GraphConnectivity(nodesList, connectedNodesShouldBe);
        if (!graphConnectivity.checkConnectivity())
            throw new IllegalArgumentException("Graph is disconnected");

        do {
            List<Edge> edgesWithExactNode = getAllEdgesWithExactNode(currentNode);

            if (edgesWithExactNode.size() == 0)
                throw new IllegalArgumentException("Wrong Node Given");

            Collections.sort(edgesWithExactNode);

            removeDuplicatesInLists(queueOfEdges, edgesWithExactNode, minTreeListWithEdges);

            currentEdge = getNextEdge(queueOfEdges, edgesWithExactNode);

            queueOfEdges.addAll(edgesWithExactNode);

            if (currentEdge != null) {
                currentNode = currentEdge.secondNode;
                minTreeListWithEdges.add(currentEdge);
            }
            connectedNodesInResultGraph.add(currentNode);

            Collections.sort(connectedNodesInResultGraph);
            connectedNodesInResultGraph = connectedNodesInResultGraph.stream().distinct().collect(Collectors.toList());

        } while (queueOfEdges.size() != 0 || currentEdge != null
                && !connectedNodesInResultGraph.equals(connectedNodesShouldBe));

        return getStringFromGraph(minTreeListWithEdges);
    }

    private Edge getNextEdge(List<Edge> queueOfEdges, List<Edge> edgesWithExactNode) {
        Edge currentEdge = null;

        if (edgesWithExactNode.size() != 0) {
            String repeatingDestination = edgesWithExactNode.get(0).secondNode;

            List<Edge> tempList = new ArrayList<>();
            for (Edge edge : queueOfEdges) {
                if (edge.secondNode.equals(repeatingDestination)) {
                    tempList.add(edge);
                }
            }

            if (tempList.size() > 0) {
                tempList.add(edgesWithExactNode.get(0));
                Collections.sort(tempList);
                currentEdge = tempList.get(0);
            } else {
                currentEdge = edgesWithExactNode.remove(0);
            }

        } else if (queueOfEdges.size() != 0) {
            Edge returnEdge = queueOfEdges.get(queueOfEdges.size() - 1);
            Edge lastEdgeAfterReturn = null;

            List<Edge> listWithShowsFirstNode = new ArrayList<>();
            List<Edge> listWithShowsSecondNode = new ArrayList<>();

            for (Edge edge : queueOfEdges) {
                if (edge.firstNode.equals(returnEdge.firstNode)) {
                    listWithShowsFirstNode.add(edge);
                }
            }

            for (Edge edge : queueOfEdges) {
                if (edge.secondNode.equals(returnEdge.secondNode)) {
                    listWithShowsSecondNode.add(edge);
                }
            }

            if (listWithShowsFirstNode.size() == 1 && listWithShowsSecondNode.size() > 1) {
                Collections.sort(listWithShowsSecondNode);

                lastEdgeAfterReturn = listWithShowsSecondNode.get(0);

                if (lastEdgeAfterReturn != null) {
                    queueOfEdges.remove(lastEdgeAfterReturn);
                    currentEdge = lastEdgeAfterReturn;
                } else
                    throw new InvalidParameterException();
            } else if (listWithShowsFirstNode.size() > 1 && listWithShowsSecondNode.size() == 1) {
                Collections.sort(listWithShowsFirstNode);

                lastEdgeAfterReturn = listWithShowsFirstNode.get(0);

                if (lastEdgeAfterReturn != null) {
                    queueOfEdges.remove(lastEdgeAfterReturn);
                    currentEdge = lastEdgeAfterReturn;
                } else
                    throw new InvalidParameterException();
            }
        }
        return currentEdge;
    }

    private void removeDuplicatesInLists(List<Edge> queueOfEdges, List<Edge> edgesWithExactNode, List<Edge> minTreeListWithEdges) {
        List<Edge> tempListToRemoveDuplicates = new ArrayList<>(edgesWithExactNode);
        edgesWithExactNode.removeIf(minTreeListWithEdges::contains);
        edgesWithExactNode.removeIf(queueOfEdges::contains);
        queueOfEdges.removeIf(tempListToRemoveDuplicates::contains);
    }
}
