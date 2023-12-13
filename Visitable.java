package MiniTwitter;

/**
 * Visitor Pattern implemented by the User/Group statistics.
 */
public abstract class Visitable {
    public abstract CompositeTree accept(Visitor v, CompositeTree userComp);

    public abstract boolean accept(ValidatedVisitor v);

    public abstract String accept(Visitor v);
}