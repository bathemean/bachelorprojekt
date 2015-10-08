import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Main {

    public static void main(String args []){
        uwGraph stringGraph = createStringGraph();

        GreedySpanner spanner = new GreedySpanner(stringGraph, 2);

        System.out.println("Original: " + stringGraph.toString());
        System.out.println("Spanner:  " + spanner.toString());

    }

    /**
     * Creates a dummy graph.
     * @return the graph.
     */
    private static uwGraph createStringGraph()
    {
        uwGraph g = new uwGraph(DefaultWeightedEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.setEdgeWeight(g.getEdge("v1", "v2"), 10);
        g.addEdge(v2, v3);
        g.setEdgeWeight(g.getEdge("v2", "v3"), 30);
        g.addEdge(v3, v4);
        g.setEdgeWeight(g.getEdge("v3", "v4"), 20);
        g.addEdge(v4, v1);
        g.setEdgeWeight(g.getEdge("v4", "v1"), 45);
        g.addEdge(v2, v4);
        g.setEdgeWeight(g.getEdge("v2", "v4"), 50);
        g.addEdge(v3, v1);
        g.setEdgeWeight(g.getEdge("v3", "v1"), 15);

        return g;
    }




}
