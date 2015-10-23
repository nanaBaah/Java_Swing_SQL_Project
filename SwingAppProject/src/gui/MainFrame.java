/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 May 2015
 * 
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1353206411140456031L;
	private ToolBar toolBar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PrefDialog preDialog;
	private Preferences prefs;

	public MainFrame() {
		super("Hello world");
		/*
		 * try { for (LookAndFeelInfo info :
		 * UIManager.getInstalledLookAndFeels()) { if
		 * ("Nimbus".equals(info.getName())) {
		 * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
		 * (Exception e) { }
		 */
		setJMenuBar(menuBar());

		toolBar = new ToolBar();
		formPanel = new FormPanel();
		controller = new Controller();
		tablePanel = new TablePanel();
		preDialog = new PrefDialog(MainFrame.this, "Preferences");

		prefs = Preferences.userNodeForPackage(this.getClass());

		tablePanel.setData(controller.getData());

		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Person Database (*.per)", "per");
		fileChooser.setFileFilter(filter);

		tablePanel.setPersonTableListener(new PersonTableListener() {

			public void removeRow(int index) {
				controller.removeRow(index);
			}
		});

		preDialog.setPrefsListener(new PrefsListener() {

			public void preferencesSet(String user, String password, int port) {
				// System.out.println(user + password + port);
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
			}
		});

		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);

		preDialog.setDefaults(user, password, port);

		toolBar.setToolBarListener(new ToolBarListener() {

			public void saveEventOccured() {
				connect();
				// System.out.println("save");

				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database", "Database Save Error",
							JOptionPane.ERROR_MESSAGE);

				}
			}

			public void refreshEventOccured() {
				connect();
				// System.out.println("refresh");
				try {
					controller.load();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Cannot load from database", "Database Load Error",
							JOptionPane.ERROR_MESSAGE);
				}

				tablePanel.refresh();
			}
		});

		formPanel.setFormListener(new FormListener() {

			public void formEventOccurred(FormEvent ev) {
				controller.addPerson(ev);
				tablePanel.refresh();
			}
		});

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				// System.out.println("window closing");
				controller.disconnect();
				dispose();
				System.gc();
			}

		});

		setLayout(new BorderLayout());

		add(toolBar, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		setSize(700, 600);
		setVisible(true);
	}

	private void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database", "Database Connection Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportItem = new JMenuItem("Export Data...");
		JMenuItem importItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		JMenu windowMenu = new JMenu("Window");
		JMenuItem prefDialogItem = new JMenuItem("Preference...");
		JMenuItem showMenu = new JMenu("Show");
		JCheckBoxMenuItem personItem = new JCheckBoxMenuItem("Person Form");

		fileMenu.add(exportItem);
		fileMenu.add(importItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		showMenu.add(personItem);
		windowMenu.add(prefDialogItem);
		windowMenu.add(showMenu);

		/* Accelerators and Mnemonics */
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		windowMenu.setMnemonic(KeyEvent.VK_W);
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
		prefDialogItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));

		exportItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showConfirmDialog(MainFrame.this, "File could not be exported", "Export Error",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});

		prefDialogItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				preDialog.setVisible(true);
			}
		});

		importItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showConfirmDialog(MainFrame.this, "Could not import file", "Import Error",
								JOptionPane.OK_OPTION);
					}
				}
			}
		});

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));

		personItem.setSelected(true);
		personItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem check = (JCheckBoxMenuItem) e.getSource();
				formPanel.setVisible(check.isSelected());
			}
		});

		exitItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int feedback = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit this application?", "Exit Confirmation",
						JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);

				if (feedback == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();

					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		return menuBar;
	}
}
