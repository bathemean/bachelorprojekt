package main;

import main.graph.uwGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DijkstraShortestPaths {

    public MinHeap heap;
    private uwGraph graph;
    private String source;
    private ArrayList<Edge> shortestPath;
    public HashMap<String, Edge> shortestPathLazy;
    private HashMap<String, Double> deltas;

    /**
     * Dijkstra's algorithm for finding shortests paths. Based on the algorithm in Cormen et al. 3. ed., page 658.
     * @param graph The graph to find paths in.
     * @param source The source of the path.
     * @throws Exception
     */
    public DijkstraShortestPaths(uwGraph graph, String source) throws Exception {

        // Clone the graph, so that we don't modify the existing one.
        this.graph = graph.cloneGraph();
        this.source = source;

        this.heap = this.initialize();
        this.shortestPath = new ArrayList<Edge>();
        this.shortestPathLazy = new HashMap<String, Edge>();
        this.setShortestPaths();

        this.deltas = null;

    }

    /**
     * Modified Dijkstra algorithm from Approximate Distance Oracles.
     * @param graph The graph to find paths in.
     * @param source The source of the path.
     * @param deltas The distances from partition to vertex.
     * @throws Exception
     */
    public DijkstraShortestPaths(uwGraph graph, String source, HashMap<String, Double> deltas) throws Exception {
        // Clone the graph, so that we don't modify the existing one.
        this.graph = graph.cloneGraph();
        this.source = source;

        this.deltas = deltas;

        this.heap = this.initialize();
        this.shortestPath = new ArrayList<Edge>();
        this.shortestPathLazy = new HashMap<String, Edge>();
        this.setShortestPaths();



    }

    /**
     * Performs initialization.
     * @return MinHeap containing the vertex set for the supplied graph. All but the source vertex has weight infinity,
     * with the source vertex having weight zero.
     */
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
                // Insert source vertex data in the head of the MinHeap.
                heap.addHead(v);
            } else { // Set all others to vInit (infinite)
                v = new Edge(vert, "", Double.POSITIVE_INFINITY);
                // Insert vertice data in the MinHeap.
                heap.add(v);
            }
        }

        return heap;
    }

    /**
     * Finds the shortests paths. If running the modified Dijkstra, this.limit will be different from null.
     * If running vanilla Dijkstra, this.limit will be null. Stores the shortests path in this.shortestPath.
     * @throws Exception
     */
    protected void setShortestPaths() throws Exception {

        //System.out.println("\nNew Dijk");
        if (this.heap.size() > 0) {
            do {

                //System.out.println(this.heap.getHeap());
                //System.out.println(this.heap.mapping);
                Edge u = this.heap.extractMin();
                //System.out.println("\nNew vertice: " + u.getSource());
                //System.out.println(this.heap.getHeap());
                //System.out.println(this.heap.mapping);
                // Add the minimal vertex to our shortest path.
                this.shortestPath.add(u);
                this.shortestPathLazy.put(u.getSource(), u);

                // We only want to check the vertex, if it has already been relaxed i.e. u.weight < infinity
                if (u.getWeight() < Double.POSITIVE_INFINITY) {
                    //System.out.println("Vertice: " + u.getSource() + " weight; " + u.getWeight());
                    Edge[] adj = this.graph.getAdjecentVertices(u.getSource());
                    // Decrease key for all the adjacent vertices.
                    for (Edge e : adj) {
                        // Only try to relax if the source vertex has not yet been evaluated
                        if (!this.shortestPathLazy.containsKey(e.getTarget())) {
                            //System.out.println(e);
                            Double limit = Double.POSITIVE_INFINITY;
                            if (this.deltas != null) {
                                if (this.deltas.containsKey(e.getTarget())) {
                                    limit = this.deltas.get(e.getTarget());
                                }
                            }

                            if (this.deltas == null || (u.getWeight() + e.getWeight()) < limit) {
                                //System.out.println(e.getTarget() + " " + u.getSource());
                                this.heap.decreaseKey(e, u);
                                //System.out.println("Current heap: " + this.heap.getHeap());
                            }
                        }
                    }
                }

            } while (this.heap.size() > 0);
        }

        // Extract the remaining vertex and add it to our path.
//        Edge u = this.heap.extractMin();
//        this.shortestPath.add(u);
//        this.shortestPathLazy.put(u.getSource(), u);
        //System.out.println("Dijkstra: " + shortestPath);

    }

    public ArrayList<Edge> getShortestPaths() {
        return this.shortestPath;
    }

    /**
     * Get the weight of vertex u in.
     * @param u Some vertex.
     * @return The weight of the vertex in the shortest path.
     */
    public double getPathWeight(String u) throws Exception {
        return this.shortestPathLazy.get(u).getWeight();
    }

    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }
}
