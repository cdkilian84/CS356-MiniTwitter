//Author: Christopher Kilian
//CS 356
//Project #3 - Mini-Twitter 2.0
package cs356.minitwitter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

//This panel defines the main method of interaction with the Mini-Twitter program. Through the panel the administrator can add Users
//and Groups, as well as access the User panels for individual users, and gain statistical information about the program status (number
//of users, groups, etc). The methods here handle communication between the panel and the AdminControl object which holds all of the main program
//data, as well as handles the displaying of the tree information.
public class AdminPanel extends javax.swing.JPanel {

    private AdminControl controller;
    private JTree mainViewTree;
    private DefaultMutableTreeNode rootNode; //the root of the visible tree on the panel
    private DefaultTreeModel treeModel; //model used by the tree
    private final String USER = "User";
    private final String GROUP = "Group";

    //Constructor for the panel - gets the instance of the AdminControl and initializes the panel components
    public AdminPanel() {
        controller = AdminControl.getInstance();
        initComponents();
        initTree();
    }

    //Initialize the JTree object which will visualize the Users and Groups - should only be called on program start (during panel construction)
    private void initTree() {
        UIManager.put("Tree.closedIcon", new ImageIcon(getClass().getResource("/groupIconSm.jpg")));
        UIManager.put("Tree.openIcon", new ImageIcon(getClass().getResource("/groupIconSm.jpg")));
        UIManager.put("Tree.leafIcon", new ImageIcon(getClass().getResource("/userIconSm.jpg")));
        //Check to see if controller has pre-existing data or if it only contains an empty root Group
        if (((Group) controller.getRoot()).getMyList().isEmpty()) {
            System.out.println("Empty tree, starting from scratch!");
            rootNode = new DefaultMutableTreeNode(controller.getRoot());
            treeModel = new DefaultTreeModel(rootNode);
        }else{ //if the root's list has values, then the data tree already has structure and needs to be built
            System.out.println("Existing tree data found!! Building representation from tree data!");
            buildModelFromExisting(null, controller.getRoot());
        }

        //build visualized tree from pre-existing data here:
        mainViewTree = new JTree(treeModel);
        mainViewTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        mainViewTree.setShowsRootHandles(true);
        treeScrollPane.setViewportView(mainViewTree);
    }

