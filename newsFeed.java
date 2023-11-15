package MiniTwitter;

import java.util.ArrayList;

public class newsFeed implements Observer, Observable {

    private ArrayList<String> messages;
    private ArrayList<Observer> observerList = new ArrayList<>();
    private long lastUpdated;

    public newsFeed() {
        this.messages = new ArrayList<String>();
    }

    // Add messages to newsfeed
    @Override
    public void update(String tweet) {
        messages.add(tweet);
        notifyFollowers("");
        lastUpdated = System.currentTimeMillis();
    }

    // Return the last update time of newsFeed
    public long getLastUpdateTime() {
        return lastUpdated;
    }

    // Return the messages in user's newsfeed
    public ArrayList<String> getMessages() {
        return messages;
    }

    /**
     * Count the number of messages in the user's newsfeed
     * 
     * @return The count of all messages
     */
    public int countMessages() {
        return messages.size();
    }

    /**
     * Count the number of positive messages in the user's newsfeed
     * 
     * @return The count of positive messages
     */
    public int countPositiveMessages() {
        int posMsg = 0;
        String[] positiveWords = { "good", "great", "nice", "happy" };
        for (int i = 0; i < messages.size(); i++) {
            for (int k = 0; k < positiveWords.length; k++) {
                if (this.messages.get(i).contains(positiveWords[k])) {
                    posMsg++;
                }
            }
        }
        return posMsg;
    }

    @Override
    public void attach(Observer o) {
        observerList.add(o);
    }

    @Override
    public void detach(Observer o) {
        observerList.remove(o);

    }

    public void notifyFollowers(String tweet) {
        for (Observer follower : observerList) {
            follower.update(tweet);
        }
    }
}