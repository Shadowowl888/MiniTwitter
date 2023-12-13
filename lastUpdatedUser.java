package MiniTwitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import MiniTwitter.CompositeTree;
import MiniTwitter.Visitor;
import MiniTwitter.UserComponent;
import MiniTwitter.User;

// Traverse through all the users and find the most recent time
public class lastUpdatedUser extends Visitor {
    public String visit(CompositeTree root) {
        ArrayList<String> userList = new ArrayList<>();
        Queue<CompositeTree> queue = new LinkedList<>();
        queue.offer(root);
        userName mostRecentUpdate = new userName("TempUser");
        mostRecentUpdate.setLastUpdateTime(0);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Tree node = queue.poll();
                assert node != null;

                // If node is a user then compare the last updated times
                if (node.getUserComponent() instanceof userName) {
                    userName user = (userName) node.getUserComponent();
                    // If last updated time is less than the new last updated time then set the
                    // mostRecentUpdated user
                    if (mostRecentUpdate.getLastUpdateTime() < user.getLastUpdateTime()) {
                        mostRecentUpdate = user;
                    }
                }

                for (Tree item : node.getChildren()) {
                    queue.offer(item);
                }
            }
        }
        return mostRecentUpdate.getUID();
    }
}
