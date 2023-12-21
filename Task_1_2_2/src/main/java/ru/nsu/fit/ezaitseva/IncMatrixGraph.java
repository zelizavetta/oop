package ru.nsu.fit.ezaitseva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class IncMatrixGraph<V, E extends Number> extends GraphCommon<V, E>
        implements GraphInterface<V, E> {

    private final HashMap<Integer, HashMap<Integer, Double>> IncMatrix;

    public IncMatrixGraph() {
        this.IncMatrix = new HashMap<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }


    public Vertex<V> addVertex(Vertex<V> vertex) {
        vertices.putIfAbsent(vertex.id, vertex);
        return vertex;
    }


    public void removeVertex(Vertex<V> vertex) {
        ArrayList<Integer> keysToDelete = new ArrayList<>();
        for (var entry : IncMatrix.entrySet()) {
            if (entry.getValue().containsKey(vertex.getId())) {
                keysToDelete.add(entry.getKey());
                edges.remove(entry.getKey());
            }
        }
        for (var key : keysToDelete) {
            IncMatrix.remove(key);
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

        edges.putIfAbsent(edge.getId(), edge);
        IncMatrix.putIfAbsent(edge.getId(), new HashMap<>());
        IncMatrix.get(edge.getId()).putIfAbsent(edge.src.id, -edge.value.doubleValue());
        IncMatrix.get(edge.getId()).putIfAbsent(edge.dest.getId(), edge.value.doubleValue());
        return edge;
    }


    public void removeEdge(Edge<V, E> edge) {
        IncMatrix.remove(edge.getId());
        edges.remove(edge.getId());
    }

    public void addEdges(Edge[] edges) {
        for (Edge edge: edges) {
            this.addEdge(edge);
        }
    }


}