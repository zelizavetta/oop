package ru.nsu.fit.ezaitseva;


public class Vertex<T> {
    T value;

    Vertex(T value) {
        this.value = value;
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


}