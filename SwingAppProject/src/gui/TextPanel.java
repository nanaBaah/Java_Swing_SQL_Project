/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 May 2015
 * 
 */

package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6187902708960123682L;
	private JTextArea textArea;

	public TextPanel() {
		textArea = new JTextArea();

		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	public void appendText(String string) {
		textArea.append(string);
	}
}
