/**
 * 	Author	:	Nana Baah
 * 	Project	:	Java Swing GUI SQL Project
 * 	Date	:	05 May 2015
 * 
 */

package gui;

import java.util.EventObject;

public class FormEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2420190448248634870L;
	private String name;
	private String occupation;
	private int ageCat;
	private String empCat;
	private boolean usCitizen;
	private String taxID;
	private String genderCat;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String name, String occupation, int ageCat, String empCat, boolean usCitizen,
			String taxID, String genderCat) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCat = ageCat;
		this.empCat = empCat;
		this.usCitizen = usCitizen;
		this.taxID = taxID;
		this.genderCat = genderCat;
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

	public int getAgeCat() {
		return ageCat;
	}

	public void setAgeCat(int ageCat) {
		this.ageCat = ageCat;
	}

	public String getEmpCat() {
		return empCat;
	}

	public void setEmpCat(String empCat) {
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

	public String getGenderCat() {
		return genderCat;
	}

	public void setGenderCat(String genderCat) {
		this.genderCat = genderCat;
	}

}
