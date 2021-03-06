//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

//This panel defines the main method of interaction with a User object. It allows the user to follow other users while displaying the list of
//Users already being followed. It also allows the user to post new tweets that they and their followers will see. These tweets are displayed
//on this panel as well. The User object controlled by the panel is passed to the panel as part of its instantiation, and a reference to it is stored
//in the panel itself, greatly simplifying access to that User's data.
public class UserPanel extends javax.swing.JPanel {

    private AdminControl controller; //access to the controller object (singleton)
    private MiniTwitComponent myUser; //the local reference to the User which corresponds to this panel (instantiated on construction)
    private JList followingViewList; //the lists which will display User information
    private JList tweetViewList;

    //Constructor for panel
    public UserPanel(MiniTwitComponent user) {
        this.myUser = user;
        controller = AdminControl.getInstance();
        clearListeners();
        initComponents();
        initListViews();
        initTime();

        //add listener to tweet list so that "last updated" timestamp can be dynamically updated whenever a new tweet is sent or received
        ListDataListener updateListener = new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                Date updatedTime = new Date(((User) myUser).getLastUpdatedTime());
                updatedTimeLabel.setText("Last Updated: " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(updatedTime));
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                System.out.println("Logging list item removed.");
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                System.out.println("Logging list contents changed.");
            }

        };

        ((User) myUser).getMyTweetListModel().addListDataListener(updateListener);

    }

    //Method which initializes the timestamp label for the most recently updated time.
    //Needed for cases when a user is updated, the User View is closed, and then reopened.
    private void initTime() {
        if (((User) myUser).getLastUpdatedTime() != 0) {
            Date updatedTime = new Date(((User) myUser).getLastUpdatedTime());
            updatedTimeLabel.setText("Last Updated: " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(updatedTime));
        } else {
            updatedTimeLabel.setText("Last Updated: Never!");
        }

    }

    //method to ensure that there isn't a buildup of unnecessary listeners on the Users ListModel objects when opening and closing
    //the User View windows. Only to be used in constructor before panel component initiation.
    private void clearListeners() {
        DefaultListModel tweetModel = ((User) myUser).getMyTweetListModel();
        DefaultListModel followModel = ((User) myUser).getMyFollowingListModel();

        if (tweetModel.getListDataListeners().length > 0) {
            for (ListDataListener listener : tweetModel.getListDataListeners()) {
                tweetModel.removeListDataListener(listener);
            }
        }
        if (followModel.getListDataListeners().length > 0) {
            for (ListDataListener listener : followModel.getListDataListeners()) {
                followModel.removeListDataListener(listener);
            }
        }
    }

    //Method to initialize the JList views using the models stored by the User object
    private void initListViews() {
        tweetViewList = new JList(((User) myUser).getMyTweetListModel());
        followingViewList = new JList(((User) myUser).getMyFollowingListModel());
        tweetScrollPane.setViewportView(tweetViewList);
        followingScrollPane.setViewportView(followingViewList);
    }

    //Handler method for posting tweets. Gets the contents of the tweet text area, and if it's empty
    //notifies the user that they can't post an empty tweet. Otherwise, passes the gathered text to the User's
    //"postTweet" method and clears the tweet text area for another tweet.
    private void postTweetHandler() {
        if (!tweetTextArea.getText().isEmpty()) {
            ((User) myUser).postTweet(tweetTextArea.getText());
            tweetTextArea.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Can't post an empty tweet! You need to have something to say!");
        }
    }

    //Handler method for following another User with the specified ID. 
    //Gets the contents of the User ID text field and checks that it meets a number of criteria in order
    //to follow. First checks for an empty field (notifies user if it's empty), next checks if the specified User
    //exists (and notifies if they don't), then checks if the specified ID is a User or a Group (notifies if it's not a 
    //User since only User's can be followed), and finally checks to see if this User is already following the indicated
    //User (notifies if they are). Assuming all of these checks are passed, then finally the "followUser" method for this
    //User is called.
    private void followUserHandler() {
        if (!toFollowField.getText().isEmpty()) {
            String theID = toFollowField.getText();
            toFollowField.setText("");
            MiniTwitComponent toFollow = controller.getComponent(theID);
            if (toFollow != null) {
                if (toFollow instanceof User) {
                    if (!((User) myUser).getFollowingList().contains(toFollow)) {
                        myUser.followUser(toFollow);
                    } else {
                        JOptionPane.showMessageDialog(null, "You're already following that user!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "I'm sorry, that ID is for a group and you can only follow Users.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "I'm sorry, that user does not exist.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must enter a user ID which you wish to follow!");
        }
    }

    //Generated code for the panel is found beyond this point. Mostly just setting up the sub-panels, labels, and action listeners for the buttons.
    //Please note that a small amount of custom code is also here, just for setting label values on the panel. Programatically sets the "nameLabel"
    //and creationTimeLabel to show appropriate values when the panel is instantiated.
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        followingScrollPane = new javax.swing.JScrollPane();
        tweetScrollPane = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        namePanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tweetPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tweetTextArea = new javax.swing.JTextArea();
        postTweetButton = new javax.swing.JButton();
        followPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        toFollowField = new javax.swing.JTextField();
        followUserButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        creationTimeLabel = new javax.swing.JLabel();
        updatedTimeLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(650, 580));

        jLabel1.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        jLabel1.setText("List of users I am following:");

        namePanel.setLayout(new java.awt.BorderLayout());

        nameLabel.setFont(new java.awt.Font("Century", 1, 16)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setText("Welcome " + myUser.getMyID() + " to your personal Mini-Twitter!");
        namePanel.add(nameLabel, java.awt.BorderLayout.CENTER);

        jLabel4.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        jLabel4.setText("Your feed:");

        jLabel3.setFont(new java.awt.Font("Century", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Write tweets here:");
        tweetPanel.add(jLabel3);

        tweetTextArea.setColumns(20);
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setRows(5);
        tweetTextArea.setToolTipText("Pearls of wisdom");
        tweetTextArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tweetTextArea.setPreferredSize(new java.awt.Dimension(260, 82));
        jScrollPane2.setViewportView(tweetTextArea);

        tweetPanel.add(jScrollPane2);

        postTweetButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        postTweetButton.setText("Post Tweet");
        postTweetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postTweetButtonActionPerformed(evt);
            }
        });
        tweetPanel.add(postTweetButton);

        jLabel2.setFont(new java.awt.Font("Century", 1, 12)); // NOI18N
        jLabel2.setText("Enter User ID to Follow:");
        followPanel.add(jLabel2);

        toFollowField.setToolTipText("Enter User ID to follow");
        toFollowField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toFollowField.setPreferredSize(new java.awt.Dimension(250, 30));
        followPanel.add(toFollowField);

        followUserButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        followUserButton.setText("Follow User");
        followUserButton.setToolTipText("Click to follow the user!");
        followUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                followUserButtonActionPerformed(evt);
            }
        });
        followPanel.add(followUserButton);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        creationTimeLabel.setFont(new java.awt.Font("Century", 1, 12)); // NOI18N
        creationTimeLabel.setText("Creation date/time: " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(myUser.getCreationTime())));
        jPanel1.add(creationTimeLabel);

        updatedTimeLabel.setFont(new java.awt.Font("Century", 1, 12)); // NOI18N
        updatedTimeLabel.setText("Time last updated: Never!");
        jPanel1.add(updatedTimeLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tweetScrollPane)
                            .addComponent(followingScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(namePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tweetPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(followPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followingScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tweetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tweetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //Action listener for the "Post Tweet" button.
    private void postTweetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postTweetButtonActionPerformed
        postTweetHandler();
    }//GEN-LAST:event_postTweetButtonActionPerformed

    //Action listener for the "Follow User" button.
    private void followUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_followUserButtonActionPerformed
        followUserHandler();
    }//GEN-LAST:event_followUserButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel creationTimeLabel;
    private javax.swing.JPanel followPanel;
    private javax.swing.JButton followUserButton;
    private javax.swing.JScrollPane followingScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JButton postTweetButton;
    private javax.swing.JTextField toFollowField;
    private javax.swing.JPanel tweetPanel;
    private javax.swing.JScrollPane tweetScrollPane;
    private javax.swing.JTextArea tweetTextArea;
    private javax.swing.JLabel updatedTimeLabel;
    // End of variables declaration//GEN-END:variables
}
