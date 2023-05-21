package com.fran.utils;

import com.fran.estructure.GraphDirected;
import com.fran.estructure.GraphMI;
import com.fran.estructure.GraphUndirectedMI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FileIO {
    public static GraphMI readGraphFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();

        GraphMI graphMI;

        if (line.equals("ND")) {
            graphMI = new GraphUndirectedMI();
        } else {
            graphMI = new GraphDirected();
        }

        System.out.println("Reading text file using FileReader");
        while ((line = br.readLine()) != null) {
            String[] vertices = line.split(",");
            graphMI.putEdge(vertices[0], vertices[1]);
            System.out.println(Arrays.toString(vertices));
        }
        br.close();
        fr.close();

        return graphMI;
    }
}
