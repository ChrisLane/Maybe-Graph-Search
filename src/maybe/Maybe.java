package maybe;

/**
 * Interface for the Maybe type using the "composite pattern".
 * We include high-order methods.
 * We will use A,B,C for type variables.
 */

public interface Maybe<A> {
    boolean isNothing();

    int size();

    boolean has(A a);

    // Higher-order methods:
    Maybe<A> filter(Predicate<A> p);

    <B> Maybe<B> map(Function<A, B> f);

    <B> B fold(Function<A, B> f, B b);

    boolean all(Predicate<A> p);

    boolean some(Predicate<A> p);

    void forEach(Action<A> a);

    // Unsafe operation, which should not be used (or even offered in this interface).
    A fromMaybe();
    // A method cases is not needed, because in this case it is the same as fold.
} 

/*

 We have two implementations of the Maybe interface:

    (1) The class Nothing.
    (2) The class Just.

 There is only one object of type Nothing.

 Look at SampleUsage.java.

 */
