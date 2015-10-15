import com.sun.tools.javac.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.HashMap;

public class uwGraph extends ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> {

    private long runtime;

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

    protected void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public String toString() {
        return super.toString();
    }

    public void getStretch() {

        Object[] vertices = this.vertexSet().toArray();
        Object[] edges = this.edgeSet().toArray();

        // GI MIG DIJKSTRA!

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

    public int getHighestDegree() {

        Object[] vertices = this.vertexSet().toArray();
        int highest = 0;

        for(Object ov : vertices) {
            String v = ov.toString();
            if(this.degreeOf(v) > highest) {
                highest = this.degreeOf(v);
            }
        }

        return highest;

    }

    public long getRuntime() {
        return this.runtime;
    }

    public String getMetricsAsString() {

        String weight = "Total Weight: " + this.getTotalWeight() + ".";
        String density = "Density: " + this.getDensity() + ".";
        String hdegree = "Highest degree: " + this.getHighestDegree() + ".";
        String runtime = "Runtime: " + this.getRuntime() + " ms.";

        String str = weight + " " + density + " " + hdegree + " " + runtime;

        return str;

    }
}
