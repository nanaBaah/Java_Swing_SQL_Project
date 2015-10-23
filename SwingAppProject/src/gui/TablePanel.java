/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	11 May 2015
 * 
 */

package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Person;

public class TablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671930841168138069L;
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popup;
	private PersonTableListener personTableListener;

	public TablePanel() {

		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();

		JMenuItem deleteItem = new JMenuItem("Delete row");
		popup.add(deleteItem);

		table.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});

		deleteItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				if (personTableListener != null) {
					personTableListener.removeRow(row);

					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<Person> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}

	public void setPersonTableListener(PersonTableListener personTableListener) {
		this.personTableListener = personTableListener;
	}

}
