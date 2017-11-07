//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Observer
//Interface which works to implement the Observer pattern by making sure that any class implementing this
//interface will include an "update" method to handle notifications.
public interface MiniTwitObserver {
    public void update(String theTweet);
}
