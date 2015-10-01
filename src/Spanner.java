import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import java.util.Set;

public class Spanner{

    private ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> graph;
    private Set<String> vertices;
    private Set<DefaultWeightedEdge> edges;

    private void __construct(ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> g){
        this.graph = g;
        this.vertices = g.vertexSet();
        this.edges = g.edgeSet();
    }

    /**
     * Builds a spanner using the Greedy-Spanner algorithm bt Althöfer
     * @return spanner
     */
    private ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> greedySpanner(){

        ListenableUndirectedWeightedGraph<String, DefaultWeightedEdge> spanner = this.graph;
        return spanner;
    }

}
