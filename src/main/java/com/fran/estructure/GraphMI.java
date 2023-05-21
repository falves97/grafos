package com.fran.estructure;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class GraphMI {
    protected Integer[][] incidenceMatrix;

    protected ArrayList<Vertex> vertices;

    public GraphMI() {
        incidenceMatrix = new Integer[0][0];
        vertices = new ArrayList<>();
    }

    public Integer[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(String v) {
        Optional<Vertex> optionalVertex = vertices.stream()
                .filter(vertex -> vertex.getTitle().equals(v))
                .findFirst();

        return optionalVertex.orElse(null);
    }

    protected int putOriginVertexEdge(String v) {
        Vertex vA = getVertex(v);

        if (vA == null) {
            vA = new Vertex(v);
            vertices.add(vA);
        }

        growSize();

        int vAIndex = vertices.indexOf(vA);
        int lastEdge = incidenceMatrix[0].length - 1;

        incidenceMatrix[vAIndex][lastEdge] = 1;

        return lastEdge;
    }

    public boolean containsVertice(String v) {
        return vertices.stream().anyMatch(vertex -> vertex.getTitle().equals(v));
    }

    protected boolean isLoop(int edge) {
        if (incidenceMatrix.length == 0) {
            return false;
        }

        if (incidenceMatrix[0].length < edge || edge < 0) {
            return false;
        }

        int count = 0;
        for (Integer[] matrix : incidenceMatrix) {
            if (matrix[edge] != 0) {
                count++;
            }
        }

        return count == 1;
    }

    public void resetForSearch() {
        for (Vertex vertex : this.vertices) {
            vertex.setColor(VertexColor.WHITE);
            vertex.setFather(null);
            vertex.setDistance(null);
        }
    }

    protected void initializeMatrix(Integer[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    protected void growSize() {
        int verticesSize = vertices.size();
        int edges = 1;

        if (incidenceMatrix.length > 0) {
            edges = incidenceMatrix[0].length + 1;
        }

        Integer[][] newMatrix = new Integer[verticesSize][edges];
        initializeMatrix(newMatrix);

        for (int i = 0; i < incidenceMatrix.length; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, incidenceMatrix[0].length);
        }

        incidenceMatrix = newMatrix;
    }

    public boolean areAdjacent(String a, String b) {
        if (!containsVertice(a) || !containsVertice(b)) {
            return false;
        }

        Vertex vX = getVertex(a);
        Vertex vY = getVertex(b);

        int vXIndex = vertices.indexOf(vX);
        int vYIndex = vertices.indexOf(vY);

        for (int edge = 0; edge < this.incidenceMatrix[vXIndex].length; edge++) {
            if (this.incidenceMatrix[vXIndex][edge] != 0 && this.incidenceMatrix[vYIndex][edge] != 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Busca em largura
     */
    public ArrayList<Vertex> bfs(String v) throws IllegalArgumentException {

        if (!containsVertice(v)) {
            throw new IllegalArgumentException("Vertice não existe no grafo.");
        }

        Vertex vertex = getVertex(v);

        resetForSearch();

        vertex.setDistance(0);
        vertex.setColor(VertexColor.GREY);
        vertex.setFather(null);

        ArrayBlockingQueue<Vertex> queue = new ArrayBlockingQueue<>(vertices.size());
        queue.add(vertex);

        while (queue.size() > 0) {
            Vertex currentVertex = queue.poll();

            ArrayList<Vertex> neighbors = this.neighbors(currentVertex.getTitle());

            for (Vertex vertexItem : neighbors) {
                if (vertexItem.getColor() == VertexColor.WHITE) {
                    vertexItem.setFather(currentVertex);
                    vertexItem.setColor(VertexColor.GREY);

                    if (currentVertex.getDistance() != null) {
                        vertexItem.setDistance(currentVertex.getDistance() + 1);
                    } else {
                        vertexItem.setDistance(1);

                    }
                    queue.add(vertexItem);
                }
            }

            currentVertex.setColor(VertexColor.BLACK);
        }

        return vertices;
    }

    public boolean isConected() {
        bfs(vertices.get(0).getTitle());

        for (Vertex vertex : vertices) {
            if (vertex.getColor() == VertexColor.WHITE) {
                return false;
            }
        }

        return true;
    }

    public boolean hasCicleDfs() {
        resetForSearch();
        Integer time = 0;

        for (int i = 0; i < incidenceMatrix.length; i++) {
            if (vertices.get(i).getColor() == VertexColor.WHITE) {
                boolean resp = dfsVisitCicle(time, vertices.get(i));

                if (resp) {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean dfsVisitCicle(Integer time, Vertex vertex) {
        time++;
        vertex.setInitTime(time);
        vertex.setColor(VertexColor.GREY);

        for (Vertex neighbor : neighbors(vertex.getTitle())) {
            if (neighbor.getColor() == VertexColor.GREY && (vertex.getInitTime() - neighbor.getInitTime()) > 1) {
                return true;
            }

            if (neighbor.getColor() == VertexColor.WHITE) {
                neighbor.setFather(vertex);
                return dfsVisitCicle(time, neighbor);
            }
        }

        vertex.setColor(VertexColor.BLACK);
        time++;
        vertex.setEndTime(time);

        return false;
    }

    abstract public void putEdge(String a, String b);

    abstract public ArrayList<ArrayList<Vertex>> allEdges();

    abstract public ArrayList<Vertex> neighbors(String v);
}
