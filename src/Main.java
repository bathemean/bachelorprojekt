import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.*;

public class Main {

    public static void main(String args []){
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph();

        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> spanner = greedySpanner(stringGraph, 2);

        System.out.println("Original: " + stringGraph.toString());
        System.out.println("Spanner:  " + spanner.toString());

    }

    private static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> createStringGraph()
    {
        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> g =
                new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

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

    public static ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> greedySpanner(ListenableUndirectedWeightedGraph g, Integer r){
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
        HashMap<DefaultWeightedEdge, Integer> edgesSorted = sortHashMapByValuesD(edgeWeights);

        // We need to figure out a structure that can contain (edge, weight) so we can sort them
        Iterator<Map.Entry<DefaultWeightedEdge, Integer>> iterEdges = edgesSorted.entrySet().iterator();
        while(iterEdges.hasNext()){
            Map.Entry<DefaultWeightedEdge, Integer> entry = iterEdges.next();

            Object v, u;
            v = g.getEdgeSource(entry.getKey());
            u = g.getEdgeTarget(entry.getKey());

            DefaultWeightedEdge evalEdge = entry.getKey();

            DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(gPling, v, u);

            if ((r * entry.getValue()) < dijkstraShortestPath.getPathLength()) {

                gPling.addEdge(v.toString(), u.toString());
                gPling.setEdgeWeight(gPling.getEdge(v.toString(), u.toString()), g.getEdgeWeight(evalEdge));
            }
        }

        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> spanner =
                new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        // iterate and add vertices/edges or possible to add a Set<> of them?
        return gPling;


    }

    /**
     * From StackOverflow:
     * http://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
     *
     * @param passedMap
     * @return
     */
    public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
//        Collections.sort(mapKeys);

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
