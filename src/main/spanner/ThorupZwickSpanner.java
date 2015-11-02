package main.spanner;

import main.DijkstraShortestPaths;
import main.Distance;
import main.VertexElement;
import main.graph.uwGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class ThorupZwickSpanner {

    private int k;
    private uwGraph graph;

    private HashMap<Integer, ArrayList<String>> partitions;
    private ArrayList<Distance> distances;

    public ThorupZwickSpanner() {

    }

    public uwGraph makeSpanner(uwGraph g, int k) {

        this.k = k;
        this.graph = g;

        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into parititions.
        this.partitions = partition(vertices);
        System.out.println("Partitions: " + this.partitions);

        // Compute distance between A_k and every vertex v.
        distances(this.partitions);
        //System.out.println("Distances: " + this.distances);
        //System.out.println("Distances length: " + this.distances.size());

        //ArrayList<ArrayList<DijkstraShortestPath>> spt = shortestPathsTrees(g, p);
        //System.out.println(spt);

        // UNITE THE TREES!
        //uniion(trees);


        return null;

    }

    private ArrayList<String> vertexSetToArray(Set<String> set) {

        ArrayList<String> array = new ArrayList<String>();

        for (String e : set) {
            array.add(e);
        }

        return array;

    }

    private HashMap<Integer, ArrayList<String>> partition(ArrayList<String> vertices) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<Integer, ArrayList<String>>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        int n = vertices.size();
        double margin = Math.pow((n / Math.log(n)), (-1.0) / k);
        double intMargin = ((double) Integer.MAX_VALUE) * margin;

        Random gen = new Random();

        for (int i = 1; i < k; i++) {

            ArrayList<String> subset = new ArrayList<String>();

            for (String v : partitions.get(i - 1)) {
                int next = gen.nextInt(Integer.MAX_VALUE);
                if (intMargin > next) {
                    subset.add(v);
                }
            }
            partitions.put(i, subset);

        }

        partitions.put(k, null);

        return partitions;

    }


    private void distances(HashMap<Integer, ArrayList<String>> partitions) {

        uwGraph spanner = this.graph.copyGraphNoEdges();

        for (int i = k - 1; i >= 0; i--) {
            System.out.println("==== i: " + i + " ====");
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

//System.out.println(tmpGraph.toString());

            try {
                DijkstraShortestPaths path = new DijkstraShortestPaths(tmpGraph, source, 0.0);
                System.out.println(tmpGraph);
                System.out.println("SP " + path.getShortestPaths());


            } catch (Exception e) {
                e.printStackTrace();
            }




    /*
            for (String v : this.graph.vertexSet()) {

                DijkstraShortestPath path = new DijkstraShortestPath(tmpGraph, source, v);
                double pathLength = path.getPathLength();
//System.out.println("EdgeList: " + path.getPathEdgeList() + " L: " + pathLength);
                if (pathLength > 0 && !Double.isInfinite(pathLength)) {
                    List<DefaultWeightedEdge> edges = path.getPathEdgeList();

                    for (DefaultWeightedEdge e : edges) {
                        String edgeSource = tmpGraph.getEdgeSource(e);
                        String edgeTarget = tmpGraph.getEdgeTarget(e);
                        double edgeWeight = tmpGraph.getEdgeWeight(e);

                        // Dont inlucde source vertex in new graph
                        if (!edgeSource.equals(source)) {
                            spanner.addEdge(edgeSource, edgeTarget);
                            spanner.setEdgeWeight(spanner.getEdge(edgeSource, edgeTarget), edgeWeight);
                        }

                    }

                }


            }*/

            System.out.println("Spanner: " + spanner);
        }
    }

    /**
     * Iterates over the A_i's from K-1 down to 0.
     * Still untested.
     *
     * @param partitions unsorted partitioning of the vertices in the graph.
     * @return ArrayList of the distances found, with partition, witness, target and the found distance.
     */
    private ArrayList<Distance> distancesOLD(HashMap<Integer, ArrayList<String>> partitions) {

        ArrayList<Distance> distancesCollection = new ArrayList<Distance>();

        ArrayList<String> sourceTagets = new ArrayList<String>();

        for (Integer i = k - 1; i >= 0; i--) {

            ArrayList<String> ai = partitions.get(i);

            // Create a copy of the original graph, so that we can add a source vertex.
            uwGraph distanceGraph = this.graph.cloneGraph();
            String sourceV;
            sourceV = "sourceV";
            distanceGraph.addVertex(sourceV);

            // Build source edges to find the witnesses fast
            for (String u : ai) {
                distanceGraph.addEdge(sourceV, u);

                if (!sourceTagets.contains(u)) {
                    sourceTagets.add(u);
                }

                distanceGraph.setEdgeWeight(distanceGraph.getEdge(sourceV, u), 0);
            }

            // For all vertices in the graph, find the shortest path to the partition (witness).
            //System.out.println(this.graph.vertexSet().toString());
            for (String v : this.graph.vertexSet()) {

                DijkstraShortestPath path = new DijkstraShortestPath(distanceGraph, sourceV, v);
                double weight = path.getPathLength();
                System.out.println(i + " EdgeList: " + path.getPathEdgeList().toString() + " " + weight);
                // The path must be bigger than just the source vertex and the partition vertex.
                if (weight > 0 && ai.size() > 0) {
                    try {
                        // Assuming the first edge is the source/dummy edge, we pick the second
                        Object witnessEdge = path.getPathEdgeList().get(1);
                        String witness = distanceGraph.getEdgeSource((DefaultWeightedEdge) witnessEdge);

                        if (!witness.equals(v)) {
                            Distance distance = new Distance(i, witness, v, weight);
                            distancesCollection.add(distance);
                        }
                    } catch (Exception e) {
                        System.out.printf("Exception: %s, target: %s, weight; %f\n", e.toString(), v, weight);
                    }

                } else {
                    //System.out.println(i + " " + v + " ");

                }
            }

        }

        if (sourceTagets.size() != 10) {
            sourceTagets.sort(String.CASE_INSENSITIVE_ORDER);
            System.out.println("Source Targets: " + sourceTagets);
        } else {
            System.out.println("All nodes sourced.");
        }

        System.out.println("=== DISTANCES ===");
        for (Distance d : distancesCollection) {
            System.out.println(d);
        }
        System.out.println("=================");


        return distancesCollection;
    }

    private ArrayList<ArrayList<DijkstraShortestPath>> shortestPathsTrees(uwGraph g, HashMap<Integer, ArrayList<String>> partitions) {

        ArrayList<ArrayList<DijkstraShortestPath>> shortestPaths = new ArrayList<ArrayList<DijkstraShortestPath>>();

        for (int i = 0; i < k; i++) {
            ArrayList<String> subset = new ArrayList<String>();

            ArrayList<String> cur = partitions.get(i);
            ArrayList<String> next = partitions.get(i + 1);

            // Computer the subset A_i - A_i+1.
            for (String c : cur) {

                if (next != null) {
                    if (!next.contains(c)) {
                        subset.add(c);
                    }
                } else {
                    subset.add(c);
                }

            }

            if (next != null) {
                // Find the shortest path from each vertex in the subset, to all the vertices in the graph.
                for (String w : subset) {
                    ArrayList<DijkstraShortestPath> sp = new ArrayList<DijkstraShortestPath>();

                    for (String v : next) {
                        DijkstraShortestPath p = new DijkstraShortestPath(g, w, v);
                        sp.add(p);
                    }
                    shortestPaths.add(sp);
                }
            }

        }

        return shortestPaths;
    }

    private /*Something here*/ void trees() {

    }

    private /*Something here*/ void uniion() {

    }

    public HashMap<Integer, ArrayList<String>> getPartitions() {
        return this.partitions;
    }

    public ArrayList<Distance> getDistances() {
        return this.distances;
    }

}
