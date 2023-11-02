package ru.nsu.fit.ezaitseva;


public class Edge<T> {
    Vertex<T> src;
    Vertex<T> dest;

    Edge(Vertex<T> src, Vertex<T> dest) {
        this.src = src;
        this.dest = dest;
    }

}