package graphalgorithms;

import model.TransportGraph;

import java.util.Iterator;
import java.util.List;

public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
//        marked = new boolean[1000];
        dfp(startIndex);
        pathTo(endIndex);
    }

    private void dfp(int v) {
        // Mark the current node as visited and print it
        marked[v] = true;
        nodesVisited.add(graph.getStation(v));

        // Recur for all the vertices adjacent to this vertex
        List<Integer>[] adj = graph.getAdjacencyLists();
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!marked[n]) {
                edgeTo[n] = v;
                dfp(n);
            }
        }
    }

}
