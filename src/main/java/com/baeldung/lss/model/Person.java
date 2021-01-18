package com.baeldung.lss.model;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sun.istack.NotNull;

@Embeddable

public class Person {

	@NotEmpty(message = "Name is required")
	@Pattern(regexp = "^[a-zA-Z](([a-zA-Z ])*)$", message = "Your name does not exist")
	private String name;

	@NotEmpty(message = "Phone Number is required.")
	@Pattern(regexp = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", message = "Your phone number should follow the form ANN-NNN-NNNN, where A is between 2 and 9 and N is between 0 and 9.")
	private String phone;

	@NotNull
	@Valid
	private Adress adress;

	public Person(String name, String phone, Adress adress) {
		super();
		this.name = name;
		this.phone = phone;
		this.adress = adress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Person() {
		super();
	}

}
