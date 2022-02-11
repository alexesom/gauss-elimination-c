package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

public class GraphConnectivity extends MSTAlgorithm {
    List<Node> listVisitedNodes;

    GraphConnectivity(List<Edge> nodesList, List<String> listNodes) {
        this.nodesList = nodesList;
        this.listVisitedNodes = new ArrayList<>();

        for (String node : listNodes) {
            listVisitedNodes.add(new Node(node, false));
        }
    }

    public boolean checkConnectivity() {
        DFS(listVisitedNodes.get(0));

        for (Node nextNode : listVisitedNodes) {
            if (!nextNode.isVisited)
                return false;
        }

        return true;
    }

    private void DFS(Node node) {
        int indexOfNode = 0;
        for (Node nextNode : listVisitedNodes) {
            if(node.nodeName.equals(nextNode.nodeName))
                break;

            indexOfNode++;
        }

        listVisitedNodes.set(indexOfNode, new Node(node.nodeName, true));

        List<Node> adjustNodes = new ArrayList<>();

        List<Edge> adjustEdges = getAllEdgesWithExactNode(node.nodeName);

        for (Edge edge : adjustEdges) {
            boolean isVisited = searchIfVisitedByNodeName(edge.secondNode);

            if (!isVisited)
                adjustNodes.add(new Node(edge.secondNode, false));
        }

        for (Node adjustNode : adjustNodes) {
            if (!adjustNode.isVisited) {
                DFS(adjustNode);
            }
        }
    }

    private boolean searchIfVisitedByNodeName(String searchNodeName) throws IllegalArgumentException {
        for (Node nextNode : listVisitedNodes) {
            if (searchNodeName.equals(nextNode.nodeName)) {
                return nextNode.isVisited;
            }
        }

        throw new IllegalArgumentException("Cannot find node");
    }


    private class Node {
        String nodeName;
        boolean isVisited;

        Node(String nodeName, boolean isVisited) {
            this.nodeName = nodeName;
            this.isVisited = isVisited;
        }
    }
}
