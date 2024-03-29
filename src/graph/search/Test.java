package graph.search;

import graph.Coordinate;
import graph.Graph;
import graph.Node;
import graph.search.searchtype.AStar;
import graph.search.searchtype.BreadthFirst;
import graph.search.searchtype.DepthFirst;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {

        Graph<Coordinate> graph = Graph.getGraph();
        Coordinate startCoord = new Coordinate(0, 0);
        Coordinate goalCoord = new Coordinate(5, 5);
        Node<Coordinate> startPos = graph.nodeWith(startCoord);
        Node<Coordinate> goalPos = graph.nodeWith(goalCoord);

        BreadthFirst breadthFirst = new BreadthFirst();
        DepthFirst depthFirst = new DepthFirst();
        AStar aStar = new AStar();


        String searchType = JOptionPane.showInputDialog("Enter search type");

        if (searchType.equalsIgnoreCase("breadth")) {
            System.out.println(breadthFirst.findNodeFrom(startPos, goalPos));
            System.out.println(breadthFirst.findPathFrom(startPos, goalPos));
        } else if (searchType.equalsIgnoreCase("depth")) {
            System.out.println(depthFirst.findNodeFrom(startPos, goalPos));
            System.out.println(depthFirst.findPathFrom(startPos, goalPos));
        } else if (searchType.equalsIgnoreCase("astar")) {
            System.out.println(aStar.findNodeFrom(startPos, goalPos));
            System.out.println(aStar.findPathFrom(startPos, goalPos));
        }
    }
}
