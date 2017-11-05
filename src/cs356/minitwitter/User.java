/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class User extends MiniTwitComponent implements MiniTwitSubject, MiniTwitObserver {

    private String myID;
    private List<MiniTwitObserver> myFollowers;
    private List<MiniTwitComponent> following;
    private List<String> tweets;

    public User(String myID) {
        this.myID = myID;
        myFollowers = new ArrayList<>();
        following = new ArrayList<>();
        tweets = new ArrayList<>();
    }

    //Tell a User object to follow another User object. The passed value is the User to be followed by the User
    //on whom this method is called. Example: User1 wants to follow User2: User1.followUser(User2);
    //If another MiniTwitComponent other than a User is passed, it is simply ignored.
    @Override
    public void followUser(MiniTwitComponent user) {
        if(user instanceof User){
            ((User) user).addObserver(this);
            if(!following.contains(user)){ //only add the user to "following" if they don't already exist in the list
                following.add(user);
            }
        }
    }

    @Override
    public String getMyID() {
        return myID;
    }

    
    //User object returns itself if it has a matching ID, and returns null otherwise
    @Override
    public MiniTwitComponent getChild(String findID) {
        MiniTwitComponent foundComponent = null;
        if(findID.equals(myID)){
            foundComponent = this;
        }
        return foundComponent;
    }

    @Override
    public void print() {
        System.out.println(", User: " + myID);
    }

    //SUBJECT METHOD
    //addObserver method takes an observer object and adds it to the "myFollowers" list if the
    //observer in question is not already in the list. If the observer is already in the list, it is
    //ignored (no need to add multiple copies of same observer).
    @Override
    public void addObserver(MiniTwitObserver theObserver) {
        if(!myFollowers.contains(theObserver)){
            myFollowers.add(theObserver);
        }
    }

    //SUBJECT METHOD
    @Override
    public void notifyObservers(String theTweet) {
        for(MiniTwitObserver observer : myFollowers){
            observer.update(theTweet);
        }
    }

    //OBSERVER METHOD
    @Override
    public void update(String theTweet) {
        tweets.add(theTweet);
    }

}
