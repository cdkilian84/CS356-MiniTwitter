//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to count the total number of messages stored in the system.
//As such, it maintains a "messageTotal" value which is representative of the total messages counted by the visitor.
//In order to count all messages stored in the system total, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
public class MiniTwitMessageTotalVisitor implements MiniTwitVisitor {

    private int messageTotal; //count of messages

    //Constructor
    public MiniTwitMessageTotalVisitor() {
        messageTotal = 0;
    }

    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //On visiting a User, get their list of tweets and add the list size to the total of all messages
    @Override
    public void visitUser(User theUser) {
        messageTotal += theUser.getTweetsList().size();
    }

    //On visiting a Group, iterate through all children of that group (including recursively visiting subgroups)
    @Override
    public void visitGroup(Group theGroup) {
        for(MiniTwitComponent component : theGroup.getMyList()){
            component.accept(this);
        }
    }
    
    //Getter for the message count
    public int getMessageCount() {
        return messageTotal;
    }

}
