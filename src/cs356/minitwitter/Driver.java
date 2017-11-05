/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356.minitwitter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Chris
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initLookAndFeel();

//        AdminControl controller = AdminControl.getInstance();
//        controller.getRoot().addUser(new User("Adam"));
//        controller.getRoot().addUser(new User("John"));
//        controller.getRoot().addUser(new Group("Cool People"));
//        MiniTwitComponent test = controller.getRoot().getChild("Cool People");
//        test.addUser(new User("Chris"));
//        test.addUser(new User("Susan"));
//        test.addUser(new Group("The Coolest"));
//        MiniTwitComponent test2 = controller.getRoot().getChild("The Coolest");
//        test2.addUser(new User("Stevearino"));
//        test2.addUser(new User("Roger"));
//        controller.getRoot().addUser(new User("Nina"));
        
        //controller.getRoot().print();
        
        //for(MiniTwitComponent component : ((Group)controller.getRoot()).getMyList()){
        //    component.print();
        //}
        
        //System.out.println(((Group)controller.getRoot()).checkForUniqueID("Cool People"));
        
        JFrame mainFrame = new JFrame();
        JPanel mainPanel = new AdminPanel();
        mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(new java.awt.Dimension(800, 500));
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        
        //Need to implement at some point!
        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //stuff
            }
        });
        */
    }
    
    //method: initLookAndFeel
    //purpose: Sets up the "look and feel" of the game
    private static void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("There was an UnsupportedLookAndFeelException with the following L&F: " + UIManager.getCrossPlatformLookAndFeelClassName());
            System.err.println("Using default look and feel.");
        } catch (ClassNotFoundException e) {
            System.err.println("There was a ClassNotFoundException with the following L&F: " + UIManager.getCrossPlatformLookAndFeelClassName());
            System.err.println("Using default look and feel.");
        } catch (InstantiationException e) {
            System.err.println("There was an InstantiationException with the following L&F: " + UIManager.getCrossPlatformLookAndFeelClassName());
            System.err.println("Using default look and feel.");
        } catch (IllegalAccessException e) {
            System.err.println("There was an IllegalAccessException with the following L&F: " + UIManager.getCrossPlatformLookAndFeelClassName());
            System.err.println("Using default look and feel.");
        }
    }
    
    
}
