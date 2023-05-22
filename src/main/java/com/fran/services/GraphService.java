package com.fran.services;

import com.fran.estructure.GraphDirected;
import com.fran.estructure.GraphMI;
import com.fran.estructure.Vertex;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSink;
import org.graphstream.stream.file.FileSinkGraphML;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import java.io.IOException;
import java.util.ArrayList;

public class GraphService {

    private static Graph loadGraph(GraphMI graphMI, String graphName) {
        boolean isDirected = graphMI instanceof GraphDirected;

        Graph graph = new SingleGraph(graphName.toUpperCase());
        ArrayList<ArrayList<Vertex>> edges = graphMI.allEdges();

        for (ArrayList<Vertex> edge : edges) {
            String vX = edge.get(0).getTitle();
            if (graph.getNode(vX) == null) {
                graph.addNode(vX);
            }

            String vY = edge.get(1).getTitle();
            if (graph.getNode(vY) == null) {
                graph.addNode(vY);
            }

            graph.addEdge(vX + vY, vX, vY, isDirected);
        }

        for (Node node : graph) {
            node.setAttribute("ui.label", node.getId());
        }
        return graph;
    }

    public static void exportGraphToGraphml(GraphMI graphMI, String graphName) {
        Graph graph = loadGraph(graphMI, graphName);

        FileSink fileSink = new FileSinkGraphML();
        try {
            fileSink.writeAll(graph, graphName + ".graphml");
            System.out.println("Arquivo " + graphName + ".graphml exportado com sucesso");
        } catch (IOException e) {
            System.out.println("Erro ao exportar arquivo " + graphName + ".graphml");
        }
    }

    public static void plotGraph(GraphMI graphMI, String graphName) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = loadGraph(graphMI, graphName);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        View view = viewer.getDefaultView();
    }
}
