package com.baeldung.lss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StudentCourseId implements Serializable {
	
	private String userName;
	
	
	private Integer regId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRegId() {
		return regId;
	}

	public void setRegId(Integer regId) {
		this.regId = regId;
	}

	public StudentCourseId(String userName, Integer regId) {
		super();
		this.userName = userName;
		this.regId = regId;
	}

	public StudentCourseId() {
		super();
	}
	
	
	
}
