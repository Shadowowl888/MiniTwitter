package MiniTwitter;

import java.util.ArrayList;

/**
 * Newsfeed is impleented using the observer pattern from the user to keep track
 * of the tweets posted by followers.
 */
public class NewsFeed implements Observer, Observable {

    private ArrayList<String> messages;
    private ArrayList<Observer> observerList = new ArrayList<>();

    public NewsFeed() {
        this.messages = new ArrayList<String>();
    }

    // Add messages to newsfeed
    @Override
    public void update(String tweet) {
        messages.add(tweet);
        notifyFollowers("");
    }

    // Return the messages in user's newsfeed
    public ArrayList<String> getMessages() {
        return messages;
    }

    // Count the number of messages in user's news feed
    public int countMessages() {
        return messages.size();
    }

    // Count the number of positive messages
    public int countPositiveMessages() {
        int positiveCount = 0;
        String[] positiveWords = { "lit", "fun", "good", "great", "nice", "happy" };
        for (int i = 0; i < messages.size(); i++) {
            for (int k = 0; k < positiveWords.length; k++) {
                if (this.messages.get(i).contains(positiveWords[k])) {
                    positiveCount++;
                }
            }
        }
        return positiveCount;
    }

    @Override
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);

    }

    // Update followers feed when user posts a tweet
    public void notifyFollowers(String tweet) {
        for (Observer follower : observerList) {
            follower.update(tweet);
        }
    }
}