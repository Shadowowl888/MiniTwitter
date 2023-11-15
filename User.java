package MiniTwitter;

import java.util.ArrayList;
import java.util.List;

public class User extends userComponent implements Observable {

    private String UID;
    private ArrayList<Observer> observerList = new ArrayList();
    private ArrayList<User> following = new ArrayList();
    private newsFeed newsFeed = new newsFeed();
    private ArrayList<String> messages = new ArrayList<String>();
    private ArrayList<User> followerList = new ArrayList();
    private long lastUpdateTime;

    public User(String username) {
        this.UID = username;
        this.attach(newsFeed);
        this.setCreationTime(System.currentTimeMillis());
        this.lastUpdateTime = this.getCreationTime();
    }

    // Returns the username
    public String getUID() {
        return UID;
    }

    // Returns the list of followers
    public ArrayList getObserverList() {
        return observerList;
    }

    // Returns the list of users that are being followed
    public ArrayList<User> getFollowing() {
        return following;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long time) {
        this.lastUpdateTime = time;
    }

    // Get list of names that user is following
    public String[] getFollowingNames() {
        String[] s = new String[500]; // Max Capacity 500
        for (int i = 0; i < following.size(); i++) {
            s[i] = this.following.get(i).getUID();
        }
        return s;
    }

    public String[] getFollowerList() {
        String[] s = new String[500]; // Max Capacity 500
        for (int i = 0; i < followerList.size(); i++) {
            s[i] = this.followerList.get(i).getUID();
        }
        return s;
    }

    // Return the user's newsfeed
    public newsFeed getNewsFeed() {
        return newsFeed;
    }

    // Add tweet to user and notify observers
    public void tweet(String tweet) {
        this.messages.add(tweet);
        notifyFollowers(tweet);
        setLastUpdateTime(this.newsFeed.getLastUpdateTime());
        updateTime();
    }

    // Returns the tweets that the user has made
    public ArrayList<String> getTweets() {
        return this.messages;
    }

    // Add other userNames to following list
    public void follow(User user) {
        this.following.add(user);
        user.followerList.add(this);
    }

    // Add observers
    public void attach(Observer o) {
        observerList.add(o);
    }

    public void detach(Observer o) {
        observerList.remove(o);
    }

    // Update all followers time
    public void updateTime() {
        for (int i = 0; i < followerList.size(); i++) {
            long lastUpTime = followerList.get(i).getNewsFeed().getLastUpdateTime();
            followerList.get(i).setLastUpdateTime(lastUpTime);
        }
    }

    // Update followers when tweet posted
    public void notifyFollowers(String tweet) {
        for (Observer follower : observerList) {
            follower.update((String) "From " + this.UID + ":    " + tweet);
        }
    }
}