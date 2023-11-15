package MiniTwitter;

/**
 * Visitor Pattern
 */
public abstract class Visitor {
    public abstract CompositeTree visit(CompositeTree root, UserComponent userC);
}
