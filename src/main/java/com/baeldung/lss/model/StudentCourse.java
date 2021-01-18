package com.baeldung.lss.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class StudentCourse {
	@EmbeddedId
	private StudentCourseId userCourseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userName")
	private User user;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@Enumerated(EnumType.ORDINAL)
	private GPA gpa;

	private Integer percentage;

	@Enumerated(EnumType.STRING)
	private IsPassed isPassed;

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	

	public IsPassed getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(IsPassed isPassed) {
		this.isPassed = isPassed;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public GPA getGpa() {
		return gpa;
	}

	public void setGpa(GPA gpa) {
		this.gpa = gpa;
	}

	public StudentCourse() {
		super();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("regId")

	private Course course;

	@Enumerated(EnumType.STRING)
	private StudentCourseStatus userCourseStatus;

	public StudentCourseId getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(StudentCourseId userCourseId) {
		this.userCourseId = userCourseId;
	}

	public StudentCourseStatus getUserCourseStatus() {
		return userCourseStatus;
	}

	public void setUserCourseStatus(StudentCourseStatus userCourseStatus) {
		this.userCourseStatus = userCourseStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public StudentCourse(User user, Course course) {
		super();
		this.user = user;
		this.course = course;
		this.userCourseId = new StudentCourseId(user.getUserName(), course.getRegId());
		this.isPassed = isPassed.TRUE;

	}

}
