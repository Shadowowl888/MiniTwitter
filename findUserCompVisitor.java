package MiniTwitter;

import java.util.LinkedList;
import java.util.Queue;

public class findUserCompVisitor extends Visitor {
    /**
     * Search the tree starting from the root node until a specified user component
     * is found
     * 
     * @param root  node to start traversal
     * @param userC the user component we are looking for
     * @return if user is found return the node, if not then return null
     */
    public Tree visit(Tree root, userComponent userC) {
        if (root == null)
            return null;
        Queue<Tree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Tree node = queue.poll();
                assert node != null;
                if (node.getUID().equals(userC.getUID())
                        && node.getUserComponent().getClass().equals(userC.getClass())) {
                    return node;
                }
                for (Tree item : node.getChildren()) {
                    queue.offer(item);
                }
            }
        }
        return null;
    }
}