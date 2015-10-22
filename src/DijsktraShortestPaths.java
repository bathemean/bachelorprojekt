import com.sun.tools.javac.util.Pair;
import javafx.util.Pair;

import java.util.*;

public class DijsktraShortestPaths {

    /**
     * Based on the algorithm in Cormen et al. 3. ed., page 658
     */

    private PriorityQueue<HashMap<Integer, String>> vertices;
    private uwGraph graph;
    private String source;
    private int shortestPaths;

    public DijsktraShortestPaths(uwGraph graph, String source){
        this.graph = graph;
        this.source = source;
        PriorityQueue<Pair<Double, String>> queue = this.initSS();

        // MAGICKZ
        ArrayList<Pair<Double, String>> verticeList = new ArrayList<>();

        while(!queue.isEmpty()) {

            // extract-min
            Pair<Double, String> u = queue.poll();

            verticeList.add(u);

            //Object[] edges = this.graph.getEdgesFrom(u.snd);
            this.graph.getAdjecentVertices(u.snd);
        }

        this.setShortestPaths();
    }

    private PriorityQueue<Pair<Double, String>> initSS(){

        //  Initialize empty min-heap v => (d, u)
        PriorityQueue<Pair<String, Pair<Double, String>>> queue = new PriorityQueue();

        // Fetch all vertices in given graph
        Set<String> vertices = this.graph.vertexSet();

        Double length = Double.POSITIVE_INFINITY;

        // Iterate through vertices and create
        for(String vert : vertices) {
            Pair<Double, String> p = new Pair<>(length, vert);
            queue.add(p);
        }

        return queue;

    }

    /**
     * Relaxes an egde and updates (creates a new) vertice
     * Else it just returns the source vertice as it is
     */
    private Pair relax(Pair source, Pair predecessor, Double weight) {

        Pair<Double, String> updatedVertice;
        double cumWeight = (((double) predecessor.getKey()) + weight);
        if ((double) source.getKey() > cumWeight){
            updatedVertice = new Pair<>(cumWeight, (String) predecessor.getValue());
            return updatedVertice;
        }
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
    public void setShortestPaths() {
        this.shortestPaths = shortestPaths;
    }

    public uwGraph getGraph() {
        return this.graph;
    }

    public void setGraph(uwGraph graph) {
        this.graph = graph;
    }

}
