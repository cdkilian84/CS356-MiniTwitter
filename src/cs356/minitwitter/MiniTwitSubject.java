//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

/**
 *
 * @author Chris
 */
public interface MiniTwitSubject {
    public void addObserver(MiniTwitObserver theObserver);
    //public void removeObserver(Observer theObserver);  //Not currently implementing "remove" - "unfollow" not current requirement
    public void notifyObservers(String theTweet);
}
