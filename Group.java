package MiniTwitter;

import java.util.ArrayList;

public class Group extends userComponent {

    private String UID;
    private ArrayList<userComponent> userComponents = new ArrayList();

    public Group(String newGroupName) {
        UID = newGroupName;
        this.setCreationTime(System.currentTimeMillis());
    }

    @Override
    public String getUID() {
        return UID;
    }

    // Remove a user or group
    public void remove(userComponent removeUserComponent) {
        userComponents.remove(removeUserComponent);
    }

    // Gets one specific node
    @Override
    public userComponent getComponent(int componentIndex) {
        return userComponents.get(componentIndex);
    }

}