/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	08 May 2015
 * 
 */

package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Person;

public class PersonTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8284186505817880272L;

	private List<Person> db;

	private String[] colName = { "ID", "Name", "Occupation", "Age", "Employment", "US Citizen", "Tax", "Gender" };

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Person person = db.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCat();
		case 4:
			return person.getEmpCat();
		case 5:
			return person.isUsCitizen();
		case 6:
			return person.getTaxID();
		case 7:
			return person.getGenderCat();

		}

		return null;
	}

}
