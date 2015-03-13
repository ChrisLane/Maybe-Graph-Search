package graph.search.searchtype;

import graph.Coordinate;
import graph.Graph;
import graph.Node;
import graph.search.function.Distance;
import graph.search.function.Heuristic;
import graph.search.function.PriorityQueueCompare;
import ilist.Cons;
import ilist.IList;
import ilist.Nil;
import maybe.Just;
import maybe.Maybe;
import maybe.Nothing;

import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AStar<A> {

    public static void main(String args[]) {
        Graph<Coordinate> graph = Graph.getGraph();
        final Coordinate startCoord = new Coordinate(0, 0);
        final Coordinate goalCoord = new Coordinate(5, 5);
        final Node<Coordinate> startPos = graph.nodeWith(startCoord);
        final Node<Coordinate> goalPos = graph.nodeWith(goalCoord);

        AStar<Coordinate> aStar = new AStar<Coordinate>();
        System.out.println(aStar.findNodeFrom(startPos, goalPos, Heuristic.manhattan, Distance.manhattan));
        System.out.println(aStar.findPathFrom(startPos, goalPos, Heuristic.manhattan, Distance.manhattan));
    }

    public Maybe<Node<A>> findNodeFrom(Node<A> startPos, Node<A> goalPos, Heuristic heuristicFunction, Distance distanceFunction) {
        Set<Node<A>> visited = new LinkedHashSet<Node<A>>();
        Queue<Node<A>> pending = new PriorityQueue<Node<A>>(new PriorityQueueCompare<A>());

        startPos.setHeuristic(heuristicFunction.apply(startPos, goalPos));
        startPos.setCost(0);

        pending.add(startPos);

        while (!pending.isEmpty()) {
            Node<A> current = pending.poll();

            if (!visited.contains(current)) {

                if (current.contentsEquals(goalPos.getContents()))
                    return new Just<Node<A>>(current);
                visited.add(current);

                for (Node<A> successor : current.getSuccessors()) {
                    if (!pending.contains(successor)) {
                        double cost = current.getCost() + distanceFunction.apply(current, successor);
                        successor.setHeuristic(heuristicFunction.apply(successor, goalPos));
                        successor.setCost(cost);
                        pending.add(successor);
                    }
                }
            }
        }
        return new Nothing<Node<A>>();
    }

    public Maybe<IList<Node<A>>> findPathFrom(Node<A> startPos, Node<A> goalPos, Heuristic heuristicFunction, Distance distanceFunction) {
        Set<Node<A>> visited = new LinkedHashSet<Node<A>>();
        Queue<Node<A>> pending = new PriorityQueue<Node<A>>(new PriorityQueueCompare<A>());
        IList<Node<A>> path = new Nil<Node<A>>();

        startPos.setHeuristic(heuristicFunction.apply(startPos, goalPos));
        startPos.setCost(0);

        pending.add(startPos);

        while (!pending.isEmpty()) {
            Node<A> current = pending.poll();

            if (!visited.contains(current)) {
                path = new Cons<Node<A>>(current, path);

                if (current.contentsEquals(goalPos.getContents())) {
                    return new Just<IList<Node<A>>>(path.reverse());
                }

                visited.add(current);

                for (Node<A> successor : current.getSuccessors()) {
                    if (!pending.contains(successor)) {
                        double cost = current.getCost() + distanceFunction.apply(current, successor);
                        successor.setHeuristic(heuristicFunction.apply(successor, goalPos));
                        successor.setCost(cost);
                        pending.add(successor);
                    }
                }
            }
        }
        return new Nothing<IList<Node<A>>>();
    }
}
