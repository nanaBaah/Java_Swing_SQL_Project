
/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	10 August 2015
 * 
 */

import java.sql.SQLException;

import model.AgeEnumCategory;
import model.Database;
import model.EmploymentCategory;
import model.GenderCategory;
import model.Person;

public class TestDatabase {

	public static void main(String[] args) {
		System.out.println("DataBase is running");

		Database db = new Database();
		try {
			db.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.addPerson(new Person("Alisa", "Student", AgeEnumCategory.child, EmploymentCategory.employed, true, "02145",
				GenderCategory.male));

		db.addPerson(new Person("Sue", "Engineer", AgeEnumCategory.adult, EmploymentCategory.unemployed, false, null,
				GenderCategory.female));

		try {
			db.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			db.load();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db.disconnect();
	}

}
