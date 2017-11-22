//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;

//Pattern implemented: Composite, Visitor
//Group class is a subclass of the MiniTwitComponent abstract class. Implements appropriate versions of the abstract methods
//(though it falls back on the default for "follow user"). This class stores a list of components (including storing more groups in a 
//recursive fashion), acting in the composite role for this pattern implementation (rather than the leaf role filled by the User class). 
//Any Group object can traverse its portion of the sub-tree using the "getChild" method, and if the group is the root of the tree then 
//it can traverse the entire tree.
public class Group extends MiniTwitComponent {

    private String myID;
    private List<MiniTwitComponent> componentList;
    //private long creationTime;

    //Constructor
    public Group(String myID) {
        super(); //call abstract constructor which sets creation time
        //creationTime = System.currentTimeMillis();
        this.myID = myID;
        componentList = new ArrayList();
    }

    //Convenience method for the Group class which uses getChild to check if the searched for ID
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
    //Will recursively call itself if any of the children are themselves Groups. Calling this on the root of the tree
    //will search the whole tree for the specified ID.
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

    //Overridden toString for Group (returns ID)
    @Override
    public String toString(){
        return this.myID;
    }

    //Implementation of Visitor pattern - accept method which calls appropriate method on the visitor.
    @Override
    public void accept(MiniTwitVisitor theVisitor) {
        theVisitor.visitGroup(this);
    }
    
    //Setter which adds a specified component to the list of components.
    //Due to tree-nature of this class, checks for ID uniqueness should come before this method is called.
    @Override
    public void addComponent(MiniTwitComponent component) {
        componentList.add(component);
    }

    //Getter for the ID of this Group
    @Override
    public String getMyID() {
        return myID;
    }
    
    //Getter for the list of components stored by this Group
    public List<MiniTwitComponent> getMyList(){
        return componentList;
    }

    //Implementation of "print" which allows output to command line of tree structure
    //used only for testing purposes - leaving in place (commented out of course) for ease of future testing.
//    @Override
//    public void print() {
//        if(componentList.isEmpty()){
//            System.out.println(", Group: " + this.myID);
//        }else{
//            for (MiniTwitComponent component : componentList) {
//                System.out.print("Group: " + this.myID);
//                component.print();
//            }
//        }
//    }
    
}
