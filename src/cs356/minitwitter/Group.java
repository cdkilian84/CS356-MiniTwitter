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
public class Group extends MiniTwitComponent{

    private String myID;
    
    public Group(String myID) {
        this.myID = myID;
    }

    @Override
    public void addUser(MiniTwitComponent user) {
        //super.addUser(user); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMyID() {
        return myID;
    }
    
}
