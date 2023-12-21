package ru.nsu.fit.ezaitseva;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphCommon<V, E extends Number> {
    protected HashMap<Integer, Vertex<V>> vertices;
    protected HashMap<Integer, Edge<V, E>> edges;

    public Vertex<V> getVertexById(int vertexId) {
        return vertices.get(vertexId);
    }

    public Edge<V, E> getEdgeById(int edgeId) {
        return edges.get(edgeId);
    }

    public ArrayList<Vertex<V>> getVerticesList() {
        return new ArrayList<>(vertices.values());
    }

    public ArrayList<Edge<V, E>> getEdgesList() {
        return new ArrayList<>(edges.values());
    }

}