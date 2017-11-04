/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
