
import org.jgrapht.alg.DijkstraShortestPath;

import java.lang.reflect.Array;
import java.util.*;

public class ThorupZwickSpanner {

    private int k;
    private uwGraph graph;

    public ThorupZwickSpanner(uwGraph g, int k) {

        this.k = k;
        this.graph = g;
        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into parititions.
        HashMap<Integer, ArrayList<String>> p = nonRandPartition(vertices);

        // Compute distance between A_k and every vertex v.
        //distances(partition p, vertex v);

        ArrayList<ArrayList<DijkstraShortestPath>> spt = shortestPathsTrees(g, p);
        System.out.println(spt);
        
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

    private HashMap<Integer, ArrayList<String>> nonRandPartition(ArrayList<String> vertices) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        for(int i = 1; i < k; i++) {

            ArrayList<String> prev = partitions.get(i - 1);
            ArrayList<String> prevPart = new ArrayList<>();

            for(int j = 1; j < prev.size(); j++) {
                prevPart.add(prev.get(j));
            }

            partitions.put(i, prevPart);
        }

        partitions.put(k, null);

        return partitions;

    }

    private HashMap<Integer, ArrayList<String>> partition(ArrayList<String> vertices) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        int n = vertices.size();
        int margin = (int) Math.pow(n, (-1.0)/k);

        Random gen = new Random();

        for(int i = 1; i < k; i++) {

            ArrayList<String> subset = new ArrayList<>();

            for(String v : partitions.get(i-1)) {
                int next = gen.nextInt(Integer.MAX_VALUE);
                System.out.println("Margin: " + margin + ", next: " + next);
                if(margin > next) {

                    subset.add(v);
                }

            }

            partitions.put(i, subset);

        }

        partitions.put(k, null);

        return partitions;

    }

    /**
     * Iterates over the A_i's from K-1 down to 0.
     * Still untested.
     * @param ai with indice in descending order, expectiong k not to be in.
     * @return Hashmap with found distance for each vertex in G to A_i,
     */
    private LinkedHashMap<Integer, LinkedHashMap<String, Integer>> distances(LinkedHashMap<Integer, ArrayList<String>> ai){

        // Iterate through each A_i
        LinkedHashMap<Integer, LinkedHashMap<String, Integer>> distancesCollection = new LinkedHashMap<Integer, LinkedHashMap<String, Integer>>();
        for (Integer i = k-1; i >= 0 ; i--) {
            uwGraph distanceGraph = this.graph.cloneGraph();
            String sourceV;
            sourceV = "sourceV";
            // Build source edges to find the witnesses fast
            for (String u : ai.get(i)) {
                distanceGraph.addEdge(sourceV, u);
                distanceGraph.setEdgeWeight(distanceGraph.getEdge(sourceV, u), 0);
            }

            // Find distances and witnesses from A_i to v
            LinkedHashMap<String, Integer> distancesAi = new LinkedHashMap<String, Integer>();
            for (String v : this.graph.vertexSet()) {
                DijkstraShortestPath distance = new DijkstraShortestPath(distanceGraph, sourceV, v);
                double weight = distance.getPathLength();
                // Assuming the first edge is the source/dummy edge, we pick the second
                Object witnessEdge = distance.getPathEdgeList().get(1);
                // witness to add
                String witness = "I have no freaking clue";
                distancesAi.put(witness, (int) weight);
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
