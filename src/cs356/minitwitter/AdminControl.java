//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Singleton, Visitor
//This class handles the primary management of the tree structure on which the MiniTwit program is based.
//There is a single instance of this class which in turn holds a single instance of the "root" MiniTwit component, which is
//initialized as a "Group" named "Root". This group holds all of the users and subgroups for the MiniTwit program. The AdminControl class
//also allows for access to any part of the tree via methods such as "getComponent" (which returns any component in the tree with the matching ID value)
//or its checkForUniqueID method which checks to ensure that the passed ID is indeed unique. The class also implements the MiniTwitElement interface so that
//visitor objects can visit it and gain information on the entire data tree.
public class AdminControl implements MiniTwitElement {

    private static AdminControl theAdmin; //the single reference to this class/object
    private MiniTwitComponent root; //the root object of the MiniTwitComponents, which will be a Group named "Root" - contains all other subgroups and users

    //Private constructor, ensures that this class can only be accessed via the "getInstance" method
    private AdminControl() {
        root = new Group("Root");
    }

    //getInstance is the method through which the users of this class are able to get a reference to the single static instance
    //of this class. This method is double locked to ensure thread-safety!
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


    //returns true if ID is unique (doesn't already exist in tree), false otherwise
    public boolean checkForUniqueID(String theID) {
        return ((Group) this.root).checkForUniqueID(theID);
    }

    //Method to accept a visitor object (Visitor pattern implementation)
    @Override
    public void accept(MiniTwitVisitor theVisitor) {
        theVisitor.visitAdmin(AdminControl.getInstance());
    }

    //Method for easy insetion of an element into the tree at a specified location. If the insertion point
    //is not a Group object or the ID of the component being inserted isn't unique, the insertion is rejected.
    //Method returns a boolean, true if the component was able to be inserted, and false otherwise.
    //This method allows users of the Admin class (such as the Admin panel) to insert components without needing to
    //rely on calling methods on individual members (Users/Groups) of the tree. Centralizes operations through the AdminControl class.
    public boolean insertComponent(MiniTwitComponent toInsert, MiniTwitComponent insertionGroup) {
        boolean flag = false;
        if ((checkForUniqueID(toInsert.getMyID())) && (insertionGroup instanceof Group)) {
            insertionGroup.addComponent(toInsert);
            flag = true;
        }
        return flag;
    }
    
    //Getter for the User/Group tree - allows access to the tree via its root
    public MiniTwitComponent getRoot() {
        return root;
    }
    
    //Getter for a specified component - searches based on the ID string passed.
    //If no component with the specified ID exists, returns null.
    public MiniTwitComponent getComponent(String theID) {
        MiniTwitComponent foundComponent = null;
        if (theID != null) {
            foundComponent = this.root.getChild(theID);
        }
        return foundComponent;
    }

}
