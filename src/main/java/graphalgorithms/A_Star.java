package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;


public class A_Star extends AbstractPathSearch {
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public A_Star(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        Station endStation = graph.getStation(graph.getIndexOfStationByName(end));
        distTo = new double[graph.getNumberOfStations()];
        pq = new IndexMinPQ<>(graph.getNumberOfStations());
        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            Station currentStation = graph.getStation(v);
            currentStation.setTravelTime(endStation.getLocation().travelTime(currentStation.getLocation()));
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
        if(v == endIndex) {
            pq.forEach(integer -> pq.delete(integer));
            return;
        }
        for (Integer w: graph.getAdjacentVertices(v)) {
            Connection connection = graph.getConnection(v, w);
            double toTime = graph.getStation(w).getTravelTime();
            if (distTo[w] > distTo[v] + connection.getWeight()) {
                distTo[w] = distTo[v] + connection.getWeight();
                edgeTo[w] = v;
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w] + toTime);
                } else {
                    pq.insert(w, distTo[w] + toTime);
                }
            }
        }
    }
}
