package graph.search.datatypes;

import graph.Node;

public interface StorageType<A> {

    public Node<A> getHead();

    public void add(Node<A> element);

    public boolean isEmpty();
}
