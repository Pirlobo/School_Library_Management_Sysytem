package com.baeldung.lss.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Indexed;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.book.Orders;
import com.baeldung.lss.config.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;



@Entity
@Table(name = "student")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Cacheable
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String>{
	
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	@Column(unique = true, name = "id")
	@NotEmpty(message = "Username is required")
	@Id
	private String userName;

//		@OneToOne
//	    private Customer customer;

//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "users_courses",
//	joinColumns = { @JoinColumn(name = "fk_user") },
//	inverseJoinColumns = { @JoinColumn(name = "fk_course") })
	 @OneToMany(
		        mappedBy = "user",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true,
		        fetch = FetchType.LAZY
		    )
	 @JsonIgnore
	private List<StudentCourse> courses = new ArrayList<StudentCourse>();
	
	@Transient
	private String passwordConfirmation;
	
	 @OneToMany(
		        mappedBy = "user",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true,
		        fetch = FetchType.LAZY
		    )
	 
	private List<Orders> orders = new ArrayList<Orders>();

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	public void addCourse(Course course) {
		 StudentCourse userCourse = new StudentCourse(this, course);
	       courses.add(userCourse);
	        
    }
	
	@Column 
	private Boolean enabled;
	
	@NotEmpty(message = "Password is mandatory")
	private String password;

	@NotNull
	@Valid
	@CreatedDate
	private Person person;

	@Email
	@NotEmpty(message = "Email is required.")
	private String email;
	
	private Calendar created = Calendar.getInstance();
	
	private String newPassword;
	
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public User(AccountStatus accountStatus, @NotEmpty(message = "Username is required") @NotEmpty String userName,
			List<StudentCourse> courses, String passwordConfirmation, Boolean enabled,
			@NotEmpty(message = "Password is mandatory") String password, @Valid Person person,
			@Email @NotEmpty(message = "Email is required.") String email, Calendar created, String newPassword,
			Set<Roles> roles) {
		super();
		this.accountStatus = accountStatus;
		this.userName = userName;
		this.courses = courses;
		this.passwordConfirmation = passwordConfirmation;
		this.enabled = enabled;
		this.password = password;
		this.person = person;
		this.email = email;
		this.created = created;
		this.newPassword = newPassword;
		this.roles = roles;
	}

	public List<StudentCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<StudentCourse> courses) {
		this.courses = courses;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	    @JoinTable(
//	    		  name = "users_roles", 
//	    		  joinColumns = @JoinColumn(name = "user_name"), 
//	    		  inverseJoinColumns = @JoinColumn(name = "roles_id"))
	@JsonIgnore
	private Set<Roles> roles = new HashSet<>();

	public User() {
		super();
		this.enabled = false;
		this.accountStatus = AccountStatus.Pending;
	}
	
	
}
