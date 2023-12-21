package ru.nsu.fit.ezaitseva;

import java.util.ArrayList;
import java.util.function.Supplier;

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

//        AdjListGraph graph = new AdjListGraph();
//        graph.addVertex(new Vertex<>("A"));
//        graph.addVertex(new Vertex<>("B"));
//        graph.addVertex(new Vertex<>("C"));
//        graph.addEdge(new Edge<>(10, new Vertex<>("A"), new Vertex<>("D")));
////        graph.removeVertex(new Vertex<>("A"));
////        graph.removeEdge(new Edge<>(new Vertex<>("A"), new Vertex<>("D")));
//        graph.displayAdjList();

        GraphInterface<String, Integer> g = new AdjMatrixGraph();
        ArrayList<Vertex<String>> vertices = g.getVerticesList();
        ArrayList<Edge<String, Integer>> edges = g.getEdgesList();
        GraphReader graphReader = new GraphReader(g, vertices, edges);
        graphReader.readGraph("Graph.txt");

    }


}