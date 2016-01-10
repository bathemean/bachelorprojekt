package main.spanner;

import main.DijkstraShortestPaths;
import main.graph.uwGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class GreedySpanner extends Spanner {

    public GreedySpanner() {

    }

    /**
     * The Greedy-Spanner algorithm originally developed by Althofer et. al.
     * @param g graph to create a spanner for.
     * @param r multiplication degree of desired spanner.
     * @return a spanner with multiplication degree r.
     */
    public uwGraph makeSpanner(uwGraph g, Integer r) throws Exception {
        this.startTiming();

        uwGraph gPling = g.copyGraphNoEdges();

        HashMap<Object, Integer> edgeWeights = g.getEdgeWeights();

        // Sort edges by weight, smallest first.
        HashMap<DefaultWeightedEdge, Integer> edgesSorted = sortHashMapByValuesD(edgeWeights);

        // Iterate over all the edges.
        for (Map.Entry<DefaultWeightedEdge, Integer> entry : edgesSorted.entrySet()) {
            System.out.println(entry + "Greedy");
            DefaultWeightedEdge entryEdge = entry.getKey();
            String v, u;
            v = g.getEdgeSource(entryEdge);
            u = g.getEdgeTarget(entryEdge);

            DijkstraShortestPaths dijkstraShortestPath = new DijkstraShortestPaths(gPling, v);

            System.out.println("Dijk for " + v + " in spanner:");
            System.out.println(dijkstraShortestPath.getShortestPaths());
            if ((r * entry.getValue()) < dijkstraShortestPath.getPathWeight(u)) {
                gPling.addEdge(v, u);
                gPling.setEdgeWeight(gPling.getEdge(v, u), entry.getValue());
            }
            System.out.print(gPling + "\n");
        }

        this.endTiming();
        gPling.setRuntime(this.getRuntime());
        this.setStretch(g, gPling);

        return gPling;

    }

    public double setStretch(uwGraph g, uwGraph spanner) throws Exception {

        HashMap<String, Integer> alreadyIterated = new HashMap<String, Integer>();

        double currentStretch = 0.0;
        for (String v : g.vertexSet()) {
            DijkstraShortestPaths graphDijk = new DijkstraShortestPaths(g, v);
            DijkstraShortestPaths spannerDijk = new DijkstraShortestPaths(spanner, v);
            for (String u : g.vertexSet()) {
                if (!alreadyIterated.containsKey(u)) {
                    double stretch = spannerDijk.getPathWeight(u) / graphDijk.getPathWeight(u);
                    if (stretch > currentStretch) {
                        currentStretch = stretch;
                    }
                }
            }
            alreadyIterated.put(v, 1);
        }

        return currentStretch;
    }


    /**
     * From StackOverflow:
     * http://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
     *
     * @param passedMap hashmap to be sorted.
     * @return the sorted hashmap.
     */
    public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);

        LinkedHashMap sortedMap = new LinkedHashMap();

        for (Object val : mapValues) {

            for (Object key : mapKeys) {
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
