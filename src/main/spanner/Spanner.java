package main;

import main.graph.uwGraph;
import main.spanner.Spanner;
import org.jgrapht.alg.DijkstraShortestPath;
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
    public uwGraph makeSpanner(uwGraph g, Integer r){
        super.startTiming();

        Object[] vertices = g.vertexSet().toArray();
        Object[] edges = g.edgeSet().toArray();

        uwGraph gPling = g.copyGraphNoEdges();

        HashMap<Object, Integer> edgeWeights = g.getEdgeWeights();

        // Sort edges by weight, smallest first.
        HashMap<DefaultWeightedEdge, Integer> edgesSorted = sortHashMapByValuesD(edgeWeights);

        // Iterate over all the edges.
        for (Map.Entry<DefaultWeightedEdge, Integer> entry : edgesSorted.entrySet()) {
            Object v, u;
            v = g.getEdgeSource(entry.getKey());
            u = g.getEdgeTarget(entry.getKey());

            DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(gPling, v, u);

            if ((r * entry.getValue()) < dijkstraShortestPath.getPathLength()) {
                String vS = v.toString();
                String uS = u.toString();
                gPling.addEdge(vS, uS);
                gPling.setEdgeWeight(gPling.getEdge(vS, uS), entry.getValue());
            }
        }

        super.endTiming();
        gPling.setRuntime(super.getRuntime());

        return gPling;

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
