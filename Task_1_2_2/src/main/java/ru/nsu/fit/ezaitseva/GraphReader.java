package ru.nsu.fit.ezaitseva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GraphReader {
    static GraphInterface<String, Integer> g;
    static ArrayList<Vertex<String>> vertices;
    static ArrayList<Edge<String, Integer>> edges;

    GraphReader(GraphInterface<String, Integer> g,
                ArrayList<Vertex<String>> vertices,
                ArrayList<Edge<String, Integer>> edges) {
        this.g = g;
        this.vertices = vertices;
        this.edges = edges;
    }


    static String curDir = System.getProperty("user.dir");

    public static Vertex vertexReading(String value) {
        Vertex v = null;
        for (var elem : vertices) {
            if (elem.value.equals(value)) {
                v = elem;
                break;
            }
        }
        if (v == null) {
            v = new Vertex<>(value);
            vertices.add(g.addVertex(v));
        }
        return v;
    }

    public static Edge edgeReading(String val1, String val2, Integer w) {
        Vertex v1 = vertexReading(val1);
        Vertex v2 = vertexReading(val2);
        Edge e = null;
        for (var elem : edges) {
            if (elem.src.value.equals(v1) && elem.dest.value.equals(v2) && elem.value == w) {
                e = elem;
                break;
            }
        }
        if (e == null) {
            Edge edge = new Edge<>(w, v1, v2);
            edges.add(g.addEdge(edge));
        }
        return e;
    }

    public static void readGraph(String fileName) {
        try {
            File file = new File(curDir + "/ru/nsu/fit/ezaitseva/" + fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] elems = line.split(" ");

                int lineSize = elems.length;
                if (lineSize == 1) {
                    vertexReading(elems[0]);
                }
                if (lineSize == 3) {
                    Integer w = Integer.parseInt(elems[2]);
                    edgeReading(elems[0], elems[1], w);
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}