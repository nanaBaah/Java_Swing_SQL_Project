/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 May 2015
 * 
 */

package gui;

import javax.swing.SwingUtilities;


public class App {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new MainFrame();
			}
		});
	}

}
