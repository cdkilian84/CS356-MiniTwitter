//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//This interface defines the methods that a concrete visitor class must be capable of handling
//since any MiniTwitVisitor can (and will) be called to visit the referenced object.
public interface MiniTwitVisitor {
    public void visitAdmin(AdminControl admin);
    public void visitUser(User theUser);
    public void visitGroup(Group theGroup);
}
