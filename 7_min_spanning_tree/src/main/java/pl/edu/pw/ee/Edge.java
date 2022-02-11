package pl.edu.pw.ee;

import java.util.Objects;

class Edge implements Comparable {
    String firstNode;
    String secondNode;
    Double weight;

    Edge() {
        this.firstNode = "";
        this.secondNode = "";
        this.weight = 0.0;
    }

    Edge(String firstNode, String destination, Double weight) {
        this.firstNode = firstNode;
        this.secondNode = destination;
        this.weight = weight;
    }


    @Override
    public int compareTo(Object o) {
        if (this == o) return -1;
        if (!(o instanceof Edge)) return -1;
        Edge edge = (Edge) o;
        return this.weight.compareTo(edge.getWeight());
    }

    public String getFirstNode() {
        return firstNode;
    }

    public String getSecondNode() {
        return secondNode;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return (Objects.equals(firstNode, edge.firstNode) && Objects.equals(secondNode, edge.secondNode) && Objects.equals(weight, edge.weight))
                || (Objects.equals(firstNode, edge.secondNode) && Objects.equals(secondNode, edge.firstNode) && Objects.equals(weight, edge.weight));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNode, secondNode, weight);
    }
}