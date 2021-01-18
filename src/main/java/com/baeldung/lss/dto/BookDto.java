package com.baeldung.lss.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Books;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

public class BookDto {
	@Id
	// @GeneratedValue
	private Integer id;

	@NotNull
	private String ISBN;

	@NotNull
	private String title;

	@NotNull
	private String subject;

	@NotNull
	private String publisher;

	@NotNull
	private String language;

	@NotNull
	private Integer numOfPages;

	private List<BookItems> bookItems = new ArrayList<BookItems>();

	private List<Authors> authorList = new ArrayList<Authors>();
	
	

	
	public List<BookItems> getBookItems() {
		return bookItems;
	}

	public void setBookItems(List<BookItems> bookItems) {
		this.bookItems = bookItems;
	}

	public List<Authors> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Authors> authorList) {
		this.authorList = authorList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getNumOfPages() {
		return numOfPages;
	}

	public BookDto(Books books) {
		super();
		this.id = books.getId();
		this.ISBN = books.getISBN();
		this.language = books.getLanguage();
		this.numOfPages = books.getNumOfPages();
		this.publisher = books.getPublisher();
		this.title = books.getTitle();
		this.authorList = books.getAuthorList();
	}

	public void setNumOfPages(Integer numOfPages) {
		this.numOfPages = numOfPages;
	}

}