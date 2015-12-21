package main.spanner;

import main.DijkstraShortestPaths;
import main.Edge;
import main.graph.uwGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class ThorupZwickSpanner extends Spanner{

    private int k;
    private uwGraph graph;
    private uwGraph spanner;

    private HashMap<Integer, ArrayList<String>> partitions;
    private ArrayList<Edge> distances;

    /**
     * Object that can create ThorupZwick spanners, according to the method described in Approximate Distance Oracles.
     */
    public ThorupZwickSpanner() {
        this.distances = new ArrayList<Edge>();
    }

    /**
     * Creates and returns a ThorupZwick spanner from the supplied graph, with some multiplication factor.
     * @param g The graph to make the spanner from.
     * @param k The multiplication factor.
     * @return A spanner of the graph g, with multiplication factor k.
     */
    public uwGraph makeSpanner(uwGraph g, int k) {
        this.startTiming();
        this.k = k;
        this.graph = g;

        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into partitions.
        this.partitions = partition(vertices);

        // Compute distance between A_k and every vertex v.
        try {
            distances(this.partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the spanner.
        this.spanner = this.union();
        this.endTiming();
        this.spanner.setRuntime(this.getRuntime());
        return this.spanner;

    }

    /**
     * Identical to makeSpanner(), but non-randomized.
     */
    public uwGraph makeTestSpanner(uwGraph g, int k) {

        this.k = k;
        this.graph = g;

        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into parititions.
        //this.partitions = partition(vertices);
        //System.out.println("Partitions: " + this.partitions);



        HashMap<Integer, ArrayList<String>> p0 = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> a0 = new ArrayList<String>();
        ArrayList<String> b0 = new ArrayList<String>();
        a0.add("v1");
        a0.add("v2");
        a0.add("v3");
        a0.add("v4");
        p0.put(0, a0);
        b0.add("v2");
        b0.add("v3");
        b0.add("v4");
        p0.put(1, b0);
        p0.put(2, null);

        HashMap<Integer, ArrayList<String>> p1 = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> a1 = new ArrayList<String>();
        ArrayList<String> b1 = new ArrayList<String>();
        a1.add("v1");
        a1.add("v2");
        a1.add("v3");
        a1.add("v4");
        b1.add("v3");
        b1.add("v4");
        p1.put(0, a1);
        p1.put(1, b1);
        p1.put(2, null);

        HashMap<Integer, ArrayList<String>> p2 = new HashMap<Integer, ArrayList<String>>();
        ArrayList<String> a2 = new ArrayList<String>();
        ArrayList<String> b2 = new ArrayList<String>();
        a2.add("v1");
        a2.add("v2");
        a2.add("v3");
        a2.add("v4");
        b2.add("v4");
        p2.put(0, a2);
        p2.put(1, b2);
        p2.put(2, null);



        ArrayList<HashMap<Integer, ArrayList<String>>> ps = new ArrayList<HashMap<Integer, ArrayList<String>>>();
        ps.add(p0);
        ps.add(p1);
        ps.add(p2);

        for(HashMap<Integer, ArrayList<String>> p : ps) {
//            System.out.println("_____________________________");
//            System.out.println(p);

            // Compute distance between A_k and every vertex v.
            try {
                distances(p);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            System.out.println("ds: " + this.distances);

            this.spanner = this.union();
            //return this.spanner;

        }

        return null;

    }

    /**
     * Takes a vertex set and returns it as an ArrayList.
     * @param set some Set.
     * @return An ArrayList og the supplied set.
     */
    private ArrayList<String> vertexSetToArray(Set<String> set) {

        ArrayList<String> array = new ArrayList<String>();

        for (String e : set) {
            array.add(e);
        }

        return array;

    }

    /**
     * Partitions the vertices, using probability n^(-1/k).
     * @param vertices The vertex set of the graph.
     * @return HashMap of the partitions. Key is the index of the partitions, while value is an ArrayList of the
     * vertices of the partition.
     */
    private HashMap<Integer, ArrayList<String>> partition(ArrayList<String> vertices) {

        // Format: {partition index, {partition members}}
        HashMap<Integer, ArrayList<String>> partitions = new HashMap<Integer, ArrayList<String>>();

        // Put all vertices into A_0.
        partitions.put(0, vertices);

        // Compute the probability.
        int n = vertices.size();
        double margin = Math.pow((n / Math.log(n)), (-1.0) / k);
        double intMargin = ((double) Integer.MAX_VALUE) * margin;

        Random gen = new Random();

        // Assign vertices into partitions of decreasing size.
        // Partition 0 should include all vertices in the graph, while partition 1 through k-1 should contain
        // increasingly smaller subsets of the vertex set.
        for (int i = 1; i < k; i++) {

            ArrayList<String> subset = new ArrayList<String>();

            // Fetch the previous partition and with the probability computed above,
            // randomly determine which of the vertices to put into the next partition.
            for (String v : partitions.get(i - 1)) {
                int next = gen.nextInt(Integer.MAX_VALUE);
                if (intMargin > next) {
                    subset.add(v);
                }
            }
            partitions.put(i, subset);

        }

        // Finally the kth partition should be empty.
        partitions.put(k, null);

        return partitions;

    }

    /**
     * Finds the distances of all vertices to each partition.
     * @param partitions The partitioned vertex set.
     * @throws Exception
     */
    private void distances(HashMap<Integer, ArrayList<String>> partitions) throws Exception {

        for (int i = k-1; i >= 0; i--) {
//            System.out.println("=== i: " + i + " ===");
            uwGraph tmpGraph = this.graph.cloneGraph();

            ArrayList<String> ai = partitions.get(i);

            // Add Source vertex to the temporary graph
            String source = "source";
            tmpGraph.addVertex(source);


            // Assign the source vertex to all the nodes in ai.
            for (String v : ai) {
                tmpGraph.addEdge(source, v);
                tmpGraph.setEdgeWeight(tmpGraph.getEdge(source, v), 0);
            }

            // Compute all the shortest paths.
            DijkstraShortestPaths dijkstra = new DijkstraShortestPaths(tmpGraph, source);
            ArrayList<Edge> path = dijkstra.getShortestPaths();

//            System.out.println("Ai: " + ai);
//            System.out.println("Path: " + path);

            // Compute the distances (deltas) from all the vertices in the graph, to the partition.
            // Store them in a HashMap formatted (vertex, weight).
            HashMap<String, Double> deltas = new HashMap<String, Double>();

            for(Edge e : path) {

                // Don't include source vertices.
                if(!e.getSource().equals("source") && !e.getTarget().equals("source")) {
                    String vertex = e.getTarget();
                    Double weight = e.getWeight();
                    deltas.put(vertex, weight);
                }

            }
            // Calculate the subset of A(i) - A(i+1).
            ArrayList<String> ai1 = partitions.get(i + 1);
            // If i == k-1, k+1 is null, thus A(i) - A(i+1) == A(i).
            if (ai1 != null) {
                ai.removeAll(ai1);
            }

//            System.out.println("subs: " + ai);
//            System.out.println(deltas);
            // Grow a shortest paths tree from the vertices in the subset A(i) - A(i+1).
            for(String w : ai) {
                DijkstraShortestPaths dijk = new DijkstraShortestPaths(this.graph, w, deltas);
                ArrayList<Edge> p = dijk.getShortestPaths();

                this.distances.addAll(p);
            }

        }
    }

    /**
     * Unites the shortest paths into a single graph, making the spanner.
     * @return The spanner.
     */
    private uwGraph union() {

        ArrayList<Edge> spannerList = new ArrayList<Edge>();

        for(Edge e : this.distances) {
            if( !(e.getSource().equals("") || e.getTarget().equals("")) ) {
                spannerList.add(e);
            }
        }

        // Inititalize the spanner, by copying it with its vertices.
        uwGraph spanner = this.graph.copyGraphNoEdges();

        // Add all the edges with their weights in spannerList to the spanner.
        for(Edge e : spannerList) {
            Double weight = this.graph.getEdgeWeight( this.graph.getEdge(e.getSource(), e.getTarget()) );
            spanner.addEdge(e.getSource(), e.getTarget());
            spanner.setEdgeWeight( spanner.getEdge(e.getSource(), e.getTarget()), weight );
        }

        return spanner;

    }

    public HashMap<Integer, ArrayList<String>> getPartitions() {
        return this.partitions;
    }

}
