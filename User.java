package MiniTwitter;

import java.util.ArrayList;
import java.util.List;

/**
 * User implements Observer Pattern to keep track of the followers following the
 * user and update their
 */
public class User extends UserComponent implements Observable {

    private String UID;
    private ArrayList<Observer> observerList = new ArrayList();
    private ArrayList<User> following = new ArrayList();
    private NewsFeed newsFeed = new NewsFeed();
    private ArrayList<String> messages = new ArrayList<String>();
    private ArrayList<User> followerList = new ArrayList();

    public User(String username) {
        this.UID = username;
        this.attach(newsFeed);
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

    // Get list of followers
    public String[] getFollowingNames() {
        String[] s = new String[500]; // Max Capacity 500
        for (int i = 0; i < following.size(); i++) {
            s[i] = this.following.get(i).getUID();
        }
        return s;
    }

    // REturn the followers list
    public String[] getFollowerList() {
        String[] s = new String[500]; // Max Capacity 500
        for (int i = 0; i < followerList.size(); i++) {
            s[i] = this.followerList.get(i).getUID();
        }
        return s;
    }

    // Return the user's newsfeed
    public NewsFeed getNewsFeed() {
        return newsFeed;
    }

    // Add tweet to users feed
    public void tweet(String tweet) {
        this.messages.add(tweet);
        notifyFollowers(tweet);
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

    // Attach and detach method to edit observers
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    // Update followers when tweet is posted
    public void notifyFollowers(String tweet) {
        for (Observer follower : observerList) {
            follower.update((String) "From " + this.UID + " - " + tweet);
        }
    }
}
