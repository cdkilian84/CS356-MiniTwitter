//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Concrete Visitor class which is designed to count the total number of messages stored in the system.
//As such, it maintains a "messageTotal" value which is representative of the total messages counted by the visitor.
//In order to count all users in the system total, this visitor should visit the Admin first (from which point it will
//traverse the entire tree of users and groups).
public class MiniTwitMessageTotalVisitor implements MiniTwitVisitor {

    private int messageTotal;

    public MiniTwitMessageTotalVisitor() {
        messageTotal = 0;
    }

    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    @Override
    public void visitUser(User theUser) {
        messageTotal += theUser.getTweetsList().size();
    }

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
