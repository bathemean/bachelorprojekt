package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
import main.spanner.GreedySpanner;
import main.spanner.ThorupZwickSpanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        // for k = 2, 3, 5.
        for(int k = 1; k < 10; k++) {
            // for vertice counts {25, 50, ..., 400}.
            for (int v = 25; v < 400; v += 25) {
                // for densities d = {0.5, 0.6, ..., 1.0}.
                for (double d = 0.8; d <= 1.0; d += 0.1) {
                    String filename = "density" + d + "_vertices" + v + "_k" + k + ".txt";
                    PrintWriter writerGreedy = new PrintWriter("Greedy_" + filename, "UTF-8");
                    PrintWriter writerTZ = new PrintWriter("TZ_" + filename, "UTF-8");

                    // We want to get a few datapoints to even out any factors
                    for (int i = 0; i < 10; i++) {
                        GreedySpanner greedy = new GreedySpanner();
                        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

                        uwGraph generatedGraph = factory.wieghtedDenseGraph(v, d);

                        uwGraph greedyspan = greedy.makeSpanner(generatedGraph, 2 * k - 1);
                        writerGreedy.print(greedyspan.getMetricsAsString());

                        uwGraph tzspan = thorupzwick.makeSpanner(generatedGraph, k);
                        writerTZ.print(tzspan.getMetricsAsString());

                    }
                    writerGreedy.close();
                    writerTZ.close();
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
