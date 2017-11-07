//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to count the number of groups which exist within the system.
//As such, it maintains a "groupCount" value which is representative of the total groups counted by the visitor.
//In order to count all groups in the system total, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
//Because the Admin always includes a "Root" group, the group count will always be a minimum of 1 for the entire MiniTwit system.
public class MiniTwitGroupTotalVisitor implements MiniTwitVisitor {

    private int groupCount; //value to store total visited groups so far

    //Constructor
    public MiniTwitGroupTotalVisitor() {
        groupCount = 0;
    }

    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //On visiting a user, do nothing. Since this visitor only counts groups, Users should be ignored.
    @Override
    public void visitUser(User theUser) {
        //Do nothing on visiting a user
    }

    //On visiting a group, increment the group count and then look for any subgroups which should also be visited.
    //Since this visitor only counts groups, ignore any components which are Users.
    @Override
    public void visitGroup(Group theGroup) {
        groupCount++;
        for (MiniTwitComponent component : theGroup.getMyList()) {
            if (component instanceof Group) {
                component.accept(this); //visitGroup((Group) component);
            }
        }
    }

    //Getter for the group count
    public int getGroupCount() {
        return groupCount;
    }

}
