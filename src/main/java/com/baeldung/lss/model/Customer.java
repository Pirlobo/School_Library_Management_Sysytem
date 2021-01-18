//package com.baeldung.lss.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "customer")
//public class Customer {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private Integer id;
//
//	@OneToOne
//	private Student user;
//
//	public Student getUser() {
//		return user;
//	}
//
//	public void setUser(Student user) {
//		this.user = user;
//	}
//
//	public Customer(Integer id, Student user) {
//		super();
//		this.id = id;
//		this.user = user;
//	}
//
//	public Customer() {
//		super();
//	}
//
//}
