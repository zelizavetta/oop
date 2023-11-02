package ru.nsu.fit.ezaitseva;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph<T> {

    List<List<Edge<T>>> adjMatrix = new ArrayList<>();


    abstract void addVertex(Vertex<T> vertex);


    abstract void removeVertex(Vertex<T> vertex);

    abstract void addEdge(Edge<T> edge);

    abstract void removeEdge(Edge<T> edge);

    abstract void addVertices(Vertex[] vertices);

    abstract void addEdges(Edge[] edges);


}