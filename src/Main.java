import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Main {

    public static void main(String args []){

        GraphFactory factory = new GraphFactory();

        GreedySpanner greedy = new GreedySpanner();
        ThorupZwickSpanner thorupzwick = new ThorupZwickSpanner();

        uwGraph stringGraph = factory.createStringGraph();
        uwGraph bigStrGraph = factory.createBiggerStringGraph();

        GraphLoader gl = new GraphLoader();
        uwGraph condmat2005 = gl.loadCondMat2005();

        System.out.println("==== String Graph: ====");
        System.out.println("Original: " + stringGraph.toString());
        System.out.println(stringGraph.getMetricsAsString());
        uwGraph spannerOne = greedy.makeSpanner(stringGraph, 2);
        System.out.println("GreedySpanner:  " + spannerOne.toString());
        System.out.println(spannerOne.getMetricsAsString());

        System.out.println(stringGraph.getAdjecentVertices("v1").length);

        //uwGraph spannerTwo = thorupzwick.makeSpanner(stringGraph, 2);
        //System.out.println("ThorupZwick: " + spannerTwo.toString());

        /*
        System.out.println("==== Big String Graph: ====");
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
