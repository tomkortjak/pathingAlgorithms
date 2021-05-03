package controller;

import graphalgorithms.A_Star;
import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortesPath;
import model.Station;
import model.TransportGraph;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        String[] redLine2 = {"metro", "red", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        Double[] line1Weights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        String[] blueLine2 = {"metro", "blue", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        Double[] line2Weights = {6.0, 5.3, 5.1, 3.3};
        String[] purpleLine2 = {"metro", "purple", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        Double[] line3Weights = {6.2, 5.2, 3.8, 3.6};
        String[] greenLine2 = {"metro", "green", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        Double[] line4Weights = {5.0, 3.7, 6.9, 3.9, 3.4};
        String[] yellowLine2 = {"bus", "yellow", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        Double[] line5Weights = {26.0, 19.0, 37.0, 25.0, 22.0, 28.0};

        int[][] locations = {
                {0, 11},
                {14, 1},
                {12, 3},
                {10, 5},
                {6, 12},
                {6, 9},
                {9, 0},
                {4, 6},
                {11, 11},
                {3, 10},
                {2, 3},
                {10, 9},
                {7, 6},
                {5, 14},
                {12, 13},
                {8, 8},
                {9, 3},
        };

        TransportGraph transportGraphA = new TransportGraph.Builder()
                .addLine(redLine)
                .addLine(blueLine)
                .addLine(greenLine)
                .addLine(yellowLine)
                .addLinesToStations()
                .buildConnections()
                .buildStationSet().build();

        // TODO Use the builder to build the graph from the String array.
        TransportGraph transportGraphB = new TransportGraph.Builder()
                .addLine(redLine2)
                .addLine(blueLine2)
                .addLine(purpleLine2)
                .addLine(greenLine2)
                .addLine(yellowLine2)
                .addLinesToStations()
                .addWeight(line1Weights)
                .addWeight(line2Weights)
                .addWeight(line3Weights)
                .addWeight(line4Weights)
                .addWeight(line5Weights)
                .addLocations(locations)
                .buildConnections()
                .buildStationSet()
                .buildLocations().build();

        System.out.println(transportGraphB);

        System.out.println("---------------------------------------");
        DijkstraShortesPath dijkstraSearch = new DijkstraShortesPath(transportGraphB, "Marken", "Coltrane Cirkel");
        System.out.println(dijkstraSearch);
        dijkstraSearch = new DijkstraShortesPath(transportGraphB, "Coltrane Cirkel", "Marken");
        System.out.println(dijkstraSearch);
        System.out.println("---------------------------------------");
        A_Star aStarSearch = new A_Star(transportGraphB, "Marken", "Coltrane Cirkel");
        System.out.println(aStarSearch);
        aStarSearch = new A_Star(transportGraphB, "Coltrane Cirkel", "Marken");
        System.out.println(aStarSearch);


        System.out.println();
        System.out.println("De korste routes:");
        for (Station s1 : transportGraphA.getStationList()) {
            for (Station s2 : transportGraphA.getStationList()) {
                if (s1 == s2) {
                    continue;
                }
                BreadthFirstPath bfOption = new BreadthFirstPath(transportGraphA, s1.getStationName(), s2.getStationName());
                DepthFirstPath dfOption = new DepthFirstPath(transportGraphA, s1.getStationName(), s2.getStationName());

                bfOption.search();
                dfOption.search();

                if (bfOption.getNodesInPath().size() < dfOption.getNodesInPath().size() ||
                        bfOption.getNodesInPath().size() == dfOption.getNodesInPath().size()) {
                    System.out.println(bfOption);
                } else {
                    System.out.println(dfOption);
                }

            }
        }


        System.out.println();
        System.out.println("Dijkstra:");
        DijkstraShortesPath DSPtest2 = new DijkstraShortesPath(transportGraphB, "Haven", "Oostvaarders");
        DSPtest2.printNodesInVisitedOrder();

        System.out.println();
        System.out.println("A*:");
        A_Star AsTest = new A_Star(transportGraphB, "Haven", "Violetplantsoen");
        AsTest.printNodesInVisitedOrder();

        System.out.println();
        System.out.println("De korste routes van dijkstra en a star:");
        for (Station s1 : transportGraphB.getStationList()) {
            for (Station s2 : transportGraphB.getStationList()) {
                if (s1 == s2) {
                    continue;
                }

                DijkstraShortesPath dijkstraOption = new DijkstraShortesPath(transportGraphB, s1.getStationName(), s2.getStationName());
                A_Star starOption = new A_Star(transportGraphB, s1.getStationName(), s2.getStationName());

                System.out.println(dijkstraOption);
                dijkstraOption.printNodesInVisitedOrder();
                System.out.println(starOption);
                starOption.printNodesInVisitedOrder();
            }
        }
    }
}
