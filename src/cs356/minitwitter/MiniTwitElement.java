//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

/**
 *
 * @author Chris
 */
public interface MiniTwitElement {
    public void accept(MiniTwitVisitor theVisitor);
}
