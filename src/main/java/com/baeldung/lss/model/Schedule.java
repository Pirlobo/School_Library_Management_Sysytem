//package com.baeldung.lss.model;
//
//import java.util.ArrayList;
//
//import java.util.List;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import org.apache.catalina.LifecycleListener;
//
//
//
//@Entity
//@Table(name = "schedule")
//public class Schedule {
//
//	@Id
//	private Integer id;
//	
//	
//	
//	
//	@Enumerated(EnumType.STRING)
//	private Day date;
//	
//	
//	@OneToMany(mappedBy = "schedule")
//	private List<Course> courses = new ArrayList<Course>();
//	
//	private String time;
//
//	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	
//
//	
//	public Schedule(Integer id, Day date, String time) {
//		super();
//		this.id = id;
//		this.date = date;
//		this.time = time;
//	}
//
//	public Schedule(Integer id, Day date, List<Course> courses, String time) {
//		super();
//		this.id = id;
//		this.date = date;
//		this.courses = courses;
//		this.time = time;
//	}
//
//	public Day getDate() {
//		return date;
//	}
//
//	public void setDate(Day date) {
//		this.date = date;
//	}
//
//	public String getTime() {
//		return time;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
//	
//	
//
//	public List<Course> getCourses() {
//		return courses;
//	}
//
//	public void setCourses(List<Course> courses) {
//		this.courses = courses;
//	}
//
//	public Schedule() {
//		super();
//	}
//	
//	
//	
//}
