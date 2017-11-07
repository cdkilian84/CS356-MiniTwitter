//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to count the number of users which exist within the system.
//As such, it maintains a "userCount" value which is representative of the total users counted by the visitor.
//In order to count all users in the system total, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
public class MiniTwitUserTotalVisitor implements MiniTwitVisitor {

    private int userCount; //value to store total visited users so far
    
    //Constructor
    public MiniTwitUserTotalVisitor(){
        userCount = 0;
    }
    
    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //When visiting a User, increment the count.
    @Override
    public void visitUser(User theUser) {
        userCount++;
    }

    //When visiting a group, visit all of the components which are held by that group including
    //recursively visiting any subgroups stored in that group. If this is called on the root of the tree,
    //all users and subgroups will be visited.
    @Override
    public void visitGroup(Group theGroup) {
        for(MiniTwitComponent component : theGroup.getMyList()){
            component.accept(this);
        }
    }
    
    //Getter for the user count
    public int getUserCount(){
        return userCount;
    }
    
}
