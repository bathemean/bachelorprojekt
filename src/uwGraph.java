import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.HashMap;

public class uwGraph extends ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> {

    Object[] edges;

    public uwGraph(Class<DefaultWeightedEdge> base) {
        super(base);

        edges = this.edgeSet().toArray();

    }

    protected uwGraph copyGraph() {

        // This is our G', that represents all the edges added. It contains the same vertices as G(g)
        uwGraph gPling = new uwGraph(DefaultWeightedEdge.class);

        org.jgrapht.Graphs.addAllVertices(gPling, this.vertexSet());

        return gPling;

    }

    protected HashMap<Object, Integer> getEdgeWeights() {

        HashMap<Object, Integer> edgeWeights = new HashMap<>();
        // Populating HasMap with edges and weights
        for (Object edge : this.edges) {
            Integer weight = (int) this.getEdgeWeight((DefaultWeightedEdge) edge);
            edgeWeights.put(edge, weight);
        }

        return edgeWeights;

    }
}
