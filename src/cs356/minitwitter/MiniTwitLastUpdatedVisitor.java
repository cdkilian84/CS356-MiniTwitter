//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to compare Users last updated time stamp and get the most recent one.
//This class does not sort the results, so if two Users have the same updated timestamp, then the first one encountered
//is the one which will be reported. The class outputs the result as a string, either reporting that no users have updated yet, 
//or returning the ID value of the most recently updated user.
//In order to validate all users in the system, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
public class MiniTwitLastUpdatedVisitor implements MiniTwitVisitor {

    private MiniTwitComponent updatedUser;

    //constructor (nothing needs initialization in this case)
    public MiniTwitLastUpdatedVisitor(){}
    
    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //On visiting a User, check to see whether the currently visited User has a more recent timestamp than the one
    //currently held (or if none is currently held, then get it). Also check to see if the visited user has a "last updated"
    //time of "0", since this indicates it has never been updated. These Users should be ignored. If all visited Users have
    //"0" for this value, then no Users have been updated yet and this visitor should not reference any of them!
    @Override
    public void visitUser(User theUser) {
        if ((updatedUser == null) || (theUser.getLastUpdatedTime() > ((User) updatedUser).getLastUpdatedTime())) {
            if (theUser.getLastUpdatedTime() != 0) {
                updatedUser = theUser;
            }
        }
    }

    //On visiting a group, just visit all of its children (groups do not have a "last updated" attribute)
    @Override
    public void visitGroup(Group theGroup) {
        for (MiniTwitComponent component : theGroup.getMyList()) {
            component.accept(this);
        }
    }

    //getter to return the ID string of the most recently updated User. If no User has been
    //updated, then the returned string will reflect this fact.
    public String getUpdatedUser() {
        String returnVal;
        if(updatedUser == null){
            returnVal = "No users updated!";
        }else{
            returnVal = updatedUser.getMyID();
        }
        return returnVal;
    }

}
