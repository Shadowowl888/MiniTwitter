package MiniTwitter;

public abstract class userComponent {
    private String UID;
    private long creationTime;

    public String getUID() {
        return UID;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long time) {
        this.creationTime = time;
    }

    public userComponent getComponent(int componentIndex) {
        throw new UnsupportedOperationException();
    }

}