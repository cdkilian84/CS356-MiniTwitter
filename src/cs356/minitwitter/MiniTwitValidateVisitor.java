//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to validate that all ID's entered into the system are "valid".
//To be counted as valid, an ID must be unique and it must not contain any spaces. The visitor maintains 
//a boolean member variable which represents the validity of the checked system (true if valid, false if not) and
//a List of all the visited ID values against which it can compare new IDs.
//In order to validate all users in the system, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
public class MiniTwitValidateVisitor implements MiniTwitVisitor {

    private boolean validated;
    private List<String> seenIDs;
    
    //constructor, initializes member variables
    public MiniTwitValidateVisitor(){
        validated = true;
        seenIDs = new ArrayList<>();
    }
    
    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //On visiting a user, check the ID of that user against the list of seen IDs
    @Override
    public void visitUser(User theUser) {
        checkID(theUser.getMyID());
    }

    //On visiting a group, check its ID against the list of seen IDs, then check all of its children
    @Override
    public void visitGroup(Group theGroup) {
        checkID(theGroup.getMyID());
        
        for(MiniTwitComponent component : theGroup.getMyList()){
            component.accept(this);
        }
    }
    
    //Method to actually check the ID of the current component - checks if the ID has already been seen (uniqueness check)
    //and then checks to see if the ID contains a space. If either of these things are true, the "validated" member variable
    //is marked as false. Either way, the ID is lastly added to the list of seen IDs.
    private void checkID(String theID){
        if((seenIDs.contains(theID)) || theID.contains(" ")){
            validated = false;
        }
        seenIDs.add(theID);
    }
    
    //getter to return the validated boolean value
    public boolean isItValid(){
        return validated;
    }
    
}
