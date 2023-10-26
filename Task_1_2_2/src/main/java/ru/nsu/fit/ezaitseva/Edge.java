package ru.nsu.fit.ezaitseva;


public class Edge<T> {
    T src;
    T dest;

    Edge(T src, T dest) {
        this.src = src;
        this.dest = dest;
    }

}