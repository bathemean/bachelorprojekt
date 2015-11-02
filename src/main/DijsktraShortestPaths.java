package main;

import com.sun.tools.javac.util.Pair;
import javafx.util.VertexElement;
import main.graph.uwGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Set;

public class DijsktraShortestPaths {

    /**
     * Based on the algorithm in Cormen et al. 3. ed., page 658
     */
    private MinHeap heap;
    private uwGraph graph;
    private String source;
    private ArrayList shortestPath;
    private int shortestPaths;

    public DijsktraShortestPaths(uwGraph graph, String source, Double w) throws Exception {
        this.graph = graph;
        this.source = source;
        this.heap = this.initSS();
        this.shortestPath = new ArrayList();
        this.setShortestPaths();
    }

    private MinHeap initSS() {

        MinHeap heap = new MinHeap();

        // Fetch all vertices in given graph
        Set<String> vertices = this.graph.vertexSet();

        // VertexElement used for initializing vertices in the heap.
        VertexElement<Double, String> vInit = new VertexElement<Double, String>(Double.POSITIVE_INFINITY, null);

        // Iterate through vertices and create
        for (String vert : vertices) {
            VertexElement<String, VertexElement<Double, String>> v;

            // Set the source vertex to weight 0.
            if (vert.equals(this.source)) {
                VertexElement<Double, String> vSource = new VertexElement<Double, String>(0.0, "");
                // (Target,
                v = new VertexElement<String, VertexElement<Double, String>>(vert, vSource);
            } else {
            // Set all others to vInit (infinite)
                v = new VertexElement<String, VertexElement<Double, String>>(vert, vInit);
            }

            heap.add(v);

        }

        return heap;
    }

    /**
     * Relaxes an egde and updates (creates a new) vertice
     * Else it just returns the source vertice as it is
     */
    private VertexElement relax(VertexElement source, String predecessor, Double weight) {

        Double sourceDist = (Double) source.getKey();
        Double predDist = (Double) predecessor.getKey();


        if(predDist > sourceDist + weight) {

            predecessor.setKey( sourceDist + weight );
            predecessor.setValue(source);
        }


        /*
        VertexElement<Double, String> updatedVertice;
        double cumWeight = (((double) predecessor.getKey()) + weight);

        if ((double) source.getKey() > cumWeight) {
            updatedVertice = new VertexElement<Double, String>(cumWeight, (String) predecessor.getValue());
            return updatedVertice;
        }
        */

        return source;
    }

    private String getPredecessor(VertexElement<String, VertexElement<Double, String>> ele) {
        return ele.getValue().getValue();
    }

    private Double getWeight(VertexElement<String, VertexElement<Double, String>> ele) {
        return ele.getValue().getKey();
    }

    // Removal is opional
    // Setter and getters cuz I can motherfucking generate them (11!+?)
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getShortestPaths() {
        return this.shortestPaths;
    }

    /**
     * Magic function that actually creates the shortest paths
     * extracts min() etc (above mentioned data structure use poll/peek)
     * be aware that object in the queue should be extracted and re-inserted to maintain the integrity of the queue
     * i.o.w. don't just alter the object in the queue by reference.
     */
    public void setShortestPaths() throws Exception {

        while(this.heap.size() > 0) {

            VertexElement u = this.heap.extractMin();
            Double weight = getWeight(u);

            shortestPath.add(u);

            String[] adj = this.graph.getAdjecentVertices((String) u.getKey());

            for(String v : adj) {

                relax(u, v, weight);
            }

        }

    }


    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }
}
