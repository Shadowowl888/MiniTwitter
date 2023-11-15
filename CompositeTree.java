package MiniTwitter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CompositeTree {

    private String uid;
    private List<CompositeTree> children = new LinkedList<>();
    private UserComponent userC;

    public CompositeTree(String userid, UserComponent component) {
        uid = userid;
        userC = component;
    }

    // Return user id
    public String getUID() {
        return this.uid;
    }

    // Return children of node
    public List<CompositeTree> getChildren() {
        return this.children;
    }

    /**
     * Count the total number of messages sent from all users in index 0 and
     * the count of positive messages in index 1
     * 
     * @param root node to start traversal
     * @return the total number of messages from all users starting from root
     */
    public int[] countMsg(CompositeTree root) {
        int msgCount = 0;
        int posMsg = 0;
        int[] c = new int[2];
        Queue<CompositeTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                CompositeTree node = queue.poll();
                assert node != null;
                if (node.userC instanceof User) {
                    User user = (User) node.userC;
                    msgCount += user.getNewsFeed().countMessages();
                    posMsg += user.getNewsFeed().countPositiveMessages();
                }
                for (CompositeTree item : node.children) {
                    queue.offer(item);
                }
            }
        }
        c[0] = msgCount;
        c[1] = posMsg;
        return c;
    }

    // Return the user component associated with the node
    public UserComponent getUserComponent() {
        return userC;
    }

    // Accept visitors
    public CompositeTree accept(Visitor visitor, UserComponent userComp) {
        return visitor.visit(this, userComp);
    }
}