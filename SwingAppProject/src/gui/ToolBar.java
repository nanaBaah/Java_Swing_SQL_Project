/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 May 2015
 * 
 */

package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2991952024483367277L;
	private JButton saveButton;
	private JButton refreshButton;
	private ToolBarListener toolBarListener;

	public ToolBar() {

		saveButton = new JButton("Save");
		refreshButton = new JButton("Refresh");

		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(saveButton);
		add(refreshButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == refreshButton) {
			if (toolBarListener != null) {
				toolBarListener.refreshEventOccured();
			}
		} else if (clicked == saveButton) {
			if (toolBarListener != null) {
				toolBarListener.saveEventOccured();
			}
		}
	}

	public void setToolBarListener(ToolBarListener listener) {
		this.toolBarListener = listener;
	}
}
