import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ThorupZwickSpannerTest {

    private uwGraph graph;
    private ThorupZwickSpanner spanner;

    public ThorupZwickSpannerTest() {
        GraphFactory factory = new GraphFactory();
        this.graph = factory.createBiggerStringGraph();
        this.spanner = new ThorupZwickSpanner(this.graph, 4);
    }

    public String testDistances() {
        ArrayList<Distance> distances = spanner.getDistances();

        Object[] vertices = graph.vertexSet().toArray();

        ArrayList<Integer> flags = new ArrayList<>();
        for(int i = 0; i < vertices.length; i++) {
            flags.add(0);
        }

System.out.println(vertices.length);
        // Make sure all vertices are connected.
        for(int j = 0; j < vertices.length; j++) {
            String v = vertices[j].toString();
            //System.out.println(v + " " + flags.toString());
            for(int i = 0; i < distances.size(); i++) {
                Distance d = distances.get(i);
                //System.out.println("j: " + j + " i: " + i + " - " + v + ", " + d);
                if( d.getWitness() == v || d.getTarget() == v ) {
                    flags.set(j, 1);
                    //break;
                }
            }

        }
System.out.println(flags.toString());
        if(flags.contains(0)) {
            String res = "Missing nodes: ";

            for(int i = 0; i < flags.size(); i++) {
                if(flags.get(i) == 0) {
                    res += i + " ";
                }
            }

            return res;
        } else {
            return "0";
        }


    }

}
