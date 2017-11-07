//Author: Christopher Kilian
//CS 356
//Project #2 - Mini-Twitter
package cs356.minitwitter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//The driver for the Mini-Twitter program creates the frame which will hold the admin panel, and then makes it visible to the user.
public class Driver {

    public static void main(String[] args) {

        //This commented out block is used to demonstrate the ability of the program to handle pre-defined tree structure data
        //Uncomment this block to see this in action!
//        AdminControl controller = AdminControl.getInstance();
//        controller.insertComponent(new User("Chris"), controller.getRoot());
//        controller.insertComponent(new User("Susan"), controller.getRoot());
//        controller.insertComponent(new User("Nina"), controller.getRoot());
//        Group ninjas = new Group("Ninjas");
//        controller.insertComponent(ninjas, controller.getRoot());
//        controller.insertComponent(new User("Joe"), ninjas);
//        controller.insertComponent(new User("Steve"), ninjas);
//        Group whales = new Group("Whales");
//        Group superNinjas = new Group("SuperNinjas");
//        Group starfleet = new Group("Starfleet");
//        controller.insertComponent(whales, controller.getRoot());
//        controller.insertComponent(new User("Mary"), controller.getRoot());
//        controller.insertComponent(starfleet, controller.getRoot());
//        controller.insertComponent(superNinjas, ninjas);
//        controller.insertComponent(new User("Roger"), superNinjas);
//        controller.insertComponent(new User("Elizabeth"), superNinjas);
//        controller.insertComponent(new User("Picard"), starfleet);
//        controller.insertComponent(new User("Data"), starfleet);
//        controller.insertComponent(new User("Snuggles"), whales);
        
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initLookAndFeel();
                JFrame mainFrame = new JFrame();
                JPanel mainPanel = new AdminPanel();
                mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                mainFrame.setSize(new java.awt.Dimension(800, 500));
                mainFrame.add(mainPanel);
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }

    //initLookAndFeel sets up the "look and feel" of the program (setting to Cross Platform)
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
