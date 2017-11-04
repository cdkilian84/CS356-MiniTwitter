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
public class User extends MiniTwitComponent {

    private String myID;

    public User(String myID) {
        this.myID = myID;
    }

    @Override
    public void followUser(MiniTwitComponent user) {
        //super.followUser(user); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMyID() {
        return myID;
    }

    
    //User object returns itself if it has a matching ID, and returns null otherwise
    @Override
    public MiniTwitComponent getChild(String findID) {
        MiniTwitComponent foundComponent = null;
        if(findID.equals(this.myID)){
            foundComponent = this;
        }
        return foundComponent;
    }

    @Override
    public void print() {
        System.out.println("User: " + this.myID);
    }

}
