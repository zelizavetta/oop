package ru.nsu.fit.ezaitseva;

import java.util.*;

public interface GraphInterface<V, E extends Number> {

    Vertex<V> addVertex(Vertex<V> vertex);
    void addVertices(Vertex[] vertices);
    Vertex<V> getVertexById(int vertexId);
    void removeVertex(Vertex<V> vertex);
    ArrayList<Vertex<V>> getVerticesList();

    Edge<V, E> addEdge(Edge<V, E> edge);

    void removeEdge(Edge<V, E> edge);
    ArrayList<Edge<V, E>> getEdgesList();
    Edge<V, E> getEdgeById(int edgeId);

    void addEdges(Edge[] edges);


}