//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

//Pattern implemented: Composite, Visitor, Observer
//User class is a subclass of the MiniTwitComponent abstract class. Implements appropriate versions of the abstract methods
//(though it falls back on the default for "addComponent"). This class also implements both the MiniTwitSubject and MiniTwitObserver
//interfaces (as well as MiniTwitElement via the parent class) as part of its implementation of the Observer pattern. 
//Because a User is both an observer AND a subject, this class must implement both interfaces in order to accomplish its goal of following 
//other users, sending tweets to them, and recieving others tweets as well. The class stores a list of observers which are subscribed to them 
//(Subject behavior) as well as implementing the methods to notify those observers. It also stores a list of those other Users it is following 
//so as to ensure that a User doesn't attempt to subscribe to another User more than once. The class can accept visitors so that information can be learned
//about the Users. And finally, the class includes two "DefaultListModel" objects which contain up-to-date information about the tweets held by the
//instance object as well as the other Users being followed. These are provided for ease of information access by Swing components.
//Note that the creation time attribute is part of the parent abstract class and is set by calling super() in the constructor
public class User extends MiniTwitComponent implements MiniTwitSubject, MiniTwitObserver {

    private String myID;
    private List<MiniTwitObserver> myFollowers; //list of observers following this Subject
    private List<MiniTwitComponent> following; //list of Users being followed
    private List<String> tweets; 
    private DefaultListModel myTweetListModel; //allows easy access by a display element to view the list of tweets
    private DefaultListModel myFollowingListModel; //allows easy access by a display element to view the list of users being followed
    private long lastUpdateTime; //holds the last time (in milliseconds) this user was updated (posted or received a tweet)
    
    //Constructor
    public User(String myID) {
        super(); //call abstract constructor which sets creation time
        this.myID = myID;
        myFollowers = new ArrayList<>();
        following = new ArrayList<>();
        tweets = new ArrayList<>();
        myTweetListModel = new DefaultListModel();
        myFollowingListModel = new DefaultListModel();
        lastUpdateTime = 0; //initialize updated time to 0 --> not "updated" until a tweet is posted/received
    }

    //Tell a User object to follow another User object. The passed value is the User to be followed by the User
    //on whom this method is called. Example: User1 wants to follow User2: User1.followUser(User2);
    //If another MiniTwitComponent other than a User is passed, it is simply ignored. This method updates both the list of stored
    //User objects as well as the list model for ease of Swing component access.
    @Override
    public void followUser(MiniTwitComponent user) {
        if(user instanceof User){
            ((User) user).addObserver(this);
            if(!following.contains(user)){ //only add the user to "following" if they don't already exist in the list
                following.add(user);
                myFollowingListModel.addElement(user.getMyID());
            }
        }
    }
    
    //Method to be called in order to post a tweet - handles updating of tweet with user info, and calling "notifyObeservers" method.
    //This is the method which should be called by outside users of the class, as it also updates the passed string with ID information
    //and updates the held list (and model) of tweets.
    public void postTweet(String tweet){
        String theTweet = this.myID + ": " + tweet;
        tweets.add(theTweet);
        lastUpdateTime = System.currentTimeMillis(); //this user is "updated" when they post a tweet
        myTweetListModel.addElement(theTweet);
        notifyObservers(theTweet);
    }

    //Implementation of the abstract getChild method as part of the Composite nature of this class.
    //User object returns itself if it has a matching ID, and returns null otherwise. Allows for
    //traversal of the leaf objects (the Users) of the data tree.
    @Override
    public MiniTwitComponent getChild(String findID) {
        MiniTwitComponent foundComponent = null;
        if(findID.equals(myID)){
            foundComponent = this;
        }
        return foundComponent;
    }

    //Subject method:
    //addObserver method takes an observer object and adds it to the "myFollowers" list if the
    //observer in question is not already in the list. If the observer is already in the list, it is
    //ignored (no need to add multiple copies of same observer).
    @Override
    public void addObserver(MiniTwitObserver theObserver) {
        if(!myFollowers.contains(theObserver)){
            myFollowers.add(theObserver);
        }
    }

    //Subject method:
    //This method is used in the Subject role by this class to notify all objects observing it to
    //run their "update" methods. Passes the tweet String which has most recently been sent out.
    @Override
    public void notifyObservers(String theTweet) {
        for(MiniTwitObserver observer : myFollowers){
            observer.update(theTweet);
        }
    }

    //Observer method:
    //This method is used in the Observer role by the class in order to update its list of tweets (and the
    //ease-of-use ListModel). This method is called by another User object acting in the Subject role when they are
    //notifying their observers of a new tweet.
    @Override
    public void update(String theTweet) {
        tweets.add(theTweet);
        lastUpdateTime = System.currentTimeMillis(); //this user is "updated" when they receive a tweet
        myTweetListModel.addElement(theTweet);
    }
    
    //Implementation of Visitor pattern interface - accepts a visitor object and calls the appropriate method
    //while passing itself to the Visitor.
    @Override
    public void accept(MiniTwitVisitor theVisitor) {
        theVisitor.visitUser(this);
    }
    
    //Override of toString method - returns ID of this User
    @Override
    public String toString(){
        return this.myID;
    }
    
    //Getter for the list of Users being followed
    public List<MiniTwitComponent> getFollowingList(){
        return following;
    }
    
    //Getter for the list of tweets held by this User
    public List<String> getTweetsList(){
        return tweets;
    }

    //Getter for the ListModel which holds the tweets (ease-of-use for Swing components)
    public DefaultListModel getMyTweetListModel(){
        return myTweetListModel;
    }
    
    //Getter for the ListModel which holds the ID's of Users this User is following (ease-of-use for Swing components)
    public DefaultListModel getMyFollowingListModel(){
        return myFollowingListModel;
    }
    
    //getter for this User's ID
    @Override
    public String getMyID() {
        return myID;
    }
    
    //getter for the last updated time
    public long getLastUpdatedTime(){
        return lastUpdateTime;
    }

    //Implementation of "print" which allows output to command line of tree structure
    //used only for testing purposes - leaving in place (commented out of course) for ease of future testing.
//    @Override
//    public void print() {
//        System.out.println(", User: " + myID);
//    }
}
