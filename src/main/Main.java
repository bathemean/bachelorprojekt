package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
import main.spanner.GreedySpanner;
import main.spanner.ThorupZwickSpanner;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    private static String resultsFile = "restults.txt";

    /**
     * This is the playground for all our stuff
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        ArrayList<Integer> kValues = new ArrayList<Integer>();
        kValues.add(2);
        kValues.add(3);
        kValues.add(5);

        GraphFactory factory = new GraphFactory();
        GreedySpanner greedy = new GreedySpanner();
        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

        // for k = 2, 3, 5.
        for(int k : kValues) {
            // for vertice counts {25, 50, ..., 400}.
            for (int v = 25; v < 400; v += 25) {
                // for densities d = {0.5, 0.6, ..., 1.0}.
                for (double d = 0.5; d <= 1.0; v += 0.1) {
                    System.out.printf("== Creating spanners with parameters: k = %d, #vertices = %d, density = %f \n", k, v, d);
                    uwGraph generatedGraph = factory.wieghtedDenseGraph(v, d);

                    uwGraph greedyspan = greedy.makeSpanner(generatedGraph, k);
                    System.out.println(greedyspan.getMetricsAsString());

                    uwGraph tzspan = thorupzwick.makeSpanner(generatedGraph, k);
                    System.out.println(tzspan.getMetricsAsString());

                    printToResults(k, v, d, greedyspan.getMetricsAsCSV(), tzspan.getMetricsAsCSV());

                }
            }
        }

    }

    private static void printToResults(int k, int verticeCount, double density, String greedyMetrics, String tzMetrics) {

        String res = k + "," + verticeCount + "," + density + "," + greedyMetrics + "," + tzMetrics;

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resultsFile, true)));
            out.println(res);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }
}
