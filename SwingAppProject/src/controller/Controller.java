/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	09 May 2015
 * 
 */

package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AgeEnumCategory;
import model.Database;
import model.EmploymentCategory;
import model.GenderCategory;
import model.Person;

public class Controller {

	private Database db = new Database();

	public List<Person> getData() {
		return db.getPeople();
	}

	public void addPerson(FormEvent ev) {
		String name = ev.getName();
		String occupation = ev.getOccupation();
		int age = ev.getAgeCat();
		String gender = ev.getGenderCat();
		String emp = ev.getEmpCat();
		String tax = ev.getTaxID();
		boolean usCitizen = ev.isUsCitizen();

		AgeEnumCategory ageCat = null;
		switch (age) {
		case 0:
			ageCat = AgeEnumCategory.child;
			break;
		case 1:
			ageCat = AgeEnumCategory.adult;
			break;
		case 2:
			ageCat = AgeEnumCategory.senior;
			break;
		}

		GenderCategory genderCat = null;
		switch (gender) {
		case "male":
			genderCat = GenderCategory.male;
			break;
		case "female":
			genderCat = GenderCategory.female;
			break;
		}

		EmploymentCategory empCat = null;
		switch (emp) {
		case "employed":
			empCat = EmploymentCategory.employed;
			break;
		case "unemployed":
			empCat = EmploymentCategory.unemployed;
			break;
		case "self-employed":
			empCat = EmploymentCategory.selfEmployed;
			break;
		default:
			empCat = EmploymentCategory.other;
		}

		Person person = new Person(name, occupation, ageCat, empCat, usCitizen, tax, genderCat);

		db.addPerson(person);
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}

	public void removeRow(int index) {
		db.removeRow(index);
	}

	public void disconnect() {
		db.disconnect();
	}

	public void load() throws SQLException {
		db.load();
	}

	public void connect() throws Exception {
		db.connect();
	}

	public void save() throws SQLException {
		db.save();
	}
}
