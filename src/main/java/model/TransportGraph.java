package model;

import java.util.*;

public class TransportGraph {
    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        // TODO
        if (vertex != null) {
            stationList.add(vertex);
            stationIndices.put(vertex.toString(), stationIndices.size());
        }
    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The graph is bidirected, so edge(to, from) should also be added.
     *
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        // TODO
        adjacencyLists[from].add(to);
        adjacencyLists[to].add(from);
        numberOfConnections++;
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connection to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // TODO
        addEdge(getIndexOfStationByName(connection.getFrom().toString()),
                getIndexOfStationByName(connection.getTo().toString()));
        connections[getIndexOfStationByName(connection.getFrom().toString())][getIndexOfStationByName(connection.getTo().toString())] = connection;
        connections[getIndexOfStationByName(connection.getTo().toString())][getIndexOfStationByName(connection.getFrom().toString())] = connection;
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public List<Integer>[] getAdjacencyLists() {
        return adjacencyLists;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    public Connection[][] getConnections() {
        return connections;
    }

    public void setConnections(Connection[][] connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private List<Double> weightList;
        private List<Location> locationList;
        private Set<Connection> connectionSet;


        public Builder() {
            stationSet = new HashSet<>();
            lineList = new ArrayList<>();
            weightList = new ArrayList<>();
            locationList = new ArrayList<>();
            connectionSet = new HashSet<>();
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            // TODO
            Line newLine = new Line(lineDefinition[1], lineDefinition[0]);
            int i = 0;
            for (String s : lineDefinition) {
                if (i > 1) {
                    newLine.addStation(new Station(s));
                }
                i++;
            }
            lineList.add(newLine);
            return this;
        }

        public Builder addLocations(int coordinates[][]) {
            for (int i = 0; i < coordinates.length; i++) {
                this.locationList.add(new Location(coordinates[i][0], coordinates[i][1]));
            }
            return this;
        }

        public Builder addWeight(Double[] connectionWeights) {
            weightList.addAll(Arrays.asList(connectionWeights));
            return this;
        }

        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         *
         * @return
         */
        public Builder buildStationSet() {
            // TODO
            this.lineList.forEach(line -> {
                line.getStationsOnLine().forEach(station -> {
                    if (!stationSet.contains(station)) {
                        stationSet.add(station);
                    }
                });
            });
            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         *
         * @return
         */
        public Builder addLinesToStations() {
            // TODO
            this.stationSet.forEach(station -> {
                station.getLines().forEach(line -> {
                    if (!lineList.contains(line)) {
                        lineList.add(line);
                    }
                });
            });
            return this;
        }

        public Builder buildLocations() {
            int i = 0;
            for (Station station : this.stationSet) {
                if (station.getLocation() == null) {
                    station.setLocation(locationList.get(i));
                }
                i++;
            }
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return
         */
        public Builder buildConnections() {
            // TODO
            int i = 0;
            for (Line line : lineList) {
                Station from = null;
                Station to;
                for (Station station : line.getStationsOnLine()) {
                    if (from == null) {
                        from = station;
                    } else {
                        to = station;
                        Connection newConnection;
                        if (weightList.isEmpty()) {
                            newConnection = new Connection(from, to);
                        } else {
                            newConnection = new Connection(from, to, weightList.get(i), line);
                        }
                        newConnection.setLine(line);
                        connectionSet.add(newConnection);
                        from = to;
                        i++;
                    }
                }
            }
            return this;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are added as vertices to the graph.
         * All connections of the connectionSet are added as edges to the graph.
         *
         * @return
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            // TODO
            this.stationSet.forEach(graph::addVertex);
            this.connectionSet.forEach(graph::addEdge);
            return graph;
        }

    }
}
