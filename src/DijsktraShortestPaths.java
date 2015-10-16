import java.util.*;

/**
 * Created by mcfallen on 10/15/15.
 */
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
        this.initSS();
        // This is where the magic happens
        this.setShortestPaths();
    }

    private void initSS(){
        // Below should be the creation of the priority queue
        PriorityQueue<HashMap<Integer, String>> queue = new PriorityQueue<>(this.graph.vertexSet().size(), Comparator.<HashMap<Integer,String>>naturalOrder());

        // Iterate over the vertices and generate the Pair v(d, p)
        Iterator<String> iterV = this.graph.vertexSet().iterator();
        while(iterV.hasNext()){
            //Create collection(?) of v(d, p)
        }
    }

    /**
     * use references to alter values _after_ extraction
     */
    private void relax(){}

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
