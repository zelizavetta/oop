package ru.nsu.fit.ezaitseva;


import java.util.*;

public class AdjMatrixGraph<V, E extends Number> extends GraphCommon<V, E> implements GraphInterface<V, E>{

    private final Map<Integer, Map<Integer, Edge<V, E>>> adjMatrix;

    public AdjMatrixGraph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.adjMatrix = new HashMap<>();
    }


    public Vertex<V> addVertex(Vertex<V> vertex) {
        vertices.putIfAbsent(vertex.id, vertex);
        adjMatrix.putIfAbsent(vertex.id, new HashMap<>());
        return vertex;
    }

    public void removeVertex(Vertex<V> vertex) {
        for (var entry : adjMatrix.get(vertex.getId()).entrySet()) {
            edges.remove(entry.getValue().getId());
        }
        // Удаляем входящие ребра
        for (var entry : adjMatrix.entrySet()) {
            if (entry.getValue().containsKey(vertex.getId())) {
                edges.remove(entry.getValue().get(vertex.getId()).getId());
            }
        }

        adjMatrix.remove(vertex.getId());

        for (var entry : adjMatrix.entrySet()) {
            entry.getValue().remove(vertex.getId());
        }
        vertices.remove(vertex.getId());
    }

    public void addVertices(Vertex[] vertices) {
        for (Vertex vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    public Edge<V, E> addEdge(Edge<V, E> edge) {
        addVertex(edge.src);
        addVertex(edge.dest);

        this.adjMatrix.get(edge.src.id).put(edge.dest.id, edge);
        this.edges.put(edge.getId(), edge);

        return edge;
    }

    public void removeEdge(Edge<V, E> edge) {
        int positionFrom = edge.src.getId();
        int positionTo = edge.dest.getId();

        this.adjMatrix.get(positionFrom).remove(positionTo);

        this.edges.remove(edge.getId());
    }


    public void addEdges(Edge[] edges) {
        for (Edge<V, E> edge: edges) {
            this.addEdge(edge);
        }
    }




}