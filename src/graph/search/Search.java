package graph.search;

import graph.Node;
import graph.search.datatype.StorageType;
import graph.search.function.Distance;
import graph.search.function.Heuristic;
import ilist.Cons;
import ilist.IList;
import ilist.Nil;
import maybe.Just;
import maybe.Maybe;
import maybe.Nothing;

import java.util.LinkedHashSet;
import java.util.Set;

public class Search<A> {

    public Maybe<Node<A>> findNodeFrom(Node<A> startPos, Node<A> goalPos, StorageType nextToUse, Heuristic heuristic, Distance distance) {
        Set<Node<A>> visited = new LinkedHashSet<Node<A>>();

        if (startPos.contentsEquals(goalPos.getContents()))
            return new Nothing<Node<A>>();

        startPos.setHeuristic(heuristic.apply(startPos, goalPos)); // Apply heuristic to initial position
        startPos.setCost(0); // First position will always have a cost of zero

        nextToUse.add(startPos); // Add the starting node of the graph to the queue

        while (!nextToUse.isEmpty()) {
            Node<A> current = nextToUse.getHead();

            if (!visited.contains(current)) {
                if (current.contentsEquals(goalPos.getContents()))
                    return new Just<Node<A>>(current); // Return node matching the predicate
                visited.add(current);
                for (Node<A> successor : current.getSuccessors()) {
                    if (!nextToUse.contains(successor)) {
                        successor.setCost(current.getCost() + distance.apply(current, successor)); // Calculate and set the cost of traveling from current to successor
                        successor.setHeuristic(heuristic.apply(successor, goalPos)); // Set the heuristic value to the successor for traveling to the goal
                        nextToUse.add(successor); // Add successors to the current node to the queue to be checked
                    }
                }
            }
        }
        return new Nothing<Node<A>>(); // No node found matching the predicate so return nothing
    }

    public Maybe<IList<Node<A>>> findPathFrom(Node<A> startPos, Node<A> goalPos, StorageType nextToUse, Heuristic heuristic, Distance distance) {
        Set<Node<A>> visited = new LinkedHashSet<Node<A>>();
        IList<Node<A>> path = new Nil<Node<A>>();

        if (startPos.contentsEquals(goalPos.getContents()))
            return new Nothing<IList<Node<A>>>();

        startPos.setHeuristic(heuristic.apply(startPos, goalPos)); // Apply heuristic to initial position
        startPos.setCost(0); // First position will always have a cost of zero

        nextToUse.add(startPos); // Add the starting node of the graph to the queue

        while (!nextToUse.isEmpty()) { // Run until there's no other nodes to check
            Node<A> current = nextToUse.getHead();

            if (!visited.contains(current)) {
                path = new Cons<Node<A>>(current, path); // Add the current node to the path

                if (current.contentsEquals(goalPos.getContents())) {
                    return new Just<IList<Node<A>>>(path.reverse()); // Return node matching the goal
                }

                visited.add(current); // Add node to visited so that it's not checked again

                for (Node<A> successor : current.getSuccessors()) {
                    if (!nextToUse.contains(successor)) {
                        successor.setCost(current.getCost() + distance.apply(current, successor)); // Calculate and set the cost of traveling from current to successor
                        successor.setHeuristic(heuristic.apply(successor, goalPos)); // Set the heuristic value to the successor for traveling to the goal
                        nextToUse.add(successor); // Add successors to the current node to the queue to be checked
                    }
                }
            }
        }
        return new Nothing<IList<Node<A>>>(); // No node found matching the predicate so return nothing
    }
}
