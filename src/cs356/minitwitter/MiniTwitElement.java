//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//Interface for Visitor pattern, which ensures that all implementing classes can be visited
//by concrete visitor classes.
public interface MiniTwitElement {
    public void accept(MiniTwitVisitor theVisitor);
}
