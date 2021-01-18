package com.baeldung.lss.model;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable

public class Adress {
	@NotEmpty(message = "Street Adress is required")
	private String streetAdress;
	@NotEmpty(message = "City is required")
	private String city;
	@NotEmpty(message = "State is required")
	private String state;
	@NotEmpty(message = "Zipcode is required")
	private String zipcode;
	@NotEmpty(message = "Country is required")
	private String country; 
	
	public Adress(String streetAdress, String city, String state, String zipcode, String country) {
		super();
		this.streetAdress = streetAdress;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
	}
	public String getStreetAdress() {
		return streetAdress;
	}
	public void setStreetAdress(String streetAdress) {
		this.streetAdress = streetAdress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Adress() {
		super();
	}
	
	
}

