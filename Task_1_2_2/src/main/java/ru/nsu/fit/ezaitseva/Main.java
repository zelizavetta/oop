package ru.nsu.fit.ezaitseva;

public class Main {

    public static void main(String[] args) {
//        AdjMatrixGraph<String> graph = new AdjMatrixGraph<>();
//        graph.addVertex(new Vertex<>("A"));
//        graph.addVertex(new Vertex<>("B"));
//        graph.addVertex(new Vertex<>("C"));
////        graph.addVertex(new Vertex<>("D"));
//        graph.addEdge(new Edge<>(new Vertex<>("B"), new Vertex<>("F")));
//        graph.addEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("B")));
////        graph.removeVertex(new Vertex<>("C"));
////        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("B")));
//        graph.displayAdjacencyMatrix();
////        graph.bfs("B");

        AdjListGraph graph = new AdjListGraph();
        graph.addVertex(new Vertex<>("A"));
        graph.addVertex(new Vertex<>("B"));
        graph.addVertex(new Vertex<>("C"));
        graph.addEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("D")));
//        graph.removeVertex(new Vertex<>("A"));
//        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("D")));
        graph.displayAdjList();

    }

}