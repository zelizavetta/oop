package ru.nsu.fit.ezaitseva;


import java.util.ArrayList;
import java.util.List;

public class AgjListGraph<T> extends Graph<T>{
    List<Edge<T>> adjList = new ArrayList<>();

    AgjListGraph() {

    }

    AgjListGraph(List<Edge<T>> adjList) {
        this.adjList = adjList;
    }

    @Override
    public void addVertex(T vertex) {

    }

    @Override
    public void addEdge(Edge edge) {

    }

    @Override
    public void addVertices(T[] vertices) {

    }

    @Override
    public void addEdges(Edge[] edges) {

    }

}