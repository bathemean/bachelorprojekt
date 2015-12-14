package main;

import main.graph.GraphFactory;
import main.graph.uwGraph;
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

        //GreedySpanner greedy = new GreedySpanner();

        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

        uwGraph stringGraph = factory.createStringGraph();
  //      uwGraph bigStrGraph = factory.createBiggerStringGraph();


//        GraphLoader gl = new GraphLoader();
//        uwGraph condmat2005 = gl.loadCondMat2005();

        /*
        System.out.println("==== String graph: ====");
        System.out.println("Original: " + stringGraph.toString());
        System.out.println(stringGraph.getMetricsAsString());
        uwGraph spannerOne = greedy.makeSpanner(stringGraph, 2);
        System.out.println("GreedySpanner:  " + spannerOne.toString());
        */
//        System.out.println("GreedySpanner:  " + spannerOne.toString());

//        System.out.println(spannerOne.getMetricsAsString());



        //DijkstraShortestPaths dijkstra = new DijkstraShortestPaths(stringGraph, "v1", false);
        //System.out.println(dijkstra.getShortestPaths());

//        System.out.println("ThorupZwick: " + spannerTwo.toString());


        uwGraph generatedGraph = factory.wieghtedDenseGraph(200, 1);
        System.out.println(generatedGraph.getMetricsAsString());
//        uwGraph derp = factory.unwieghtedDenseGraph(20, 4.0);
  //       uwGraph derp2 = factory.wieghtedCompleteDenseGraph(5);
    //    uwGraph gderp= factory.unweightedCompleteDenseGraph(5);
        //System.out.println(generatedGraph);

    //    System.out.println("==== Big String graph: ====");

        //System.out.println("Original: " + bigStrGraph.toString());
//        uwGraph spannerThree = thorupzwick.makeSpanner(generatedGraph, 5);
  //      System.out.println(spannerThree.getMetricsAsString());
        //System.out.println("GreedySpanner:  " + spannerThree.toString());
//          uwGraph spannerFour = thorupzwick.makeSpanner(bigStrGraph, 2);
  //      System.out.println("ThorupZwick: " + spannerFour.toString());

        //Spanner bspanner = greedy.makeSpanner(bigStrGraph, 2);
        //System.out.println("BSpanner:  " + bspanner.toString());
    }
}
