//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

/**
 *
 * @author Chris
 */
public abstract class MiniTwitComponent implements MiniTwitElement {
    
    //default implementations of these methods just throw exceptions - concrete subclasses should override any
    //methods they'll actually use
    public void addUser(MiniTwitComponent user){
        throw new UnsupportedOperationException("Operation not supported by this component.");
    }
    
    public void followUser(MiniTwitComponent user){
        throw new UnsupportedOperationException("Operation not supported by this component.");
    }
    
    
    public abstract MiniTwitComponent getChild(String findID);
    
    public abstract String getMyID();
    
    //POSSIBLE TEMP FOR WORKING - CONSIDER REMOVING LATER
    public abstract void print();
    
    @Override
    public abstract String toString();
    
}
