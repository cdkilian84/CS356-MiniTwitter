//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

/**
 *
 * @author Chris
 */
public interface MiniTwitVisitor {
    public void visitAdmin(AdminControl admin);
    public void visitUser(User theUser);
    public void visitGroup(Group theGroup);
}
