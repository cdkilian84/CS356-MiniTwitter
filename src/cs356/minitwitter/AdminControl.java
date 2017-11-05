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
public class AdminControl {

    private static AdminControl theAdmin;
    private MiniTwitComponent root; //the root object of the MiniTwitComponents, which will be a Group named "Root" - contains all other subgroups and users

    private AdminControl() {
        root = new Group("Root");
    }
    

    public static AdminControl getInstance() {
        if (theAdmin == null) {
            synchronized (AdminControl.class) {
                if (theAdmin == null) {
                    theAdmin = new AdminControl();
                }
            }
        }
        return theAdmin;
    }
    
    
    public MiniTwitComponent getRoot(){
        return root;
    }
    
    public MiniTwitComponent getComponent(String theID){
        MiniTwitComponent foundComponent = null;
        if(theID != null){
            foundComponent = this.root.getChild(theID);
        }
        return foundComponent;
    }
    
    public boolean checkForUniqueID(String theID){
        return ((Group)this.root).checkForUniqueID(theID);
    }

}
