/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 August 2015
 * 
 */

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private LinkedList<Person> people;
	private Connection conn;

	public Database() {

		people = new LinkedList<Person>();
	}

	public void connect() throws Exception {
		if (conn != null) {
			return;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/swingtest";
			conn = DriverManager.getConnection(url, "root", "test");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}

		// System.out.println("Connected: " + conn);

	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Cannot close connection");
			}
		}
	}

	public void save() throws SQLException {
		String checkSql = "Select count(*) as count from people where id=?";
		PreparedStatement checkStmt = conn.prepareStatement(checkSql);

		String insertSql = "insert into people (id, name, age, employment_status, tax_id, us_citizen, gender, occupation) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = conn.prepareStatement(insertSql);

		String updateSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatement = conn.prepareStatement(updateSql);

		for (Person person : people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeEnumCategory age = person.getAgeCat();
			EmploymentCategory emp = person.getEmpCat();
			String tax = person.getTaxID();
			boolean isUS = person.isUsCitizen();
			GenderCategory gender = person.getGenderCat();

			checkStmt.setInt(1, id);

			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();

			int count = checkResult.getInt(1);

			if (count == 0) {
				// System.out.println("Inserting person with ID " + id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, age.name());
				insertStatement.setString(col++, emp.name());
				insertStatement.setString(col++, tax);
				insertStatement.setBoolean(col++, isUS);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);

				insertStatement.executeUpdate();

			} else {
				// System.out.println("Updating person with ID " + id);

				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setString(col++, age.name());
				updateStatement.setString(col++, emp.name());
				updateStatement.setString(col++, tax);
				updateStatement.setBoolean(col++, isUS);
				updateStatement.setString(col++, gender.name());
				updateStatement.setString(col++, occupation);
				updateStatement.setInt(col++, id);

				updateStatement.executeUpdate();

			}

			// System.out.println("Count for the person with ID: " + id);
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}

	public void load() throws SQLException {
		people.clear();

		String sql = "select id, name, age, employment_status, tax_id, us_citizen, gender, occupation from people order by name";
		Statement selectStatement = conn.createStatement();

		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {
			int id = results.getInt("id");
			// System.out.println(id);
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String tax = results.getString("tax_id");
			boolean isUs = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occupation = results.getString("occupation");

			people.add(new Person(id, name, occupation, AgeEnumCategory.valueOf(age), EmploymentCategory.valueOf(emp),
					isUs, tax, GenderCategory.valueOf(gender)));

		}

		results.close();
		selectStatement.close();
	}

	public void removeRow(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public void saveToFile(File file) throws IOException {

		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);
		oos.writeObject(persons);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ois.close();
	}
}