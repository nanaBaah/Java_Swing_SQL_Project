/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	12 May 2015
 * 
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -984942908219185190L;
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField userField;
	private JPasswordField passField;
	private PrefsListener prefsListener;

	public PrefDialog(JFrame parent, String string) {
		super(parent, string, false);

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);

		userField = new JTextField(10);
		passField = new JPasswordField(10);
		passField.setEchoChar('*');

		layoutControls();

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Integer port = (Integer) portSpinner.getValue();

				String user = userField.getText();
				char[] password = passField.getPassword();

				if (prefsListener != null) {
					prefsListener.preferencesSet(user, new String(password), port);
				}
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		setSize(320, 230);
		setMinimumSize(new Dimension(300, 200));
		setLocationRelativeTo(parent);
	}

	private void layoutControls() {

		JPanel controlPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		int space = 10;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

		controlPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

		// controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		controlPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridy = 0;
		Insets rightPadding = new Insets(0, 0, 0, space);
		Insets noPadding = new Insets(0, 0, 0, 0);

		/* First row */

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Username: "), gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(userField, gc);

		/* Next row */
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Password: "), gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(passField, gc);

		/* Next row */
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Port: "), gc);
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(portSpinner, gc);

		/* Button Panel */
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		/* make buttons equal sizes */
		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);

		// Add sub panels to dialog
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void setDefaults(String user, String password, int port) {
		userField.setText(user);
		passField.setText(password);
		portSpinner.setValue(port);
	}

	public void setPrefsListener(PrefsListener prefsListener) {
		this.prefsListener = prefsListener;
	}
}
