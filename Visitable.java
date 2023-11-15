package MiniTwitter;

/**
 * Visitor Pattern implemented by the User/Group statistics.
 */
public abstract class Visitable {
    public abstract CompositeTree accept(Visitor v, CompositeTree userComp);

    public abstract String accept(Visitor v);
}