//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Pattern implemented: Visitor
//Concrete Visitor class which is designed to count both the total number of messages in the system as well as the total number of messages
//which contain pre-defined "positive" words. This positive wordlist is defined as a constant array of strings.
//Once the count is completed, the visitor can report on the percentage of the total messages in the system which contained positive words.
import java.text.NumberFormat;

public class MiniTwitPosPercentVisitor implements MiniTwitVisitor {

    private int totalMessageCount; //count of all messages in the system
    private int totalPositiveCount; //count of messages that contained words from the POSITIVE_WORDS array
    private final String[] POSITIVE_WORDS = {"good", "great", "excellent", "star trek", "happy", "positive", "pizza"}; //list of words/phrases considered "positive"

    //Constructor
    public MiniTwitPosPercentVisitor() {
        totalMessageCount = 0;
        totalPositiveCount = 0;
    }

    //On visiting the admin, begin to traverse tree by visiting the root Group
    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    //On visiting a user, get the list of tweets held by that user and add its total length to the total
    //count of messages. Then check each message in the list for the presence of a word from the POSITIVE_WORDS array
    //and increment the count of positive messages each time one is found.
    @Override
    public void visitUser(User theUser) {
        totalMessageCount += theUser.getTweetsList().size();
        for (String tweet : theUser.getTweetsList()) {
            if (checkStringForPositive(tweet)) {
                totalPositiveCount++;
            }
        }
    }

    //On visiting a Group, iterate through all children of that group (including recursively visiting subgroups)
    @Override
    public void visitGroup(Group theGroup) {
        for (MiniTwitComponent component : theGroup.getMyList()) {
            component.accept(this);
        }
    }

    //Method to check a provided string for the presence of one of the pre-defined "positive" words.
    //Returns true if found, false otherwise.
    private boolean checkStringForPositive(String tweet) {
        boolean flag = false;
        for (String word : POSITIVE_WORDS) {
            if (tweet.toLowerCase().contains(word)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //Getter for the percentage. Returns a formatted string if there is at least one message in the system,
    //and a string notifying the Admin that there are no messages yet otherwise.
    public String getPositivePercentage() {
        String resultString;
        if (totalMessageCount > 0) {
            double percentage = (double)totalPositiveCount / (double)totalMessageCount;
            NumberFormat percent = NumberFormat.getPercentInstance();
            resultString = percent.format(percentage);
        }else{
            resultString = "No messages in the system yet!";
        }
        
        return resultString;
    }

}
