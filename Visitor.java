package MiniTwitter;

/**
 * Visitor Pattern
 */
public abstract class Visitor {
    public abstract Tree visit(Tree root, userComponent userC);
}
