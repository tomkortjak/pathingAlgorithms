package graphalgorithms;

import model.TransportGraph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        // Mark all the vertices as not visited(By default
        // set as false)
        int currentIndex = startIndex;

        // Create a queue for BFS
        verticesInPath = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        marked[currentIndex] = true;
        verticesInPath.add(currentIndex);
        nodesVisited.add(graph.getStation(currentIndex));

        while (verticesInPath.size() != 0) {
            // Dequeue a vertex from queue and print it
            currentIndex = verticesInPath.poll();
//            System.out.print(currentIndex + " ");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            List<Integer> adj[] = graph.getAdjacencyLists();
            Iterator<Integer> i = adj[currentIndex].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!marked[n]) {
                    marked[n] = true;
                    verticesInPath.add(n);
                    nodesVisited.add(graph.getStation(n));
                    edgeTo[n] = currentIndex;
                }
            }
        }
        pathTo(endIndex);
    }
}
