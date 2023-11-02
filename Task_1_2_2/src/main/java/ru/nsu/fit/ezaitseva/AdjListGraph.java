package ru.nsu.fit.ezaitseva;


import java.util.*;

public class AdjListGraph<T> extends Graph<T>{

    Map<Vertex, List<Vertex>> adjVertices;

    AdjListGraph() {
        this.adjVertices = new HashMap<>();

    }

//    AgjListGraph(List<Edge<T>> adjList) {
//        this.adjList = adjList;
//    }

    @Override
    void addVertex(Vertex<T> vertex) {
        this.adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    void removeVertex(Vertex<T> vertex) {
        Vertex v = vertex;
        this.adjVertices.values().stream().forEach(e -> e.remove(v));
        this.adjVertices.remove(vertex);
    }

    @Override
    void addEdge(Edge<T> edge) {
        addVertex(edge.src);
        addVertex(edge.dest);

        this.adjVertices.get(edge.src).add(edge.dest);
        this.adjVertices.get(edge.dest).add(edge.src);
    }


    @Override
    void removeEdge(Edge<T> edge) {
        List<Vertex> eV1 = this.adjVertices.get(edge.src);
        List<Vertex> eV2 = this.adjVertices.get(edge.dest);
        if (eV1 != null)
            eV1.remove(edge.dest);
        if (eV2 != null)
            eV2.remove(edge.src);
    }
    @Override
    public void addVertices(Vertex[] vertices) {
        for (Vertex vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    @Override
    public void addEdges(Edge[] edges) {
        for (Edge edge: edges) {
            this.addEdge(edge);
        }
    }

    public void displayAdjList() {
        this.adjVertices.forEach((key, value) -> System.out.println(key + " " + value));
    }

    public void BFS(T s)
    {
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[this.adjVertices.size()];

        // Create a queue for BFS
        LinkedList<T> queue = new LinkedList<T>();

        // Mark the current node as visited and enqueue it
        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {

            // Dequeue a vertex from queue and print it
            s = queue.poll();
            System.out.print(s + " ");

            // Get all adjacent vertices of the dequeued
            // vertex s.
            // If an adjacent has not been visited,
            // then mark it visited and enqueue it
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }


}