package graph.search.datatype;

import graph.Node;

import java.util.Stack;

public class SearchStack<A> implements StorageType {
    final Stack<Node<A>> stack;

    public SearchStack() {
        stack = new Stack<Node<A>>();
    }

    @Override
    public Node getHead() {
        return stack.pop();
    }

    @Override
    public void add(Node element) {
        stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public boolean contains(Node element) {
        return stack.contains(element);
    }
}
