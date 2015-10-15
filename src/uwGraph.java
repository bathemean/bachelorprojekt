import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.HashMap;

public class uwGraph extends ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> {

    public uwGraph(Class<DefaultWeightedEdge> base) {
        super(base);
    }

    protected uwGraph copyGraphNoEdges() {

        // This is our G', that represents all the edges added. It contains the same vertices as G(g)
        uwGraph gPling = new uwGraph(DefaultWeightedEdge.class);

        org.jgrapht.Graphs.addAllVertices(gPling, this.vertexSet());

        return gPling;

    }

    protected uwGraph cloneGraph() {
        uwGraph graphClone = copyGraphNoEdges();
        // We might be able to just use the line below to do all the magic
        org.jgrapht.Graphs.addAllEdges(graphClone, this, this.edgeSet());
        return graphClone;
    }

    /**
     * Gets all the edge, weight pairs.
     * @return (edge, weight) pairs.
     */
    protected HashMap<Object, Integer> getEdgeWeights() {

        HashMap<Object, Integer> edgeWeights = new HashMap<>();
        // Populating HasMap with edges and weights
        for (Object edge : this.edgeSet().toArray()) {
            Integer weight = (int) this.getEdgeWeight((DefaultWeightedEdge) edge);
            edgeWeights.put(edge, weight);
        }

        return edgeWeights;

    }

    public String toString() {
        return super.toString();
    }

    public void getStretch() {


        Object[] vertices = this.vertexSet().toArray();
        Object[] edges = this.edgeSet().toArray();

    }

    public double getDensity() {

        double numVertices = this.vertexSet().size();
        double numEdges = this.edgeSet().size();
        double result = numEdges / numVertices;

        return result;

    }

    public int getTotalWeight() {

        Object[] weights = this.getEdgeWeights().values().toArray();
        int sum = 0;

        for(Object w : weights) {
            sum += (int) w;
        }

        return sum;

    }

    public void getDegree() {

    }

    public String getMetricsAsString() {

        String str;

        str = "Total Weight: " + this.getTotalWeight() + ".";
        str += " Density: " + this.getDensity() + ".";

        return str;

    }
}
