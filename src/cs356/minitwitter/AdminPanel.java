/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356.minitwitter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Chris
 */
public class AdminPanel extends javax.swing.JPanel {

    private AdminControl controller;
    private JTree mainViewTree;
    private DefaultMutableTreeNode rootNode; //the root of the visible tree on the panel
    private DefaultTreeModel treeModel;
    private final String USER = "User";
    private final String GROUP = "Group";
    

    public AdminPanel() {
        controller = AdminControl.getInstance();
        initComponents();
        initTree();
    }

    private void initTree() {
        UIManager.put("Tree.closedIcon", new ImageIcon(getClass().getResource("/groupIconSm.jpg")));
        UIManager.put("Tree.openIcon", new ImageIcon(getClass().getResource("/groupIconSm.jpg")));
        UIManager.put("Tree.leafIcon", new ImageIcon(getClass().getResource("/userIconSm.jpg")));
        rootNode = new DefaultMutableTreeNode(controller.getRoot());//new DefaultMutableTreeNode(controller.getRoot().getMyID());
        treeModel = new DefaultTreeModel(rootNode);
        mainViewTree = new JTree(treeModel);
        mainViewTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        mainViewTree.setShowsRootHandles(true);

        treeScrollPane.setViewportView(mainViewTree);
    }

    private void addComponentHandler(String theID, String type) {
        if (!theID.isEmpty()) { //only add users that don't have a blank name and are a unique ID
            if (controller.checkForUniqueID(theID)){
                MiniTwitComponent newComponent;
                DefaultMutableTreeNode newNode;
                DefaultMutableTreeNode selectedNode;
                MiniTwitComponent insertionLocation = controller.getRoot(); //default to inserting new users in the root group
                TreePath selectedPath = mainViewTree.getSelectionPath();

                //instantiate newComponent and newNode based on type
                if (type.equals(USER)) {
                    newComponent = new User(theID);
                    newNode = new DefaultMutableTreeNode(newComponent, false);//new DefaultMutableTreeNode(theID, false); //users are always leaf nodes
                } else { //if it's not a user, it must be a group
                    newComponent = new Group(theID);
                    newNode = new DefaultMutableTreeNode(newComponent, true);//new DefaultMutableTreeNode(theID, true); //Groups allow children
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

                insertionLocation.addUser(newComponent); //add new user/group to the proper location in the admin tree structure
                treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount()); //add new user to the proper location in the visible tree
                mainViewTree.scrollPathToVisible(new TreePath(newNode.getPath()));
            } else {
                JOptionPane.showMessageDialog(null, "That ID is not unique! Must enter a unique ID!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cannot add an item with an empty ID!");
        }

        controller.getRoot().print();
        System.out.println("----------------------------");
    }

    private void openUserHandler() {
        TreePath selectedPath = mainViewTree.getSelectionPath();

        if ((selectedPath == null) || ((DefaultMutableTreeNode) selectedPath.getLastPathComponent()).getAllowsChildren()) {
            JOptionPane.showMessageDialog(null, "Please select a valid user (not a group!) to open.");
        } else {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            //TEST
            //MiniTwitComponent theUser = controller.getComponent(selectedNode.toString());//controller.getRoot().getChild(selectedNode.toString());
            MiniTwitComponent theUser = (MiniTwitComponent) selectedNode.getUserObject();
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
        showUserTotalButton = new javax.swing.JButton();
        showGroupTotalButton = new javax.swing.JButton();
        showMessagesTotalButton = new javax.swing.JButton();
        showPosPercentButton = new javax.swing.JButton();
        treeScrollPane = new javax.swing.JScrollPane();

        userIDField.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        userIDField.setToolTipText("Enter User ID to create");
        userIDField.setPreferredSize(new java.awt.Dimension(230, 40));

        groupIDField.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        groupIDField.setToolTipText("Enter Group ID to create");
        groupIDField.setPreferredSize(new java.awt.Dimension(230, 40));

        addUserButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        addUserButton.setText("Add User");
        addUserButton.setPreferredSize(new java.awt.Dimension(150, 40));
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        addGroupButton.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        addGroupButton.setText("Add Group");
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

        showUserTotalButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        showUserTotalButton.setText("Show User Total");
        showUserTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));

        showGroupTotalButton.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        showGroupTotalButton.setText("Show Group Total");
        showGroupTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));

        showMessagesTotalButton.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        showMessagesTotalButton.setText("Show Messages Total");
        showMessagesTotalButton.setPreferredSize(new java.awt.Dimension(180, 40));

        showPosPercentButton.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        showPosPercentButton.setText("Show Positive Percentage");
        showPosPercentButton.setPreferredSize(new java.awt.Dimension(180, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(groupIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(userIDField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(openUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(showUserTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(showGroupTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(showMessagesTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(showPosPercentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showUserTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showGroupTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showMessagesTotalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showPosPercentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treeScrollPane)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        String theID = userIDField.getText();
        userIDField.setText(""); //blank the field immediately
        addComponentHandler(theID, USER);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGroupButtonActionPerformed
        String theID = groupIDField.getText();
        groupIDField.setText(""); //blank the field immediately
        addComponentHandler(theID, GROUP);
    }//GEN-LAST:event_addGroupButtonActionPerformed

    private void openUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openUserButtonActionPerformed
        openUserHandler();
    }//GEN-LAST:event_openUserButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGroupButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JTextField groupIDField;
    private javax.swing.JButton openUserButton;
    private javax.swing.JButton showGroupTotalButton;
    private javax.swing.JButton showMessagesTotalButton;
    private javax.swing.JButton showPosPercentButton;
    private javax.swing.JButton showUserTotalButton;
    private javax.swing.JScrollPane treeScrollPane;
    private javax.swing.JTextField userIDField;
    // End of variables declaration//GEN-END:variables
}
