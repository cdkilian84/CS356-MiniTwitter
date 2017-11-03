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

    public User() {
    }

    @Override
    public void followUser(MiniTwitComponent user) {
        //super.followUser(user); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMyID() {
        return myID;
    }

    //check a user for unique ID by checking if the passed string is equal to myID
    //Return true if the ID is unique, and false if the ID is not unique (or if the passed string is null)
    @Override
    public boolean checkForUniqueID(String theID) {
        boolean checkFlag = true;
        if ((theID == null) || theID.equals(myID)) {
            checkFlag = false;
        }
        return checkFlag;
    }

}
