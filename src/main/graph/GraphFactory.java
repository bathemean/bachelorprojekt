package main.graph;

import javafx.util.Pair;
import main.DijkstraShortestPaths;
import main.Edge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

public class GraphFactory {

    private ArrayList<Set<String>> subGraphs;

    public GraphFactory() {
    }
    private boolean isNotCoherent(uwGraph graph) throws Exception {
        // We know v0 always exists, since it creates nodes iteratively
        DijkstraShortestPaths sPaths = new DijkstraShortestPaths(graph, "v0");
        ArrayList<Edge> es = sPaths.getShortestPaths();

        // We assume the edges with the largest weight (untouched/unreachable) are located in the end
        // and we want to find these ASAP
        Collections.reverse(es);

        boolean coherence = false;
        for(Edge e : es){
            coherence = (e.getWeight() == Double.POSITIVE_INFINITY);

            // If an unreachable vertex is found, we want to stop looping.
            if (coherence){
                System.out.println("Generate graph not coherent, generating again.");
                break;
            }
        }

        return coherence;
    }

    public uwGraph coherentGraph(int vertices, double density, boolean isWeighted) throws Exception {
        uwGraph graph;
        // Create a graph and check whether or not it is coherent and generate new one if not
        do {
            System.out.println(("Generating test graph with vertices: " + vertices + ", density: " + density + " and is weighted: " + isWeighted));
            graph = this.genGraphFromData(vertices, density, isWeighted);
        } while (isNotCoherent(graph));

        return graph;
    }

    public uwGraph wieghtedCompleteDenseGraph(int vertices) throws Exception {
        double density = (double) vertices - 1;
        return this.coherentGraph(vertices, density, true);
    }

    public uwGraph wieghtedDenseGraph(int vertices, double density) throws Exception {
        return this.coherentGraph(vertices, density, true);
    }

    public uwGraph unweightedCompleteDenseGraph(int vertices) throws Exception {
        double density = (double) vertices - 1;
        return this.coherentGraph(vertices, density, false);
    }

    public uwGraph unwieghtedDenseGraph(int vertices, double density) throws Exception {
        return this.coherentGraph(vertices, density, false);
    }

    /**
     * Generate graph, given amount of vertices, density in decimals, 1 being a complete graph and whether it's weighted or not
     * @param vertices
     * @param density
     * @param isWeighted
     * @return
     * @throws Exception
     */
    private uwGraph genGraphFromData(int vertices, double density, boolean isWeighted) throws Exception {
        System.out.println(density * ((double) (vertices*(vertices - 1))/(2.0)) / (double) vertices);
        if (density < ((double) vertices - 1.0)/((double) (vertices*(vertices - 1))/(2.0))) {
            throw new Exception("Density too low, must not be below minimum: " + ((double) vertices - 1.0)/((double) (vertices*(vertices - 1))/(2.0)) + ", all vertices cannot be connected\n");
        } else if (vertices < 2) {
            throw new Exception("Not enough vertices given, need atleast 2\n");
        } else if (density > 1.0) {
            // If density is above the maximum density, we set it to the maximum density
            density = 1.0;
        }

        // Instantiate graph
        uwGraph g = new uwGraph(DefaultWeightedEdge.class);
        ArrayList<String> vList = new ArrayList<String>();

        // Insert vertices intro graph and arraylist
        for (int i = 0; i < vertices; i++) {
            String v = ("v" + i);
            g.addVertex(v);
            vList.add(v);
        }

        // Generate all possible edges for graph
        ArrayList<Pair<String, String>> edges = generateEdges(vList);
        // Sleep for half a second, to insure a new seed is available.
        Thread.sleep(500);
        Random rndGen = new Random(System.nanoTime());
        double margin = density * ((double) Integer.MAX_VALUE);
        System.out.print(margin);
        // Keep iterating until we've depleted the edge pool
        for (Pair<String, String> p : edges){
            if (rndGen.nextInt(Integer.MAX_VALUE) < margin){
                g.addEdge(p.getKey(), p.getValue());
                double weight = (isWeighted ? ((double) rndGen.nextInt(1000)) : 1.0);
                g.setEdgeWeight(g.getEdge(p.getKey(), p.getValue()), weight);
            }
        }

        return g;
    }

    private ArrayList<Pair<String, String>> generateEdges(ArrayList<String> vList) {
        ArrayList<Pair<String, String>> edges = new ArrayList<Pair<String, String>>();
        ArrayList<String> tmpVList = new ArrayList<String>();
        for(String v : vList){
            for(String u : tmpVList){
                if (!u.isEmpty()){
                    edges.add(new Pair<String, String>(u, v));
                }
            }
            tmpVList.add(v);
        }

        return edges;
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
