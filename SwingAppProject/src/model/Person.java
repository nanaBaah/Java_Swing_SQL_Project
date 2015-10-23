/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	01 August 2015
 * 
 */

package model;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6581360630006198277L;

	private static int counter = 1;

	private int id;
	private String name;
	private String occupation;
	private AgeEnumCategory ageCat;
	private EmploymentCategory empCat;
	private boolean usCitizen;
	private String taxID;
	private GenderCategory genderCat;

	public Person(int id, String name, String occupation, AgeEnumCategory ageCat, EmploymentCategory empCat,
			boolean usCitizen, String taxID, GenderCategory genderCat) {

		this(name, occupation, ageCat, empCat, usCitizen, taxID, genderCat);

		this.id = id;
	}

	public Person(String name, String occupation, AgeEnumCategory ageCat, EmploymentCategory empCat, boolean usCitizen,
			String taxID, GenderCategory genderCat) {

		this.name = name;
		this.occupation = occupation;
		this.ageCat = ageCat;
		this.empCat = empCat;
		this.usCitizen = usCitizen;
		this.taxID = taxID;
		this.genderCat = genderCat;

		this.id = counter;
		counter++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public AgeEnumCategory getAgeCat() {
		return ageCat;
	}

	public void setAgeCat(AgeEnumCategory ageCat) {
		this.ageCat = ageCat;
	}

	public EmploymentCategory getEmpCat() {
		return empCat;
	}

	public void setEmpCat(EmploymentCategory empCat) {
		this.empCat = empCat;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(boolean usCitizen) {
		this.usCitizen = usCitizen;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public GenderCategory getGenderCat() {
		return genderCat;
	}

	public void setGenderCat(GenderCategory genderCat) {
		this.genderCat = genderCat;
	}

	public String toString() {
		return id + " : " + name;
	}
}
