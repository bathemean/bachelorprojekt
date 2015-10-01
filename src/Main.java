import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String args []){
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph();

        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> spanner = greedySpanner(stringGraph, 2);

        System.out.println(spanner.toString());

    }

    private static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> createStringGraph()
    {
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> g =
                new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

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

    public static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> greedySpanner(ListenableUndirectedWeightedGraph g, Integer r){
        Set<String> vertices = g.vertexSet();
        Set<DefaultWeightedEdge> edges = g.edgeSet();
        // This is our G', that represents all the edges added. It contains the same vertices as G(g)
        Set<DefaultWeightedEdge> gPling;

        // We need to figure out a structure that can contain (edge, weight) so we can sort them
        for(Integer i = sortedEdges.size(); i>0; i){
        //    String startV, endV = sortedEdges.pop[0]?
            if ((r * g.getEdgeWeight(g.getEdge(startV, endV))) < DijkstraShortestPath(gPling, startV, endV).getWeigt()){
                gPling.addEdge(startV, endV);
            }
        }

        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> spanner =
                new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        // iterate and add vertices/edges or possible to add a Set<> of them?
        return spanner;


    }
}
