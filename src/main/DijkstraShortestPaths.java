package main;

import main.graph.uwGraph;

import java.util.ArrayList;
import java.util.Set;

public class DijkstraShortestPaths {

    /**
     * Based on the algorithm in Cormen et al. 3. ed., page 658
     */
    private MinHeap heap;
    private uwGraph graph;
    private String source;
    private ArrayList<Edge> shortestPath;
    private boolean isTZ;

    public DijkstraShortestPaths(uwGraph graph, String source, boolean isTZ) throws Exception {

        // Clone the graph, so that we don't modify the existing one.
        this.graph = graph.cloneGraph();
        this.source = source;
        this.isTZ = isTZ;

        this.heap = this.initialize();
        this.shortestPath = new ArrayList<Edge>();
        this.setShortestPaths();

    }

    private MinHeap initialize() {

        MinHeap heap = new MinHeap();

        // Fetch all vertices in given graph
        Set<String> vertices = this.graph.vertexSet();

        // Iterate through vertices
        for (String vert : vertices) {

            Edge v;

            // Set the source vertex to weight 0.
            if (vert.equals(this.source)) {

                v = new Edge(vert, "", 0.0);
                // Insert source vertice data in the head of the MinHeap.
                heap.addHead(v);
            } else { // Set all others to vInit (infinite)

                v = new Edge(vert, null, Double.POSITIVE_INFINITY);
                // Insert vertice data in the MinHeap.
                heap.add(v);
            }
        }

        return heap;
    }

    /**
     * Magic function that actually creates the shortest paths
     * extracts min() etc (above mentioned data structure use poll/peek)
     * be aware that object in the queue should be extracted and re-inserted to maintain the integrity of the queue
     * i.o.w. don't just alter the object in the queue by reference.
     */
    protected void setShortestPaths() throws Exception {

        while(this.heap.size() > 1) {

            Edge u = this.heap.extractMin();

            // Add the minimal vertex to our shortest path.
            shortestPath.add(u);

            Edge[] adj = this.graph.getAdjecentVertices(u.getSource());

            // Decrease key for all the adjacent vertices.
            for(Edge e : adj) {
                this.heap.decreaseKey(u, e.getWeight(), e.getTarget());
            }

        }

        // Extract the remaining vertex and add it to our path.
        Edge u = this.heap.extractMin();
        shortestPath.add(u);
        //System.out.println("Dijkstra: " + shortestPath);

    }

    public ArrayList<Edge> getShortestPaths() {
        return this.shortestPath;
    }

    public double getPathWeight(String u) {
        return this.heap.getVertex(u).getValue().getWeight();
    }

    public Double getPathBetween(String source, String target) {

        for(Edge e : this.shortestPath) {
            if( (e.getSource() == source && e.getTarget() == target) || (e.getSource() == target && e.getTarget() == source)) {
                return e.getWeight();
            }
        }

        return Double.POSITIVE_INFINITY;

    }

    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }
}
