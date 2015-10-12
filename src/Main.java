import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Main {

    public static void main(String args []){

        GraphFactory factory = new GraphFactory();

        uwGraph stringGraph = factory.createStringGraph();
        uwGraph bigStrGraph = factory.createBiggerStringGraph();

        GreedySpanner spanner = new GreedySpanner(stringGraph, 2);

        System.out.println("Original: " + stringGraph.toString());
        System.out.println("Spanner:  " + spanner.toString());

        ThorupZwickSpanner tzspanner = new ThorupZwickSpanner(bigStrGraph, 4);
        System.out.println("TZ Spanner: " + tzspanner.toString());

        //ThorupZwickSpannerTest tzTest = new ThorupZwickSpannerTest();
        //System.out.println("Distanes test: " + tzTest.testDistances());

    }





}
