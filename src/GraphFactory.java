import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Random;

public class GraphFactory {

    public GraphFactory() {

    }

    /**
     * Creates a dummy graph.
     * @return the graph.
     */
    public uwGraph createStringGraph()
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

    /**
     * Creates a bigger dummy graph.
     * @return the graph.
     */
    public uwGraph createBiggerStringGraph() {
        uwGraph g = new uwGraph(DefaultWeightedEdge.class);


        ArrayList<String> vertices = new ArrayList<>();

        vertices.add("v0");
        vertices.add("v1");
        vertices.add("v2");
        vertices.add("v3");
        vertices.add("v4");
        vertices.add("v5");
        vertices.add("v6");
        vertices.add("v7");
        vertices.add("v8");
        vertices.add("v9");

        // Add vertices
        for(String v : vertices) {
            g.addVertex(v);
        }

        Random gen = new Random();

        // Assign edges between all vertices, and assign a random weight.
        for(String v : vertices) {
            for(String u : vertices) {
                if(v != u) {
                    g.addEdge(v, u);
                    g.setEdgeWeight(g.getEdge(v, u), gen.nextInt(1000));
                }
            }
        }

        return g;
    }

}
