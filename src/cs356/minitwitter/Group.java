/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class Group extends MiniTwitComponent {

    private String myID;
    List<MiniTwitComponent> componentList;

    public Group(String myID) {
        this.myID = myID;
        componentList = new ArrayList();
    }

    @Override
    public void addUser(MiniTwitComponent user) {
        componentList.add(user);
    }

    @Override
    public String getMyID() {
        return myID;
    }

    @Override
    public boolean checkForUniqueID(String theID) {
        boolean checkFlag = true;
        if (theID.equals(myID)) {
            checkFlag = false;
        } else {
            for (MiniTwitComponent component : componentList) {
                if (!component.checkForUniqueID(theID)) {
                    checkFlag = false;
                    break;
                }
            }
        }

        return checkFlag;
    }

}
