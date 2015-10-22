package main;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class StoreageTest {

    public static void main(String args[]) {
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph();
        System.out.println(stringGraph.toString());

        Set<String> vertices = stringGraph.vertexSet();
        Set<DefaultWeightedEdge> edges = stringGraph.edgeSet();

        Object[] va = vertices.toArray();

        for (DefaultWeightedEdge e : edges) {
            double o = stringGraph.getEdgeWeight(e);
            System.out.println(o);
        }

    }

    private static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> createStringGraph() {
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> g =
                new ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

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
