package com.baeldung.lss.book;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.subst.Token.Type;

@Entity
@Table(name = "reservationOrder")
public class RO {

	// By placing @Id on field which implicitly set the AccessType (AccessType.Field) (Best Practice)
	//meaning that getter setter does not considered as parts of persistent state, (Using Reflection API instead)
	// omit getter or setter methods that should not be called, and easy to add more business code
	// no need to mark @transient (not a part of persistent state)
	
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JsonIgnore
	private Orders orders;

	@ManyToOne
	private BookItems bookItems;

	private Date dueDate;

	private Date borrowDate;

	
	private Date returnDate;

	public BookItems getBookItems() {
		return bookItems;
	}

	public void setBookItems(BookItems bookItems) {
		this.bookItems = bookItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RO() {
		super();
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public RO(Date dueDate, Date borrowDate, Date returnDate) {
		super();
		this.dueDate = dueDate;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

}
