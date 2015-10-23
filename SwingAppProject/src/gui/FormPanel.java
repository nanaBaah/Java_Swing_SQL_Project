/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	06 May 2015
 * 
 */

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.sun.glass.events.KeyEvent;

public class FormPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6300406699184003566L;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel occupationLabel;
	private JTextField occupationField;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox usCitizenCheck;
	private JLabel taxLabel;
	private JTextField taxField;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private JButton okBtn;

	private FormListener formListener;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		Border inner = BorderFactory.createTitledBorder("Add Person");
		Border outer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outer, inner));

		nameLabel = new JLabel("Name: ");
		nameField = new JTextField(10);
		occupationLabel = new JLabel("Occupation: ");
		occupationField = new JTextField(10);
		ageList = new JList<AgeCategory>();
		empCombo = new JComboBox<String>();
		usCitizenCheck = new JCheckBox();
		taxLabel = new JLabel("Tax ID: ");
		taxField = new JTextField(10);
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		genderGroup = new ButtonGroup();
		okBtn = new JButton("OK");

		/* Mnems */
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);

		/* Gender */
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		maleRadio.setSelected(true);

		/* US Check */
		usCitizenCheck.setSelected(false);
		taxLabel.setVisible(false);
		taxField.setVisible(false);

		usCitizenCheck.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean isTicked = usCitizenCheck.isSelected();

				taxLabel.setVisible(isTicked);
				taxField.setVisible(isTicked);
			}
		});

		/* Employment Cat */
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");

		empCombo.setModel(empModel);
		empCombo.setEditable(true);
		empCombo.setSelectedIndex(0);

		/* age Cat */
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<AgeCategory>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 or 65"));
		ageModel.addElement(new AgeCategory(2, "65 or more"));

		ageList.setModel(ageModel);
		ageList.setSelectedIndex(1);
		ageList.setPreferredSize(new Dimension(110, 75));
		ageList.setBorder(BorderFactory.createEtchedBorder());

		okBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = ageList.getSelectedValue();
				String empCat = empCombo.getSelectedItem().toString();
				boolean isCitizen = usCitizenCheck.isSelected();
				String taxID = taxField.getText();
				String genderCat = genderGroup.getSelection().getActionCommand();

				FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId(), empCat, isCitizen, taxID,
						genderCat);

				if (formListener != null) {
					formListener.formEventOccurred(ev);
				}
			}
		});

		LayoutComponent();
	}

	public void LayoutComponent() {
		setLayout(new GridBagLayout());

		Insets insetField = new Insets(0, 0, 0, 0);
		Insets insetLabel = new Insets(0, 0, 0, 5);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;

		/* Name row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = insetLabel;
		add(nameLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = insetField;
		add(nameField, gc);

		/* Occupation row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(occupationLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(occupationField, gc);

		/* age List row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(new JLabel("Age Category: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(ageList, gc);

		/* employment Combo row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(new JLabel("Employment: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(empCombo, gc);

		/* US Citizen row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(new JLabel("US Citizenship: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(usCitizenCheck, gc);

		/* Tax ID row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(taxLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(taxField, gc);

		/* Radio row */
		gc.weightx = 1.0;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy++;

		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = insetLabel;
		add(new JLabel("Gender: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(maleRadio, gc);

		gc.gridy++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(femaleRadio, gc);

		/* OK */
		gc.weightx = 1.0;
		gc.weighty = 2.0;

		gc.gridx = 1;
		gc.gridy++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = insetField;
		add(okBtn, gc);
	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}
}
