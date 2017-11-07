//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Observer
//Interface which works to implement the Observer pattern by making sure that any class implementing this
//interface will include methods needed by a subject. These include the ability to add an observer which wishes to subscribe to
//the subject, and a method to notify those observers when an update needs to be sent out.
//Currently, removing an observer is not a supported function, but it's left here (commented out) for potential future implementation.
public interface MiniTwitSubject {
    public void addObserver(MiniTwitObserver theObserver);
    //public void removeObserver(Observer theObserver);  //Not currently implementing "remove" - "unfollow" not current requirement
    public void notifyObservers(String theTweet);
}
