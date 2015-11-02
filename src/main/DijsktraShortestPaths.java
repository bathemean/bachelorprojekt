package main;

import main.graph.uwGraph;

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


        // Iterate through vertices and create
        for (String vert : vertices) {
            // VertexElement used for initializing vertices in the heap.
            VertexElement<Double, String> vInit = new VertexElement<Double, String>(Double.POSITIVE_INFINITY, "");
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

        while(this.heap.size() > 1) {

            VertexElement<String, VertexElement<Double, String>> u = this.heap.extractMin();

            shortestPath.add(u);

            VertexElement[] adj = this.graph.getAdjecentVertices(u.getKey());

            for(VertexElement v : adj) {
                this.heap.decreaseKey(u, (Double) v.getKey(), (String) v.getValue());
            }

        }
        VertexElement<String, VertexElement<Double, String>> u = this.heap.extractMin();
        shortestPath.add(u);
        System.out.println(this.shortestPath);
    }


    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }
}
