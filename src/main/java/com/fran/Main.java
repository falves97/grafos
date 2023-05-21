package com.fran;

import com.fran.estructure.GraphDirected;
import com.fran.estructure.GraphMI;
import com.fran.estructure.GraphUndirectedMI;
import com.fran.estructure.Vertex;
import com.fran.services.GraphService;
import com.fran.utils.FileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        GraphMI graphMI;
        try {
            init(args);
            graphMI = FileIO.readGraphFromFile(args[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        int option = 0;
        do {
            System.out.println("1 - São adjacentes?");
            System.out.println("2 - Grau");
            System.out.println("3 - Vizinhos");
            System.out.println("4 - Todas as arestas");
            System.out.println("5 - Exportar Grafo Original");
            System.out.println("6 - Plotar Grafos");
            System.out.println("0 - Sair");

            option = input.nextInt();

            switch (option) {
                case 1 -> areAdjacent(graphMI);
                case 2 -> grade(graphMI);
                case 3 -> neighbors(graphMI);
                case 4 -> edges(graphMI);
                case 5 -> exportOriginalGraph(graphMI);
                case 6 -> plotGraphs(graphMI);
            }

        } while (option != 0);
    }

    private static void plotGraphs(GraphMI graphMI) {
        GraphService.plotGraph(graphMI, "original");

        if (graphMI instanceof GraphUndirectedMI) {
            GraphUndirectedMI tree = ((GraphUndirectedMI) graphMI).minimalSpanningTree();
            GraphService.plotGraph(tree, "mst");
        }
    }

    private static void exportOriginalGraph(GraphMI graphMI) {
        GraphService.exportGraphToGraphml(graphMI, "original");
        if (graphMI instanceof GraphUndirectedMI) {
            GraphUndirectedMI tree = ((GraphUndirectedMI) graphMI).minimalSpanningTree();
            GraphService.exportGraphToGraphml(tree, "mst");
        }
    }

    private static void mst(GraphMI graphMI) {
        if (!(graphMI instanceof GraphUndirectedMI)) {
            throw new IllegalArgumentException("Grafo não pode ser direcionado");
        }

        GraphUndirectedMI tree = ((GraphUndirectedMI) graphMI).minimalSpanningTree();

        edges(tree);
    }

    private static void isConcted(GraphMI graphMI) {
        if (!graphMI.isConected()) {
            System.out.print("Não ");
        }

        System.out.println("Conexo");
    }

    public static void init(String[] args) throws InputMismatchException {
        if (args.length == 0) {
            throw new InputMismatchException("Necessário informar caminho completo do arquivo");
        }

        String filePath = args[0];
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            throw new InputMismatchException("Caminho para arquivo é inválido");
        }
    }

    public static void areAdjacent(GraphMI graphMI) {
        System.out.print("Vertice vX: ");
        String vX = input.next();
        System.out.print("Vertice vY: ");
        String vY = input.next();

        boolean are = graphMI.areAdjacent(vX, vY);

        if (!are) {
            System.out.print("Não ");
        }

        System.out.println("São Adjacentes");
    }

    public static void grade(GraphMI graphMI) {
        System.out.print("Vertice: ");
        String v = input.next();

        if (graphMI instanceof GraphUndirectedMI) {
            int grade = ((GraphUndirectedMI) graphMI).grade(v);
            System.out.println("Grau de " + v + ": " + grade);
        } else {
            int entryGrade = ((GraphDirected) graphMI).entryGrade(v);
            System.out.println("Grau de entrada de " + v + ": " + entryGrade);

            int outGrade = ((GraphDirected) graphMI).outGrade(v);
            System.out.println("Grau de saída de " + v + ": " + outGrade);
        }

    }

    public static void neighbors(GraphMI graphMI) {
        System.out.print("Vertice: ");
        String v = input.next();

        ArrayList<Vertex> neighbors = graphMI.neighbors(v);

        System.out.println("Vizinhos de " + v + ": " + neighbors);
    }

    public static void edges(GraphMI graphMI) {
        ArrayList<ArrayList<Vertex>> edges = graphMI.allEdges();
        System.out.print("[");
        for (int i = 0; i < edges.size(); i++) {
            System.out.print("[");

            System.out.print(edges.get(i).get(0).getTitle() + ", " + edges.get(i).get(1).getTitle());

            if (i < edges.size() - 1) {
                System.out.print("], ");
            } else {
                System.out.print("]");
            }
        }
        System.out.println("]");
    }

    private static void bfs(GraphMI graphMI) {
        System.out.print("Vertice v: ");
        String v = input.next();

        System.out.println(graphMI.bfs(v));
    }

    public static void hasCicle(GraphMI graphMI) {
        boolean resp = graphMI.hasCicleDfs();

        if (!resp) {
            System.out.print("Não ");
        }

        System.out.println("Tem ciclos");
    }

}