    //Method to build the onscreen representation of data already stored in the AdminController object.
    //This ensures that if user/group data was added to the controller before opening the window, the on-screen
    //tree will still be reflective of the information stored by the controller object.
    private void buildModelFromExisting(DefaultMutableTreeNode parentNode, MiniTwitComponent toInsert) {
        DefaultMutableTreeNode newNode = null;
        //if parentNode is null and toInsert is "Root", then this is the first call for this method and the treeModel must be instantiated before continuing
        if (parentNode == null && toInsert.getMyID().equals("Root")) { 
            rootNode = new DefaultMutableTreeNode(controller.getRoot());
            treeModel = new DefaultTreeModel(rootNode);
            for (MiniTwitComponent component : ((Group) toInsert).getMyList()) {
                buildModelFromExisting(rootNode, component);
            }
        } else {
            if(toInsert instanceof User){
                newNode = new DefaultMutableTreeNode(toInsert, false); //users are always leaf nodes
            }else{
                newNode = new DefaultMutableTreeNode(toInsert, true); //groups allow children
            }
            
            treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());
            if (toInsert instanceof Group) { //if the object just inserted is a group, then insert all of its children with this group as parent
                for (MiniTwitComponent component : ((Group) toInsert).getMyList()) {
                    buildModelFromExisting(newNode, component);
                }
            }
        }
    }

    //Method which handles the addition of a specified component to both the data and visualized trees. Ensures they are in sync.
    //If the component ID is not unique, method informs user and terminates (without adding anything). The same occurs if the user
    //attempts to add a component with no ID at all.
    private void addComponentHandler(String theID, String type) {
        if (!theID.isEmpty()) { //only add users that don't have a blank name and are a unique ID
            if (controller.checkForUniqueID(theID)) {
                MiniTwitComponent newComponent;
                DefaultMutableTreeNode newNode;
                DefaultMutableTreeNode selectedNode;
                MiniTwitComponent insertionLocation = controller.getRoot(); //default to inserting new users in the root group
                TreePath selectedPath = mainViewTree.getSelectionPath(); //path selected by user on the panel

                //instantiate newComponent and newNode based on type
                if (type.equals(USER)) {
                    newComponent = new User(theID);
                    newNode = new DefaultMutableTreeNode(newComponent, false);//users are always leaf nodes
                } else { //if it's not a user, it must be a group
                    newComponent = new Group(theID);
                    newNode = new DefaultMutableTreeNode(newComponent, true);//Groups allow children
                }

                //first check if null, if not null check if selected path value allows children or not (if not, use root node instead)
                if ((selectedPath == null) || !((DefaultMutableTreeNode) selectedPath.getLastPathComponent()).getAllowsChildren()) {
                    selectedNode = rootNode;
                } else {
                    selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    MiniTwitComponent theComponent = controller.getComponent(selectedNode.toString());//get the group to insert into based on ID (same as selected group name)
                    if (theComponent instanceof Group) {
                        insertionLocation = theComponent;
                    }
                }

                controller.insertComponent(newComponent, insertionLocation); //add new user/group to the proper location in the admin tree structure
                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount()); //add new user to the proper location in the visible tree
                mainViewTree.scrollPathToVisible(new TreePath(newNode.getPath()));
            } else {
                JOptionPane.showMessageDialog(null, "That ID is not unique! Must enter a unique ID!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cannot add an item with an empty ID!");
        }

        //these calls are strictly for testing purposes (visualizes the stored tree structure in the command line)
        //controller.getRoot().print(); 
        //System.out.println("----------------------------");
    }

    //Method which handles the opening of a User Panel.
    //Checks to see if a user was selected (if not, notify Admin). Pass selected User object to the User Panel for instantiation
    //and open a new Frame to hold that panel.
    private void openUserHandler() {
        TreePath selectedPath = mainViewTree.getSelectionPath();

        if ((selectedPath == null) || ((DefaultMutableTreeNode) selectedPath.getLastPathComponent()).getAllowsChildren()) {
            JOptionPane.showMessageDialog(null, "Please select a valid user (not a group!) to open.");
        } else {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            MiniTwitComponent theUser = controller.getComponent(selectedNode.toString());
            JFrame userFrame = new JFrame();
            JPanel userPanel = new UserPanel(theUser);

            userFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            userFrame.setTitle("User " + theUser.getMyID() + "'s Control Panel");
            userFrame.add(userPanel);
            userFrame.pack();
            userFrame.setLocationRelativeTo(this);
            userFrame.setVisible(true);
        }

    }

    //Method which handles the displaying of the total number of users.
    //Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void userTotalHandler() {
        MiniTwitVisitor userTotalVisitor = new MiniTwitUserTotalVisitor();
        controller.accept(userTotalVisitor);
        JOptionPane.showMessageDialog(null, "The total number of users is: " + ((MiniTwitUserTotalVisitor) userTotalVisitor).getUserCount());
    }

    //Method which handles the displaying of the total number of groups.
    //Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void groupTotalHandler() {
        MiniTwitVisitor groupTotalVisitor = new MiniTwitGroupTotalVisitor();
        controller.accept(groupTotalVisitor);
        JOptionPane.showMessageDialog(null, "The total number of groups is: " + ((MiniTwitGroupTotalVisitor) groupTotalVisitor).getGroupCount()
                + "\n" + "(Remember that the group count is always a minimum of 1 thanks to the Root!)");
    }

    //Method which handles the displaying of the total number of messages stored by the system.
    //Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void messageTotalHandler() {
        MiniTwitVisitor messageTotalVisitor = new MiniTwitMessageTotalVisitor();
        controller.accept(messageTotalVisitor);
        JOptionPane.showMessageDialog(null, "The total number of messages stored in the system is: " + ((MiniTwitMessageTotalVisitor) messageTotalVisitor).getMessageCount());
    }

    //Method which handles the displaying of the percentage of "positive" messages in the system (determined by the
    //visitor object). Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void positiveMessageHandler() {
        MiniTwitVisitor positivePercentageVisitor = new MiniTwitPosPercentVisitor();
        controller.accept(positivePercentageVisitor);
        JOptionPane.showMessageDialog(null, "The percentage of positive messages stored in the system is: "
                + ((MiniTwitPosPercentVisitor) positivePercentageVisitor).getPositivePercentage());
    }
    
    //Method which handles the displaying of whether or not all the ID's are valid
    //Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void verifyIDHandler(){
        MiniTwitVisitor validateVisitor = new MiniTwitValidateVisitor();
        controller.accept(validateVisitor);
        if(((MiniTwitValidateVisitor)validateVisitor).isItValid()){
            JOptionPane.showMessageDialog(null, "The User and Group ID's are all valid! Congratulations!");
        }else{
            JOptionPane.showMessageDialog(null, "The User and Group ID's are not all valid! ID's must all be unique and not contain spaces!");
        }
    }
    
    //Method which handles the displaying of the most recently updated User in the system.
    //Instantiate an appropriate visitor object, and have it visit the controller (which will then handle
    //all other visitations required). Finally display the results.
    private void findLastUpdatedHandler(){
        MiniTwitVisitor validateVisitor = new MiniTwitLastUpdatedVisitor();
        controller.accept(validateVisitor);
        JOptionPane.showMessageDialog(null, "The most recently updated User is: " + 
                ((MiniTwitLastUpdatedVisitor)validateVisitor).getUpdatedUser());
    }

    //Generated code for the panel is found beyond this point. Mostly just setting up the sub-panels, labels, and action listeners for the buttons.
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userIDField = new javax.swing.JTextField();
        groupIDField = new javax.swing.JTextField();
        addUserButton = new javax.swing.JButton();
        addGroupButton = new javax.swing.JButton();
        openUserButton = new javax.swing.JButton();
        treeScrollPane = new javax.swing.JScrollPane();
        miscFuncPanel = new javax.swing.JPanel();
        showUserTotalButton = new javax.swing.JButton();
        showGroupTotalButton = new javax.swing.JButton();
        showMessagesTotalButton = new javax.swing.JButton();
        showPosPercentButton = new javax.swing.JButton();
        newButtonsPanel = new javax.swing.JPanel();
        verifyIDButton = new javax.swing.JButton();
        lastUpdatedButton = new javax.swing.JButton();

        userIDField.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        userIDField.setToolTipText("Enter User ID to create");
        userIDField.setPreferredSize(new java.awt.Dimension(230, 40));

        groupIDField.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        groupIDField.setToolTipText("Enter Group ID to create");
        groupIDField.setPreferredSize(new java.awt.Dimension(230, 40));

        addUserButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        addUserButton.setText("Add User");
        addUserButton.setToolTipText("Make sure to enter an ID first!");
        addUserButton.setPreferredSize(new java.awt.Dimension(150, 40));
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        addGroupButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        addGroupButton.setText("Add Group");
        addGroupButton.setToolTipText("Make sure to enter an ID first!");
        addGroupButton.setPreferredSize(new java.awt.Dimension(150, 40));
        addGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGroupButtonActionPerformed(evt);
            }
        });

        openUserButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        openUserButton.setText("Open User View");
        openUserButton.setPreferredSize(new java.awt.Dimension(450, 40));
        openUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openUserButtonActionPerformed(evt);
            }
        });

        miscFuncPanel.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        showUserTotalButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        showUserTotalButton.setText("Show User Total");
        showUserTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));
        showUserTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUserTotalButtonActionPerformed(evt);
            }
        });
        miscFuncPanel.add(showUserTotalButton);

        showGroupTotalButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        showGroupTotalButton.setText("Show Group Total");
        showGroupTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));
        showGroupTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showGroupTotalButtonActionPerformed(evt);
            }
        });
        miscFuncPanel.add(showGroupTotalButton);

        showMessagesTotalButton.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        showMessagesTotalButton.setText("Show Messages Total");
        showMessagesTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));
        showMessagesTotalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showMessagesTotalButtonActionPerformed(evt);
            }
        });
        miscFuncPanel.add(showMessagesTotalButton);

        showPosPercentButton.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        showPosPercentButton.setText("Show Positive Percentage");
        showPosPercentButton.setPreferredSize(new java.awt.Dimension(180, 40));
        showPosPercentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPosPercentButtonActionPerformed(evt);
            }
        });
        miscFuncPanel.add(showPosPercentButton);

        newButtonsPanel.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        verifyIDButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        verifyIDButton.setText("Verify User/Group ID's");
        verifyIDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyIDButtonActionPerformed(evt);
            }
        });
        newButtonsPanel.add(verifyIDButton);

        lastUpdatedButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lastUpdatedButton.setText("Find Last Updated User");
        lastUpdatedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastUpdatedButtonActionPerformed(evt);
            }
        });
        newButtonsPanel.add(lastUpdatedButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miscFuncPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(groupIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(userIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(openUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newButtonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userIDField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(groupIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(openUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(miscFuncPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treeScrollPane)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //Action listener for the "Add User" button.
    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        String theID = userIDField.getText();
        userIDField.setText(""); //blank the field immediately
        addComponentHandler(theID, USER);
    }//GEN-LAST:event_addUserButtonActionPerformed

    //Action listener for the "Add Group" button.
    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGroupButtonActionPerformed
        String theID = groupIDField.getText();
        groupIDField.setText(""); //blank the field immediately
        addComponentHandler(theID, GROUP);
    }//GEN-LAST:event_addGroupButtonActionPerformed

    //Action listener for the "Open User View" button.
    private void openUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openUserButtonActionPerformed
        openUserHandler();
    }//GEN-LAST:event_openUserButtonActionPerformed

    //Action listener for the "Show User Total" button.
    private void showUserTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showUserTotalButtonActionPerformed
        userTotalHandler();
    }//GEN-LAST:event_showUserTotalButtonActionPerformed

    //Action listener for the "Show Group Total" button.
    private void showGroupTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showGroupTotalButtonActionPerformed
        groupTotalHandler();
    }//GEN-LAST:event_showGroupTotalButtonActionPerformed

    //Action listener for the "Show Messages Total" button.
    private void showMessagesTotalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showMessagesTotalButtonActionPerformed
        messageTotalHandler();
    }//GEN-LAST:event_showMessagesTotalButtonActionPerformed

    //Action listener for the "Show Positive Percentage" button.
    private void showPosPercentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPosPercentButtonActionPerformed
        positiveMessageHandler();
    }//GEN-LAST:event_showPosPercentButtonActionPerformed

    private void verifyIDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyIDButtonActionPerformed
        verifyIDHandler();
    }//GEN-LAST:event_verifyIDButtonActionPerformed

    private void lastUpdatedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastUpdatedButtonActionPerformed
        findLastUpdatedHandler();
    }//GEN-LAST:event_lastUpdatedButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGroupButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JTextField groupIDField;
    private javax.swing.JButton lastUpdatedButton;
    private javax.swing.JPanel miscFuncPanel;
    private javax.swing.JPanel newButtonsPanel;
    private javax.swing.JButton openUserButton;
    private javax.swing.JButton showGroupTotalButton;
    private javax.swing.JButton showMessagesTotalButton;
    private javax.swing.JButton showPosPercentButton;
    private javax.swing.JButton showUserTotalButton;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JTextField userIDField;
    private javax.swing.JButton verifyIDButton;
    // End of variables declaration//GEN-END:variables
}
