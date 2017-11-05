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
    private List<MiniTwitComponent> componentList;

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
    
    //TEMP MAYBE?
    public List<MiniTwitComponent> getMyList(){
        return componentList;
    }

    //convenience method for the "Group" class which uses getChild to check if the searched for ID
    //is unique or not - if getChild returns a non-null value, then the ID string is not unique (is already in use)
    //so return false. Else, return true.
    public boolean checkForUniqueID(String theID) {
        boolean checkFlag = true;
        
        if(this.getChild(theID) != null){
            checkFlag = false;
        }

        return checkFlag;
    }
    
    //Group object returns itself if its ID matches the searched for ID, or iterates through all of its children to find the
    //searched for object otherwise. If none of the children match the searched ID either, return null.
    @Override
    public MiniTwitComponent getChild(String findID) {
        MiniTwitComponent foundComponent = null;
        if(findID.equals(this.myID)){
            foundComponent = this;
        }else{
            for (MiniTwitComponent component : componentList) {
                if (component.getChild(findID) != null) {
                    foundComponent = component.getChild(findID);
                    break;
                }
            }
        }
        
        return foundComponent;
    }

    @Override
    public void print() {
        if(componentList.isEmpty()){
            System.out.println(", Group: " + this.myID);
        }else{
            for (MiniTwitComponent component : componentList) {
                System.out.print("Group: " + this.myID);
                component.print();
            }
        }
        
        //System.out.println("---------");
    }

}
