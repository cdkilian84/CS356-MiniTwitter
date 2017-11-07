//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

//Concrete Visitor class which is designed to 
import java.text.NumberFormat;

public class MiniTwitPosPercentVisitor implements MiniTwitVisitor {

    private int totalMessageCount;
    private int totalPositiveCount;
    private final String[] POSITIVE_WORDS = {"good", "great", "excellent", "star trek", "happy", "positive", "pizza"}; //list of words/phrases considered "positive"

    public MiniTwitPosPercentVisitor() {
        totalMessageCount = 0;
        totalPositiveCount = 0;
    }

    @Override
    public void visitAdmin(AdminControl admin) {
        admin.getRoot().accept(this);
    }

    @Override
    public void visitUser(User theUser) {
        totalMessageCount += theUser.getTweetsList().size();
        for (String tweet : theUser.getTweetsList()) {
            if (checkStringForPositive(tweet)) {
                totalPositiveCount++;
            }
        }
    }

    @Override
    public void visitGroup(Group theGroup) {
        for (MiniTwitComponent component : theGroup.getMyList()) {
            component.accept(this);
        }
    }

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

    //Getter for the percentage
    public String getPositivePercentage() {
        String resultString;
        if (totalMessageCount > 0) {
            double percentage = (double)totalPositiveCount / (double)totalMessageCount;
            NumberFormat percent = NumberFormat.getPercentInstance();
            resultString = percent.format(percentage);
        }else{
            resultString = "No messages in the system yet!";
        }
        System.out.println("Getting positive percentage! The totalPositiveCount was: " + totalPositiveCount + " and the total message count was: " + totalMessageCount);
        
        return resultString;
    }

    public String[] getPositiveWords() {
        return POSITIVE_WORDS;
    }

}
