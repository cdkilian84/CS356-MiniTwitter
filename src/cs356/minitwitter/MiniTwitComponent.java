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
public abstract class MiniTwitComponent {
    
    //default implementations of these methods just throw exceptions - concrete subclasses should override any
    //methods they'll actually use
    public void addUser(MiniTwitComponent user){
        throw new UnsupportedOperationException();
    }
    
    public void followUser(MiniTwitComponent user){
        throw new UnsupportedOperationException();
    }
    
    public abstract String getMyID();
    
}
