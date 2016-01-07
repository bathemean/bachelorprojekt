package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
import main.spanner.GreedySpanner;
import main.spanner.ThorupZwickSpanner;

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

        // for vertice counts {25, 50, ..., 400}.
        for (int v = 25; v < 400; v += 25) {
            // for k = 2, 3, 5.
            for(int k = 1; k < 10; k++) {
                System.out.println(k);
                // for densities d = {0.5, 0.6, ..., 1.0}.
                for (double d = 0.7; d <= 1.0; d += 0.1) {
                    String descriptionHeaders = "weight,density,highest degree,runtime,stretch,jumpstretch\n";
                    String filename = "density" + d + "_vertices" + v + "_k" + k + ".csv";

                    PrintWriter writerGreedy = new PrintWriter("datapoints/Greedy_" + filename, "UTF-8");
                    PrintWriter writerTZ = new PrintWriter("datapoints/TZ_" + filename, "UTF-8");

                    writerGreedy.write(descriptionHeaders);
                    writerTZ.write(descriptionHeaders);


                    // We want to get a few datapoints to even out any factors
                    for (int i = 0; i < 10; i++) {
                        uwGraph generatedGraph = factory.wieghtedDenseGraph(v, d);

                        GreedySpanner greedy = new GreedySpanner();
                        uwGraph greedyspan = greedy.makeSpanner(generatedGraph, 2 * k - 1);

                        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();
                        uwGraph tzspan = thorupzwick.makeSpanner(generatedGraph, k);

                        // Fetch data from spanners before they'll be altered
                        String greedyData = greedyspan.getMetricsAsCSV();
                        String tzData = tzspan.getMetricsAsCSV();
                        // Get stretch for weights
                        double greedyStretch = greedy.setStretch(generatedGraph, greedyspan);
                        double tzStretch = thorupzwick.setStretch(generatedGraph, tzspan);

                        // Set all edge weights to 1.0
                        generatedGraph.setAllEdgesToOne();
                        greedyspan.setAllEdgesToOne();
                        tzspan.setAllEdgesToOne();

                        // Get stretch for edge-jumps
                        double greedyJumpStretch = greedy.setStretch(generatedGraph, greedyspan);
                        double tzJumpStretch = thorupzwick.setStretch(generatedGraph, tzspan);

                        writerGreedy.print(greedyData + "," + greedyStretch + "," + greedyJumpStretch + "\n");
                        writerTZ.print(tzData + "," + tzStretch + "," + tzJumpStretch + "\n");
                    }
                    writerGreedy.close();
                    writerTZ.close();
                }
            }
        }

    }
}
