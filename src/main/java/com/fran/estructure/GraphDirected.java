package com.fran.estructure;

import java.util.ArrayList;

public class GraphDirected extends GraphMI {

    public Integer entryGrade(String v) throws IllegalArgumentException {
        if (!containsVertice(v)) {
            throw new IllegalArgumentException("Vertice não existe no grafo.");
        }

        int grade = 0;
        int vIndex = vertices.indexOf(getVertex(v));

        for (int edge = 0; edge < this.incidenceMatrix[vIndex].length; edge++) {
            if (this.incidenceMatrix[vIndex][edge] < 0) {
                grade++;
            } else if (isLoop(edge)) {
                grade++;
            }
        }

        return grade;
    }

    public Integer outGrade(String v) throws IllegalArgumentException {
        if (!containsVertice(v)) {
            throw new IllegalArgumentException("Vertice não existe no grafo.");
        }

        int grade = 0;
        int vIndex = vertices.indexOf(getVertex(v));

        for (int edge = 0; edge < this.incidenceMatrix[vIndex].length; edge++) {
            if (this.incidenceMatrix[vIndex][edge] > 0) {
                grade++;
            }
        }

        return grade;
    }

    @Override
    public void putEdge(String a, String b) {
        Vertex vB = getVertex(b);

        if (vB == null) {
            vB = new Vertex(b);
            vertices.add(vB);
        }

        int lastEdge = putOriginVertexEdge(a);
        int vBIndex = vertices.indexOf(vB);

        if (!a.equals(b)) {
            incidenceMatrix[vBIndex][lastEdge] = -1;
        }
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
            edge.add(null);
            edge.add(null);

            boolean loop = isLoop(i);

            for (int j = 0; j < this.incidenceMatrix.length; j++) {
                if (this.incidenceMatrix[j][i] > 0) {
                    edge.set(0, vertices.get(j));
                    if (loop) {
                        edge.set(1, vertices.get(j));
                    }
                } else if (this.incidenceMatrix[j][i] < 0) {
                    edge.set(1, vertices.get(j));
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
            if (this.incidenceMatrix[vAIndex][edge] > 0) {
                if (isLoop(edge)) {
                    neighbors.add(vertices.get(vAIndex));
                } else {
                    for (int vBIndex = 0; vBIndex < this.incidenceMatrix.length; vBIndex++) {
                        if (this.incidenceMatrix[vBIndex][edge] < 0) {
                            neighbors.add(vertices.get(vBIndex));
                        }
                    }
                }

            }
        }

        return neighbors;
    }
}
