package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
import main.spanner.GreedySpanner;
import main.spanner.ThorupZwickSpanner;

public class Main {

    /**
     * This is the playground for all our stuff
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        GraphFactory factory = new GraphFactory();

        GreedySpanner greedy = new GreedySpanner();

        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

        uwGraph generatedGraph = factory.wieghtedDenseGraph(200, 1);
        uwGraph greedyspan = greedy.makeSpanner(generatedGraph, 5);
        System.out.println(greedyspan.getMetricsAsString());
        uwGraph tzspan = thorupzwick.makeSpanner(generatedGraph, 3);
        System.out.println(tzspan.getMetricsAsString());
    }
}
