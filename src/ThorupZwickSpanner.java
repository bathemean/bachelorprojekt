/**
 * Created by blackapple on 08/10/15.
 */
public class ThorupZwickSpanner {

    public ThorupZwickSpanner(uwGraph stringGraph, int k) {

        // Assign vertices into parititions.
        partition(vertices, k);

        // Compute distance between A_k and every vertex v.
        distances(partition p, vertex v);

        // Grow shortest path tree from some w in A_i - A_i+1.
        tree(w, vertices);

        // UNITE THE TREES!
        uniion(trees);

        


    }

}
