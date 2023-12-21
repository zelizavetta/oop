package ru.nsu.fit.ezaitseva;


public class Edge<V, E> {
    private static int newId = 0;
    E value;
    Vertex<V> src;
    Vertex<V> dest;
    int id;

    Edge(E value, Vertex<V> src, Vertex<V> dest) {
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.id = newId;
        newId++;
    }

    public int getId() {
        return this.id;
    }


}