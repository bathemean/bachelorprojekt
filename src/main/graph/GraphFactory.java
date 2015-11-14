package main.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class GraphFactory {

    private ArrayList<Set<String>> subGraphs;

    public GraphFactory() {
    }

    public uwGraph wieghtedCompleteDenseGraph(int vertices) throws Exception {
        double density = (double) vertices - 1;
        return this.genGraphFromData(vertices, density, true);
    }

    public uwGraph wieghtedDenseGraph(int vertices, double density) throws Exception {
        return this.genGraphFromData(vertices, density, true);
    }

    public uwGraph unweightedCompleteDenseGraph(int vertices) throws Exception {
        double density = (double) vertices - 1;
        return this.genGraphFromData(vertices, density, false);
    }

    public uwGraph unwieghtedDenseGraph(int vertices, double density) throws Exception {
        return this.genGraphFromData(vertices, density, false);
    }

    private uwGraph genGraphFromData(int vertices, double density, boolean isWeighted) throws Exception {
        if (density < 0.0) {
            throw new Exception("Density too low, all vertices cannot be connected\n");
        } else if (vertices < 2) {
            throw new Exception("Not enough vertices given, need atleast 2\n");
        } else if (density > ((vertices - 1)/2)) {
            // If density is above the maximum density, we set it to the maximum density
            density = (((double) vertices - 1.0)/2.0);
        }

        // Instantiate graph
        uwGraph g = new uwGraph(DefaultWeightedEdge.class);
        ArrayList<String> vList = new ArrayList<String>();

        // Insert vertices intro graph and arraylist
        for (int i = 0; i < vertices; i++) {
            g.addVertex(("v" + i));
            vList.add(("v" + i));
        }

        // Amount of edges to be inserted into the graph
        // This should always give a round number, since it is based on edges/vertices
        // If the amount of edges has some decimals, we will add an ekstra edge.
        double edges = (double) (vertices) * density;

        Random gen = new Random();
        String prev = vList.get(gen.nextInt(vList.size()));

        // Keep iterating until we've depleted the edge pool
        while (edges > 0.0) {

            // Fetch a random vertices within range
            String next = vList.get(gen.nextInt(vList.size()));

            // Skip if get the same node or edge already exists
            if (prev.equals(next) || g.containsEdge(prev, next) || g.containsEdge(next, prev)) {
                // If we don't do this, we might end up on a vertex that already leads to all and we then loop forever.
                prev = next;
                continue;
            }

            // Add to graph
            g.addEdge(prev, next);
            double weight = (isWeighted ? ((double) gen.nextInt(1000)) : 1.0);
            g.setEdgeWeight(g.getEdge(prev, next), weight);
            prev = next;
            edges--;
        }

        return g;
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
        g.setEdgeWeight(g.getEdge("v1", "v2"), 10.0);
        g.addEdge(v2, v3);
        g.setEdgeWeight(g.getEdge("v2", "v3"), 30.0);
        g.addEdge(v3, v4);
        g.setEdgeWeight(g.getEdge("v3", "v4"), 20.0);
        g.addEdge(v4, v1);
        g.setEdgeWeight(g.getEdge("v4", "v1"), 45.0);
        g.addEdge(v2, v4);
        g.setEdgeWeight(g.getEdge("v2", "v4"), 50.0);
        g.addEdge(v3, v1);
        g.setEdgeWeight(g.getEdge("v3", "v1"), 15.0);

        return g;
    }

    /**
     * Creates a bigger dummy graph.
     * @return the graph.
     */
    public uwGraph createBiggerStringGraph() {
        uwGraph g = new uwGraph(DefaultWeightedEdge.class);


        ArrayList<String> vertices = new ArrayList<String>();

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
