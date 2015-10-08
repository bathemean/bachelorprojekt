
import java.util.*;

public class ThorupZwickSpanner {



    public ThorupZwickSpanner(uwGraph g, int k) {

        ArrayList<String> vertices = vertexSetToArray(g.vertexSet());

        // Assign vertices into parititions.
        HashMap<Integer, ArrayList<String>> p = nonRandPartition(vertices, k);

        System.out.println(p.toString());

        // Compute distance between A_k and every vertex v.
        //distances(partition p, vertex v);

        // Grow shortest path tree from some w in A_i - A_i+1.
        //tree(w, vertices);

        // UNITE THE TREES!
        //uniion(trees);




    }

    private ArrayList<String> vertexSetToArray(Set<String> set) {

        ArrayList<String> array = new ArrayList<>();

        for(String e : set) {
            array.add(e);
        }

        return array;

    }
    
    private HashMap<Integer, ArrayList<String>> nonRandPartition(ArrayList<String> vertices, int k) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        for(int i = 1; i < k; i++) {

            ArrayList<String> prev = partitions.get(i - 1);
            ArrayList<String> prevPart = new ArrayList<>();

            for(int j = 1; j < prev.size(); j++) {
                prevPart.add(prev.get(j));
            }

            partitions.put(i, prevPart);
        }

        partitions.put(k, null);

        return partitions;

    }

    private HashMap<Integer, ArrayList<String>> partition(ArrayList<String> vertices, int k) {

        HashMap<Integer, ArrayList<String>> partitions = new HashMap<>();

        partitions.put(0, vertices); // Put all vertices into A_0.

        int n = vertices.size();
        int margin = (int) Math.pow(n, (-1.0)/k);

        Random gen = new Random();

        for(int i = 1; i < k; i++) {

            ArrayList<String> subset = new ArrayList<>();

            for(String v : partitions.get(i-1)) {
                int next = gen.nextInt(Integer.MAX_VALUE);
                System.out.println("Margin: " + margin + ", next: " + next);
                if(margin > next) {

                    subset.add(v);
                }

            }

            partitions.put(i, subset);

        }

        partitions.put(k, null);

        return partitions;

    }

}
