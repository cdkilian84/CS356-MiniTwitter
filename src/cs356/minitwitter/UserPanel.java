//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris
 */
public class UserPanel extends javax.swing.JPanel {

    private AdminControl controller;
    private MiniTwitComponent myUser;
    private JList followingViewList;
    private JList tweetViewList;
    
    /**
     * Creates new form UserPanel
     */
    public UserPanel(MiniTwitComponent user) {
        this.myUser = user;
        controller = AdminControl.getInstance();
        initComponents();
        initListViews();
    }

    private void initListViews(){
        tweetViewList = new JList(((User)myUser).getMyTweetListModel());
        followingViewList = new JList(((User)myUser).getMyFollowingListModel());
        tweetScrollPane.setViewportView(tweetViewList);
        followingScrollPane.setViewportView(followingViewList);
    }
    
    private void postTweetHandler(){
        if(!tweetTextArea.getText().isEmpty()){
            ((User)myUser).postTweet(tweetTextArea.getText());
            tweetTextArea.setText("");
        }else{
            JOptionPane.showMessageDialog(null, "Can't post an empty tweet! You need to have something to say!");
        }
    }
    
    private void followUserHandler(){
        if(!toFollowField.getText().isEmpty()){
            String theID = toFollowField.getText();
            toFollowField.setText("");
            MiniTwitComponent toFollow = controller.getComponent(theID);
            if(toFollow != null){
                if(toFollow instanceof User){
                    if(!((User)myUser).getFollowingList().contains(toFollow)){
                        myUser.followUser(toFollow);
                    }else{
                        JOptionPane.showMessageDialog(null, "You're already following that user!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "I'm sorry, that ID is for a group and you can only follow Users.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "I'm sorry, that user does not exist.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "You must enter a user ID which you wish to follow!");
        }
    }
    
    
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                    .addComponent(followPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(followingScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tweetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tweetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void postTweetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postTweetButtonActionPerformed
        postTweetHandler();
    }//GEN-LAST:event_postTweetButtonActionPerformed

    private void followUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_followUserButtonActionPerformed
        followUserHandler();
    }//GEN-LAST:event_followUserButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel followPanel;
    private javax.swing.JButton followUserButton;
    private javax.swing.JScrollPane followingScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JButton postTweetButton;
    private javax.swing.JTextField toFollowField;
    private javax.swing.JPanel tweetPanel;
    private javax.swing.JScrollPane tweetScrollPane;
    private javax.swing.JTextArea tweetTextArea;
    // End of variables declaration//GEN-END:variables
}
