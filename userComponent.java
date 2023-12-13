package MiniTwitter;

/**
 * User Component to keep track of unique users.
 */
public abstract class UserComponent {
    private String UID;
    private long creationTime;

    public String getUID() {
        return UID;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long time) {
        creationTime = time;
    }

    public UserComponent getComponent(int componentIndex) {
        throw new UnsupportedOperationException();
    }
}