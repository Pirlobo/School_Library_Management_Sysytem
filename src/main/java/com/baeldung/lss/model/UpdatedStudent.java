package com.baeldung.lss.model;

import javax.validation.constraints.Email;

import com.sun.istack.NotNull;

public class UpdatedStudent {

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String passwordConfirmation;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public UpdatedStudent(@Email String email, String password, String passwordConfirmation) {
		super();
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public UpdatedStudent() {
		super();

	}

}
