package MiniTwitter;

import java.util.LinkedList;
import java.util.Queue;

public class findUserCompVisitor extends Visitor {
    /**
     * Search the tree starting at the root node until the user is found or not
     * 
     * @param root     the node to start searching at
     * @param userComp the user to search for
     * @return if the user is found return the node, else return a null value
     */
    public CompositeTree visit(CompositeTree root, UserComponent userComp) {
        if (root == null)
            return null;
        Queue<CompositeTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                CompositeTree node = queue.poll();
                assert node != null;
                if (node.getUID().equals(userComp.getUID())
                        && node.getUserComponent().getClass().equals(userComp.getClass())) {
                    return node;
                }
                for (CompositeTree item : node.getChildren()) {
                    queue.offer(item);
                }
            }
        }
        return null;
    }
}