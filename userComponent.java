package MiniTwitter;

/**
 * User Component to keep track of unique users.
 */
public abstract class UserComponent {
    private String UID;

    public String getUID() {
        return UID;
    }

    public UserComponent getComponent(int componentIndex) {
        throw new UnsupportedOperationException();
    }

}