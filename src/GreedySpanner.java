import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.*;

public class GreedySpanner {

    /**
     * The Greedy-Spanner algorithm originally developed by Althofer et. al.
     * @param g graph to create a spanner for.
     * @param r multiplication degree of desired spanner.
     * @return a spanner with multiplication degree r.
     */
    public static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> GreedySpanner(ListenableUndirectedWeightedGraph g, Integer r){
        Object[] vertices = g.vertexSet().toArray();
        Object[] edges = g.edgeSet().toArray();

        // This is our G', that represents all the edges added. It contains the same vertices as G(g)
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> gPling =
                new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        org.jgrapht.Graphs.addAllVertices(gPling, g.vertexSet());

        HashMap<Object, Integer> edgeWeights = new HashMap<>();
        // Populating HasMap with edges and weights
        for (int i = 0; i < edges.length; i++) {
            Integer weight = (int) g.getEdgeWeight(edges[i]);
            edgeWeights.put(edges[i], weight);
        }
        // Sort edges by weight, smalles first.
        HashMap<DefaultWeightedEdge, Integer> edgesSorted = sortHashMapByValuesD(edgeWeights);

        // Iterate over all the edges.
        Iterator<Map.Entry<DefaultWeightedEdge, Integer>> iterEdges = edgesSorted.entrySet().iterator();
        while(iterEdges.hasNext()){
            Map.Entry<DefaultWeightedEdge, Integer> entry = iterEdges.next();

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

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)){
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
