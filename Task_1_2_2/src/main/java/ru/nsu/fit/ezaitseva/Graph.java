package ru.nsu.fit.ezaitseva;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph<T> {

    List<List<Edge<T>>> adjMatrix = new ArrayList<>();
    public abstract void addVertex(T vertex);

    public abstract void addEdge(Edge edge);

    public abstract void addVertices(T[] vertices);

    public abstract void addEdges(Edge[] edges);


}