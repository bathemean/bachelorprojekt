
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class ThorupZwickSpanner {

    private int k;
    private uwGraph graph;

    public ThorupZwickSpanner(uwGraph g, int k) {

        this.k = k;
        this.graph = g;
        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into parititions.
        HashMap<Integer, ArrayList<String>> p = partition(vertices);

        // Compute distance between A_k and every vertex v.
        ArrayList<Distance> d = distances(p);
        System.out.println("Distances: " + d);
        System.out.println("Distances length: " + d.size());

        //ArrayList<ArrayList<DijkstraShortestPath>> spt = shortestPathsTrees(g, p);
        //System.out.println(spt);
        
        // UNITE THE TREES!
        //uniion(trees);




    }

    private ArrayList<String> vertexSetToArray(Set<String> set) {

        ArrayList<String> array = new ArrayList<>();

        for(String e : set) {
            array.add(e);
        }

        return array;

    }

    private HashMap<Integer, ArrayList<String>> partition(ArrayList<String> vertices) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        int n = vertices.size();
        double margin =  Math.pow(n, (-1.0) / k);
        double intMargin =  ((double) Integer.MAX_VALUE) * margin;

        Random gen = new Random();

        for(int i = 1; i < k; i++) {

            ArrayList<String> subset = new ArrayList<>();

            for(String v : partitions.get(i - 1)) {
                int next = gen.nextInt(Integer.MAX_VALUE);
                if(intMargin > next) {
                    subset.add(v);
                }
            }
            System.out.println(subset.toArray().length);
            partitions.put(i, subset);

        }

        partitions.put(k, null);

        return partitions;

    }

    /**
     * Iterates over the A_i's from K-1 down to 0.
     * Still untested.
     * @param partitions unsorted partitioning of the vertices in the graph.
     * @return ArrayList of the distances found, with partition, witness, target and the found distance.
     */
    private ArrayList<Distance> distances(HashMap<Integer, ArrayList<String>> partitions){

        ArrayList<Distance> distancesCollection = new ArrayList<>();

        for (Integer i = k-1; i > 0 ; i--) {

            ArrayList<String> ai = partitions.get(i);
            // Create a copy of the original graph, so that we can add a source vertex.
            uwGraph distanceGraph = this.graph.cloneGraph();
            String sourceV;
            sourceV = "sourceV";
            distanceGraph.addVertex(sourceV);

            // Build source edges to find the witnesses fast
            for (String u : ai) {
                distanceGraph.addEdge(sourceV, u);
                distanceGraph.setEdgeWeight(distanceGraph.getEdge(sourceV, u), 0);
            }

            // For all vertices in the graph, find the shortest path to the partition (witness).
            for (String v : this.graph.vertexSet()) {

                DijkstraShortestPath path = new DijkstraShortestPath(distanceGraph, sourceV, v);
                double weight = path.getPathLength();

                // The path must be bigger than just the source vertex and the partition vertex.
                if(weight > 0) {
                    try {
                        // Assuming the first edge is the source/dummy edge, we pick the second
                        Object witnessEdge = path.getPathEdgeList().get(1);
                        String witness = distanceGraph.getEdgeSource((DefaultWeightedEdge) witnessEdge);

                        Distance distance = new Distance(i, witness, v, weight);
                        distancesCollection.add(distance);
                    } catch(Exception e) {
                        System.out.println("Exception: " + e.toString());
                    }

                }
            }

        }

        return distancesCollection;
    }

    private ArrayList<ArrayList<DijkstraShortestPath>> shortestPathsTrees(uwGraph g, HashMap<Integer, ArrayList<String>> partitions) {

        ArrayList<ArrayList<DijkstraShortestPath>> shortestPaths = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            ArrayList<String> subset = new ArrayList<>();

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
                    ArrayList<DijkstraShortestPath> sp = new ArrayList<>();

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

    private /*Something here*/ void trees(){

    }
    private /*Something here*/ void uniion(){

    }


}
