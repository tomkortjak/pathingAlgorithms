package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.TransportGraph;


public class DijkstraShortesPath extends AbstractPathSearch {
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraShortesPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        distTo = new double[graph.getNumberOfStations()];
        pq = new IndexMinPQ<Double>(graph.getNumberOfStations());
        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        this.distTo[startIndex] = 0.0;
        pq.insert(startIndex, 0.0);
        while (!pq.isEmpty()) {
            search();
        }
        pathTo(endIndex);
    }

    @Override
    public void search() {
        int v = pq.delMin();
        nodesVisited.add(graph.getStation(v));
        for (Integer w: graph.getAdjacentVertices(v)) {
            Connection connection = graph.getConnection(v, w);

            if (distTo[w] > distTo[v] + connection.getWeight()) {
                distTo[w] = distTo[v] + connection.getWeight();
                edgeTo[w] = v;
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }
}
