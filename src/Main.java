import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class Main {

    public static void main(String args []){
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph();
        Spanner s = new Spanner(stringGraph);

        System.out.println(stringGraph.toString());

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
        g.setEdgeWeight(g.getEdge("v1", "v2"), 1000);
        g.addEdge(v2, v3);
        g.setEdgeWeight(g.getEdge("v2", "v3"), 3000);
        g.addEdge(v3, v4);
        g.setEdgeWeight(g.getEdge("v3", "v4"), 2000);
        g.addEdge(v4, v1);
        g.setEdgeWeight(g.getEdge("v4", "v1"), 4500);
        g.addEdge(v2, v4);
        g.setEdgeWeight(g.getEdge("v2", "v4"), 5000);
        g.addEdge(v3, v1);
        g.setEdgeWeight(g.getEdge("v3", "v1"), 1500);

        return g;
    }

    public static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> greedySpanner(ListenableUndirectedWeightedGraph g, Integer r){



    }
}
