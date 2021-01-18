package com.baeldung.lss.book;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.baeldung.lss.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import org.hibernate.annotations.Cache;

@Entity
@Table(name = "book")
public class Books {

	@Id
	//@GeneratedValue
	private Integer id;

	@NotNull
	private String ISBN;
	@NotNull
	private String title;

	@NotNull
	private String publisher;

	@NotNull
	private String language;

	@NotNull
	private Integer numOfPages;
	
	@ManyToOne
	private Course course;

	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BookItems> bookItems = new ArrayList<BookItems>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "books_authors",
	joinColumns = { @JoinColumn(name = "fk_book") },
	inverseJoinColumns = { @JoinColumn(name = "fk_author") })
	private List<Authors> authorList = new ArrayList<Authors>();

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

	public void setNumOfPages(Integer numOfPages) {
		this.numOfPages = numOfPages;
	}

	public Books() {
		super();
	}
	
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Books(Integer id, String iSBN, String title, String publisher, String language,
			Integer numOfPages) {
		super();
		this.id = id;
		ISBN = iSBN;
		this.title = title;
		this.publisher = publisher;
		this.language = language;
		this.numOfPages = numOfPages;
		
	}
	 public void addAuthor(Authors author) {
	        authorList.add(author);
	       author.getBookList().add(this);
	    }
	public Books(Integer id, String iSBN, String title, String publisher, String language,
			Integer numOfPages, List<Authors> authorList) {
		super();
		this.id = id;
		ISBN = iSBN;
		this.title = title;
		
		this.publisher = publisher;
		this.language = language;
		this.numOfPages = numOfPages;
		this.authorList = authorList;
	}

	public List<Authors> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Authors> authorList) {
		this.authorList = authorList;
	}

	public List<BookItems> getBookItems() {
		return this.bookItems;
	}

	public void setBookItems(List<BookItems> bookItems) {
		this.bookItems = bookItems;
	}

	public void addBookItem(BookItems bookItem) {
		bookItems.add(bookItem);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
