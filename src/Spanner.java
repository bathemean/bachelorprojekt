import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.HashMap;

/**
 * Created by blackapple on 08/10/15.
 */
public class Spanner extends uwGraph {


    public Spanner(Class<DefaultWeightedEdge> base) {
        super(base);
    }
}
