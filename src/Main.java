import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Main {

    public static void main(String args []){

        GraphFactory factory = new GraphFactory();

        GreedySpanner greedy = new GreedySpanner();

        uwGraph stringGraph = factory.createStringGraph();
        uwGraph bigStrGraph = factory.createBiggerStringGraph();


        Spanner spanner = greedy.makeSpanner(stringGraph, 2);
        System.out.println("Original: " + stringGraph.toString());
        System.out.println("Spanner:  " + spanner.toString());

        //ThorupZwickSpanner tzspanner = new ThorupZwickSpanner(bigStrGraph, 1);
        //System.out.println("TZ Spanner: " + tzspanner.toString());

        //ThorupZwickSpannerTest tzTest = new ThorupZwickSpannerTest();
        //System.out.println("Distanes test: " + tzTest.testDistances());


        //GraphLoader gl = new GraphLoader();
        //uwGraph condmat2005 = gl.loadCondMat2005();
        //ThorupZwickSpanner tzspanner = new ThorupZwickSpanner(condmat2005, 4);
        //System.out.println("TZ Spanner: " + tzspanner.toString());


        //GreedySpanner bspanner = new GreedySpanner(condmat2005, 2);
        //System.out.println("Spanner:  " + bspanner.toString());

    }





}
