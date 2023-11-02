package ru.nsu.fit.ezaitseva;


import java.util.*;

public class AdjMatrixGraph<T> extends Graph<T>{

//    private Map<T, Integer> vertices;
    //To get vertex using index at O(1) time
    private List<T> verticesLookup;

    //adjacency matrix
//    private int[][] adj;
    private List<List<Integer>> adj = new ArrayList<>();

    private int index;

    public AdjMatrixGraph() {
//        this.adj = new int[numVertices][numVertices];
        this.adj = new ArrayList<>();
        this.index = 0;
//        this.vertices = new HashMap<>();
        this.verticesLookup = new ArrayList<>();
    }


    @Override
    public void addEdge(Edge<T> edge) {

        addVertex(edge.src);
        addVertex(edge.dest);

        int fromIndex = this.verticesLookup.indexOf(edge.src.value);
        int toIndex = this.verticesLookup.indexOf(edge.dest.value);
        this.adj.get(fromIndex).set(toIndex, 1);
    }

    @Override
    void removeEdge(Edge<T> edge) {
        int fromIndex = this.verticesLookup.indexOf(edge.src.value);
        int toIndex = this.verticesLookup.indexOf(edge.dest.value);
        this.adj.get(fromIndex).set(toIndex, 0);
    }

    @Override
    public void addVertex(Vertex<T> vertex) {

            if (!this.verticesLookup.contains(vertex.value)) {
//                this.vertices.put(vertex.value, index);
                this.verticesLookup.add(index, vertex.value);
                this.index++;
                for (List<Integer> elem : adj) {
                    elem.add(0);
                }
                List<Integer> newLine = new ArrayList<>();
                for (int i = 0; i < index; i++) {
                    newLine.add(0);
                }
                adj.add(newLine);

        }
    }

    @Override
    void removeVertex(Vertex<T> vertex) {
        int vertInd = verticesLookup.indexOf(vertex.value);
//        this.vertices.remove(vertex.value);
        this.verticesLookup.remove(vertex.value);

        for (int i = 0; i < index; i++) {
            this.adj.get(i).remove(vertInd);
        }
        this.adj.remove(vertInd);
        this.index--;


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

    public void bfs(T start) {
        Queue<T> queue = new LinkedList<>();
        boolean[] visited = new boolean[this.index];

        queue.add(start);
        int index = verticesLookup.indexOf(start);
        visited[index] = true;

        while(!queue.isEmpty()) {
            T v = queue.poll();
            System.out.print(v + " ");

            List<T> adjacentVertices = getAdjacentVertices(v);
            for(T a : adjacentVertices) {
                int adjInd = verticesLookup.indexOf(a);
                if(!visited[adjInd]) {
                    queue.add(a);
                    visited[adjInd] = true;
                }
            }

        }

    }

//    public void dfs(V start) {
//        boolean[] visited = new boolean[vertices.size()];
//        dfs(start, visited);
//    }
//
//    private void dfs(V v, boolean[] visited) {
//        System.out.print(v + " ");
//        int index = vertices.get(v);
//        visited[index] = true;
//
//        List<V> adjacentVertices = getAdjacentVertices(v);
//        for(V a : adjacentVertices) {
//            int aIndex = vertices.get(a);
//            if(!visited[aIndex]) {
//                dfs(a, visited);
//            }
//        }
//    }

    private List<T> getAdjacentVertices(T t) {
        int vertInd = this.verticesLookup.indexOf(t);
        List<T> result = new ArrayList<>();
        for(int i = 0; i < this.index; i++) {
            if(this.adj.get(vertInd).get(i) == 1) {
                result.add(this.verticesLookup.get(i));
            }
        }
        return result;
    }

    public void displayAdjacencyMatrix()
    {
        System.out.print("\n\n Adjacency Matrix:");

        // displaying the 2D array
        for (int i = 0; i < this.index; ++i) {
            System.out.println();
            for (int j = 0; j < this.index; ++j) {
                System.out.print(" " + this.adj.get(i).get(j));
            }
        }
    }


}