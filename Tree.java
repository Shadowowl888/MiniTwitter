package MiniTwitter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {

    private String uid;
    private List<Tree> children = new LinkedList<>();
    private userComponent userC;

    public Tree(String userid, userComponent component) {
        uid = userid;
        userC = component;
    }

    // Return user id
    public String getUID() {
        return this.uid;
    }

    // Return children of node
    public List<Tree> getChildren() {
        return this.children;
    }

    /**
     * Count the total number of messages sent from all users in index 0 and
     * the count of positive messages in index 1
     * 
     * @param root node to start traversal
     * @return the total number of messages from all users starting from root
     */
    public int[] countMsg(Tree root) {
        int msgCount = 0;
        int posMsg = 0;
        int[] c = new int[2];
        Queue<Tree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Tree node = queue.poll();
                assert node != null;
                if (node.userC instanceof User) {
                    User user = (User) node.userC;
                    msgCount += user.getNewsFeed().countMessages();
                    posMsg += user.getNewsFeed().countPositiveMessages();
                }
                for (Tree item : node.children) {
                    queue.offer(item);
                }
            }
        }
        c[0] = msgCount;
        c[1] = posMsg;
        return c;
    }

    // Return the user component associated with the node
    public userComponent getUserComponent() {
        return userC;
    }

    // Accept visitors
    public Tree accept(Visitor visitor, userComponent userComp) {
        return visitor.visit(this, userComp);
    }
}