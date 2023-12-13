package MiniTwitter;

import java.util.ArrayList;

public class Group extends UserComponent {

    private String UID;
    private ArrayList<UserComponent> userComponents = new ArrayList();

    public Group(String newGroupName) {
        UID = newGroupName;
        this.setCreationTime(System.currentTimeMillis());
    }

    @Override
    public String getUID() {
        return UID;
    }

    // Remove a user or group
    public void remove(UserComponent removeUserComponent) {
        userComponents.remove(removeUserComponent);
    }

    // Gets one specific node
    @Override
    public UserComponent getComponent(int componentIndex) {
        return userComponents.get(componentIndex);
    }

}