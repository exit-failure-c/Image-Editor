import java.util.*;

public class ShortestPathsTopological {
    private final int[] parent;
    private final int s;
    private final double[] dist;


    /**
     * Constructor
     * Finds shortest paths in G starting in s. Saves result in the instance attribute parent.
     *
     * @param G Graph G
     * @param s start node s
     */
    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        this.dist = new double[G.V()];
        this.parent = new int[G.V()];
        this.s = s;
        for (int v = 0; v < G.V(); v++)
            dist[v] = Double.POSITIVE_INFINITY;
        dist[s] = 0.0;
        TopologicalWD my_topolo = new TopologicalWD(G);
        my_topolo.dfs(s);
        while (!my_topolo.order().isEmpty()){
            int edge_to_test = my_topolo.order().pop();
            for (DirectedEdge e : G.incident(edge_to_test))
                relax(e);
        }
    }

    /**
     * Implements the relaxation of en edge.
     * For further information see subsection on relaxation in the script.
     *
     * @param e edge that will be relaxed
     */
    public void relax(DirectedEdge e) {
        // TODO
        int v = e.from();
        int w = e.to();
        if (dist[w] > dist[v] + e.weight()) {
            dist[w] = dist[v] + e.weight();
            parent[w] = e.from();
        }
    }
    /**
     * @param v node
     * @return whether the node already has a path to it
     */
    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    /**
     * @param v node
     * @return path to node from start node s
     */
    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

