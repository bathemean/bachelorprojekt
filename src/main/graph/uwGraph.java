package main.graph;

import javafx.util.Pair;
import main.Edge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.Collection;
import java.util.HashMap;

public class uwGraph extends ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> {

    private long runtime;

    public uwGraph(Class<DefaultWeightedEdge> base) {
        super(base);
    }

    public uwGraph copyGraphNoEdges() {

        // This is our G', that represents all the edges added. It contains the same vertices as G(g)
        uwGraph gPling = new uwGraph(DefaultWeightedEdge.class);

        org.jgrapht.Graphs.addAllVertices(gPling, this.vertexSet());

        return gPling;
    }

    public uwGraph cloneGraph() {
        uwGraph graphClone = copyGraphNoEdges();
        // We might be able to just use the line below to do all the magic
        org.jgrapht.Graphs.addAllEdges(graphClone, this, this.edgeSet());
        return graphClone;
    }

    /**
     * Gets all the edge, weight pairs.
     *
     * @return (edge, weight) pairs.
     */
    public HashMap<Object, Integer> getEdgeWeights() {

        HashMap<Object, Integer> edgeWeights = new HashMap<Object, Integer>();
        // Populating HasMap with edges and weights
        for (Object edge : this.edgeSet().toArray()) {
            Integer weight = (int) this.getEdgeWeight((DefaultWeightedEdge) edge);
            edgeWeights.put(edge, weight);
        }

        return edgeWeights;
    }

    /**
     * Gets the components of the edge, source and target.
     *
     * @param edge The String edge in question.
     * @return Pair of Strings, source and target.
     */
    public Pair<String, String> getEdgeComponents(String edge) {
        String[] s = edge.split(":");

        String source = s[0].substring(1, s[0].length() - 1); // Remove initial ( and trailing whitespace.
        String target = s[1].substring(1, s[1].length() - 1); // Remove initial whitespace, and trailing ).

        Pair<String, String> components = new Pair<String, String>(source, target);
        return components;
    }

    /**
     * Alternative way to get edge components, passing an Object.
     *
     * @param edge The Object edge in question.
     * @return Same as above, Pair of Strings, source and target.
     */
    protected Pair<String, String> getEdgeComponents(Object edge) {
        return getEdgeComponents(edge.toString());
    }

    public Edge[] getAdjecentVertices(String v) {

        Object[] edges = this.edgesOf(v).toArray();
        Edge[] adjacent = new Edge[edges.length];

        int i = 0;
        for (Object e : edges) {
            String source = this.getEdgeComponents(e).getKey();
            String target = this.getEdgeComponents(e).getValue();

            String insert = "";
            if (source.equals(v)) {
                insert = target;
            } else {
                insert = source;
            }

            Double w = this.getEdgeWeight((DefaultWeightedEdge) e);
            //VertexElement ele = new VertexElement<Double, String>(w, insert);
            Edge ele = new Edge(null, insert, w);
            adjacent[i] = ele;
            i++;
            this.removeEdge((DefaultWeightedEdge) e);
        }

        return adjacent;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public String toString() {
        return super.toString();
    }

    // kør dijk for spanner og graf og divider de to afstande fra u til v.
    // Vælg den største brøk.
    public void getStretch() {

        Object[] vertices = this.vertexSet().toArray();
        Object[] edges = this.edgeSet().toArray();
    }

    public double getDensity() {

        double numVertices = this.vertexSet().size();
        double numEdges = this.edgeSet().size();
        double result = (2 * numEdges) / (numVertices * (numVertices -1));
        System.out.println(numEdges);
        return result;
    }

    public int getTotalWeight() {

        Collection<Integer> weights = this.getEdgeWeights().values();
        int sum = 0;

        for (int w : weights) {
            sum += w;
        }

        return sum;
    }

    public int getHighestDegree() {

        Object[] vertices = this.vertexSet().toArray();
        int highest = 0;

        for (Object ov : vertices) {
            String v = ov.toString();
            if (this.degreeOf(v) > highest) {
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
