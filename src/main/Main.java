package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
import main.spanner.GreedySpanner;
import main.spanner.ThorupZwickSpanner;

public class Main {

    public static void main(String args[]) throws Exception {

        GraphFactory factory = new GraphFactory();

        GreedySpanner greedy = new GreedySpanner();
        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

        uwGraph stringGraph = factory.createStringGraph();
//        uwGraph bigStrGraph = factory.createBiggerStringGraph();

//        GraphLoader gl = new GraphLoader();
//        uwGraph condmat2005 = gl.loadCondMat2005();

        /*
        System.out.println("==== String graph: ====");
        System.out.println("Original: " + stringGraph.toString());
        System.out.println(stringGraph.getMetricsAsString());
        uwGraph spannerOne = greedy.makeSpanner(stringGraph, 2);
        System.out.println("GreedySpanner:  " + spannerOne.toString());
        uwGraph spannerOne = thorupzwick.makeSpanner(stringGraph, 2);
        System.out.println("GreedySpanner:  " + spannerOne.toString());

        System.out.println(spannerOne.getMetricsAsString());
        */
        DijkstraShortestPaths dijkstra = new DijkstraShortestPaths(stringGraph, "v1", 0.0);
        System.out.println(dijkstra.getShortestPaths());

        uwGraph spannerTwo = thorupzwick.makeSpanner(stringGraph, 2);
        //System.out.println("ThorupZwick: " + spannerTwo.toString());

        /*
        System.out.println("==== Big String graph: ====");
        System.out.println("Original: " + bigStrGraph.toString());
        Spanner spannerThree = greedy.makeSpanner(bigStrGraph, 2);
        System.out.println("GreedySpanner:  " + spannerThree.toString());
        Spanner spannerFour = thorupzwick.makeSpanner(bigStrGraph, 2);
        System.out.println("ThorupZwick: " + spannerFour.toString());
        */

        //Spanner bspanner = greedy.makeSpanner(bigStrGraph, 2);
        //System.out.println("BSpanner:  " + bspanner.toString());
    }
}
