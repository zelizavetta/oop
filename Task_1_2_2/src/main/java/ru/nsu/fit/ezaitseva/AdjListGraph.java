package ru.nsu.fit.ezaitseva;


import java.util.*;

public class AdjListGraph<V, E extends Number> extends GraphCommon<V, E>
implements GraphInterface<V, E> {

    private final Map<Integer, List<Integer>> adjVertices;

    AdjListGraph() {
        this.adjVertices = new HashMap<>();
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }


    public Vertex<V> addVertex(Vertex<V> vertex) {
        this.adjVertices.putIfAbsent(vertex.id, new ArrayList<>());
        this.vertices.putIfAbsent(vertex.id, vertex);
        return vertex;
    }

    public void removeVertex(Vertex<V> vertex) {
        Vertex v = vertex;
        this.adjVertices.values().stream().forEach(e -> e.remove(v.id));
        this.adjVertices.remove(vertex.id);
        this.vertices.remove(vertex.id);
    }

    public Edge<V, E> addEdge(Edge<V,E> edge) {
        addVertex(edge.src);
        addVertex(edge.dest);

        this.edges.put(edge.id, edge);
        this.adjVertices.get(edge.src.id).add(edge.dest.id);
        this.adjVertices.get(edge.dest.id).add(edge.src.id);
        return edge;
    }


    public void removeEdge(Edge<V, E> edge) {
        List<Integer> eV1 = this.adjVertices.get(edge.src.id);
        List<Integer> eV2 = this.adjVertices.get(edge.dest.id);
        if (eV1 != null)
            eV1.remove(edge.dest.id);
        if (eV2 != null)
            eV2.remove(edge.src.id);
        this.edges.remove(edge.id);
    }
    public void addVertices(Vertex[] vertices) {
        for (Vertex vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    public void addEdges(Edge[] edges) {
        for (Edge edge: edges) {
            this.addEdge(edge);
        }
    }

    public void displayAdjList() {
        this.adjVertices.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public ArrayList<Edge> findEdges(Integer srcId, Integer destId) {
        ArrayList<Edge> res = new ArrayList<>();
        for (var elem : this.edges.entrySet()) {
            Integer edgeId = elem.getKey();
            Edge edge = elem.getValue();
            if (edge.src.id == srcId && edge.dest.id == destId) {
                res.add(edge);
            }
        }
        return res;
    }


}