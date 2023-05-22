package com.fran.estructure;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.ArrayBlockingQueue;

public class GraphUndirectedMI extends GraphMI {

    public Integer grade(String v) throws IllegalArgumentException {
        if (!containsVertice(v)) {
            throw new IllegalArgumentException("Vertice não existe no grafo.");
        }

        int grade = 0;
        int vIndex = vertices.indexOf(getVertex(v));

        for (int edge = 0; edge < this.incidenceMatrix[vIndex].length; edge++) {
            if (this.incidenceMatrix[vIndex][edge] != 0) {
                if (isLoop(edge)) {
                    grade += 2;
                }
                grade++;
            }
        }

        return grade;
    }

    public GraphUndirectedMI minimalSpanningTree() {
        GraphUndirectedMI tree = new GraphUndirectedMI();
        GraphUndirectedMI tempTree = new GraphUndirectedMI();

        if (vertices.size() < 2) {
            return this;
        }

        if (!this.isConected()) {
            throw new InputMismatchException("Grafo não é conexo");
        }

        ArrayList<ArrayList<Vertex>> edges = this.allEdges();
        for (ArrayList<Vertex> edge : edges) {
            tempTree.putEdge(edge.get(0).getTitle(), edge.get(1).getTitle());
            if (tempTree.hasCicleDfs()) {
                tempTree = (GraphUndirectedMI) tree.clone();
            } else {
                tree = (GraphUndirectedMI) tempTree.clone();
            }
        }

        return tree;
    }

    @Override
    public Object clone() {
        GraphUndirectedMI clone = new GraphUndirectedMI();
        ArrayList<ArrayList<Vertex>> edges = this.allEdges();

        for (ArrayList<Vertex> edge : edges) {
            clone.putEdge(edge.get(0).getTitle(), edge.get(1).getTitle());
        }

        return clone;
    }

    @Override
    public void putEdge(String a, String b) throws IllegalArgumentException {
        Vertex vB = getVertex(b);

        if (vB == null) {
            vB = new Vertex(b);
            vertices.add(vB);
        }

        int lastEdge = putOriginVertexEdge(a);
        int vBIndex = vertices.indexOf(vB);

        incidenceMatrix[vBIndex][lastEdge] = 1;
    }

    @Override
    public ArrayList<ArrayList<Vertex>> allEdges() {
        if (this.incidenceMatrix == null) {
            return null;
        }

        if (this.incidenceMatrix.length == 0) {
            return null;
        }

        ArrayList<ArrayList<Vertex>> edges = new ArrayList<>();

        for (int i = 0; i < this.incidenceMatrix[0].length; i++) {
            ArrayList<Vertex> edge = new ArrayList<>();

            boolean loop = isLoop(i);

            for (int j = 0; j < this.incidenceMatrix.length; j++) {
                if (this.incidenceMatrix[j][i] != 0) {
                    edge.add(vertices.get(j));
                    if (loop) {
                        edge.add(vertices.get(j));
                    }
                }
            }

            edges.add(edge);
        }
        return edges;
    }

    @Override
    public ArrayList<Vertex> neighbors(String v) throws IllegalArgumentException {
        if (!containsVertice(v)) {
            throw new IllegalArgumentException("Vertice não existe no grafo.");
        }

        ArrayList<Vertex> neighbors = new ArrayList<>();
        int vAIndex = vertices.indexOf(getVertex(v));

        for (int edge = 0; edge < this.incidenceMatrix[vAIndex].length; edge++) {
            if (this.incidenceMatrix[vAIndex][edge] != 0) {
                if (isLoop(edge)) {
                    neighbors.add(vertices.get(vAIndex));
                } else {
                    for (int vBIndex = 0; vBIndex < this.incidenceMatrix.length; vBIndex++) {
                        if (vAIndex != vBIndex && this.incidenceMatrix[vBIndex][edge] != 0) {
                            neighbors.add(vertices.get(vBIndex));
                        }
                    }
                }
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GrafoMI{");
        stringBuilder.append("incidenceMatrix=[");
        for (Integer[] matrix : incidenceMatrix) {
            stringBuilder.append("\n").append("[");
            for (int j = 0; j < incidenceMatrix[0].length; j++) {
                if (j == incidenceMatrix[0].length - 1) {
                    stringBuilder.append(matrix[j]);
                } else {
                    stringBuilder.append(matrix[j]).append(", ");
                }
            }
            stringBuilder.append("]");
        }
        stringBuilder.append("\n").append("]");
        stringBuilder.append(", vertices=");
        stringBuilder.append(vertices.toString());
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}
