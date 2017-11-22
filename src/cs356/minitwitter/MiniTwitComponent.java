//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

//Pattern implemented: Composite, Visitor
//This is the abstract class which forms the basis for concrete MiniTwitComponents, in this case the User and Group classes.
//Implements default versions of methods which may not be implemented by all subclasses (ex: User doesn't need to implement "addComponent").
//This class also implements the MiniTwitElement interface, which itself is the implementation for the Visitor pattern. This ensures all of the
//subclasses will also implement the MiniTwitElement interface (to accept visitors). 
public abstract class MiniTwitComponent implements MiniTwitElement {

    private long creationTime;
    
    public MiniTwitComponent(){
        creationTime = System.currentTimeMillis();
    }
    //default implementations of these methods just throw exceptions - concrete subclasses should override any methods they'll actually use
    public void addComponent(MiniTwitComponent component) {
        throw new UnsupportedOperationException("Operation not supported by this component.");
    }

    public void followUser(MiniTwitComponent user) {
        throw new UnsupportedOperationException("Operation not supported by this component.");
    }
    
    public long getCreationTime(){
        return creationTime;
    }

    //Methods without default implementations must be implemented by the subclasses - ensures each subclass handles
    //these methods appropriately for their specifc needs.
    public abstract MiniTwitComponent getChild(String findID);

    public abstract String getMyID();

    @Override
    public abstract String toString();

    //Method which is only used for testing purposes - leaving here for ease of testing
    //Designed to output the tree structure of a Group - if called on the root will output entire tree to command line
    //public abstract void print();
}
