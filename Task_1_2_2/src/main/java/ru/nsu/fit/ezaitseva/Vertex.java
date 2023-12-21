package ru.nsu.fit.ezaitseva;


public class Vertex<V> {
    private static int newId = 0;
    V value;
    int id;

    Vertex(V value) {
        this.value = value;
        this.id = newId;
        newId++;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex<?>))
            return false;
        if (obj == this)
            return true;

        Vertex vertex = (Vertex) obj;
        return (this.value != null && this.value.equals(vertex.value));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    public int getId() {
        return this.id;
    }



}