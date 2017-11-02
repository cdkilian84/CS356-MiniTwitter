/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs356.minitwitter;

/**
 *
 * @author Chris
 */
public class AdminControl {
    
    private static AdminControl theAdmin;
	
	private AdminControl() { 
        
        }
	
	public static AdminControl getInstance() {
		if (theAdmin == null) {
                    synchronized(AdminControl.class){
                        if(theAdmin == null){
                            theAdmin = new AdminControl();
                        }
                    }
		}
		return theAdmin;
	}

}
