package main;

import javafx.util.Pair;
import main.graph.uwGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Set;

public class DijsktraShortestPaths {

    /**
     * Based on the algorithm in Cormen et al. 3. ed., page 658
     */
    private MinHeap vertices;
    private uwGraph graph;
    private String source;
    private int shortestPaths;

    public DijsktraShortestPaths(uwGraph graph, String source, Double w) throws Exception {
        this.graph = graph;
        this.source = source;
        this.vertices = this.initSS();
        this.setShortestPaths();
    }

    private MinHeap initSS() {

        //  Initialize empty min-heap v => (d, u)
        MinHeap queue = new MinHeap();

        // Fetch all vertices in given graph
        Set<String> vertices = this.graph.vertexSet();

        // Init values for all vertices
        VertexElement<Double, String> vInit = new VertexElement<Double, String>(Double.POSITIVE_INFINITY, null);

        // Iterate through vertices and create
        for (String vert : vertices) {
            VertexElement<String, VertexElement<Double, String>> v;
            if (vert.equals(this.source)) {
                VertexElement<Double, String> vSource = new VertexElement<Double, String>(0.0, "");
                v = new VertexElement<String, VertexElement<Double, String>>(vert, vSource);
            } else {
                v = new VertexElement<String, VertexElement<Double, String>>(vert, vInit);
            }
            queue.add(v);
        }

        return queue;
    }

    /**
     * Relaxes an egde and updates (creates a new) vertice
     * Else it just returns the source vertice as it is
     */
    private VertexElement relax(VertexElement source, VertexElement predecessor, Double weight) {

        return source;
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
        while (this.vertices.size() > 0) {
            VertexElement<String, VertexElement<Double, String>> vertice = this.vertices.extractMin();

            ArrayList<String> adjV = new ArrayList<String>();
            for(DefaultWeightedEdge edge : this.getGraph().edgesOf(vertice.getKey())){
                adjV.add(this.findAdjVertex(vertice.getKey(), edge));
            }

        }

    }

    /**
     * Find the adj vertex to the evaluated vertex from an edge
     * @param vertice
     * @param edge
     * @return
     * @throws Exception
     */
    private String findAdjVertex(String vertice, DefaultWeightedEdge edge) throws Exception {
        Pair<String, String> vPair = this.getGraph().getEdgeComponents(edge.toString());
        if (vPair.getKey().equals(vertice)){
            return vPair.getValue();
        }else if (vPair.getValue().equals(vertice)){
            return vPair.getKey();
        } else {
            throw new Exception("Something went wrong, neither vertices matches evaluated one");
        }
    }
    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }
}
