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

/**
 *
        GreedySpanner greedy = new GreedySpanner();
        uwGraph stringGraph = new GraphFactory().wieghtedDenseGraph(50, 1.0);
        uwGraph spannerG = greedy.makeSpanner(stringGraph, 2*3-1); // 5
        greedy.setStretch(stringGraph, spannerG);

        System.out.println(greedy.setStretch(stringGraph, spannerG));
        System.out.println(spannerG);
    }
*/        ArrayList<Integer> kValues = new ArrayList<Integer>();
        kValues.add(2);
        kValues.add(3);
        kValues.add(5);

        GraphFactory factory = new GraphFactory();

        // for vertice counts {25, 50, ..., 400}.
        for (int v = 5; v < 400; v += 5) {
            // for k = 2, 3, 5.
            for(int k = 3; k < 6; k++) {
                System.out.println(k);
                // for densities d = {0.5, 0.6, ..., 1.0}.
                for (double d = 1.0; d <= 1.0; d += 0.1) {
                    String descriptionHeaders = "weight,density,highest degree,runtime,stretch\n";
                    String filename = "density" + d + "_vertices" + v + "_k" + k + ".csv";

                    PrintWriter writerGreedy = new PrintWriter("datapoints/Greedy_" + filename, "UTF-8");
                    PrintWriter writerTZ = new PrintWriter("datapoints/TZ_" + filename, "UTF-8");

                    writerGreedy.write(descriptionHeaders);
                    writerTZ.write(descriptionHeaders);


                    // We want to get a few datapoints to even out any factors
                    for (int i = 0; i < 10; i++) {
                        uwGraph generatedGraph = factory.coherentGraph(v, d, true);

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
                        System.out.println("Greedy: " + greedyStretch + " & TZ: " + tzStretch);
                        if (greedyStretch > 2*k-1) {
                            System.out.println(greedyspan);
                        } else if (tzStretch > 2*k-1) {
                            System.out.println(tzspan);
                        }
                        // Set all edge weights to 1.0
//                        generatedGraph.setAllEdgesToOne();
//                        greedyspan.setAllEdgesToOne();
//                        tzspan.setAllEdgesToOne();

                        // Get stretch for edge-jumps
//                        double greedyJumpStretch = greedy.setStretch(generatedGraph, greedyspan);
//                        double tzJumpStretch = thorupzwick.setStretch(generatedGraph, tzspan);

                        writerGreedy.print(greedyData + "," + greedyStretch + "\n");
                        writerTZ.print(tzData + "," + tzStretch + "\n");
                    }
                    System.out.println("Vertices: " + v + " density: " + d + "k: " + k);
                    writerGreedy.close();
                    writerTZ.close();
                }
            }
        }

    }
 }
