package com.baeldung.lss.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Orders;
import com.baeldung.lss.book.PO;
import com.baeldung.lss.book.RO;
//import com.baeldung.lss.config.Auditable;
import com.baeldung.lss.dto.BookItemDto;
import com.baeldung.lss.model.AccountStatus;
import com.baeldung.lss.model.Calendar;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.IsPassed;
import com.baeldung.lss.model.PasswordResetToken;
import com.baeldung.lss.model.Roles;
import com.baeldung.lss.model.UpdatedStudent;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseId;
import com.baeldung.lss.model.StudentCourseStatus;
import com.baeldung.lss.model.VerificationToken;
import com.baeldung.lss.persistence.CalendarRepository;
import com.baeldung.lss.persistence.CourseRepository;
import com.baeldung.lss.persistence.PasswordResetTokenRepository;
import com.baeldung.lss.persistence.UserCourseRepository;
import com.baeldung.lss.persistence.UserRepository;
import com.baeldung.lss.persistence.VerificationTokenRepository;
import com.baeldung.lss.validation.EmailExistsException;
import com.baeldung.lss.validation.PasswordMatcherException;

@Service
@Transactional

public class UserService implements IUserService {
	
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private BookItemService bookItemService;
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserCourseService userCourseService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCourseRepository userCourseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public VerificationToken getVerificationToken(final String token) {
		return verificationTokenRepository.findByToken(token);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public User registerNewUser(final User user) throws EmailExistsException {
		if (emailExist(user.getEmail())) {
			throw new EmailExistsException("There is an account with that email address: " + user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Roles> rSet1 = new HashSet<Roles>();
		Roles role1 = new Roles(1, "User");
		
		rSet1.add(role1);
		
		user.setRoles(rSet1);

		return userRepository.save(user);
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveRegisteredUser(final User user) {
		userRepository.save(user);
	}

	private boolean emailExist(final String email) {
		final User user = userRepository.findByEmail(email);
		return user != null;
	}

	@Override
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepository.save(myToken);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);

	}

	@Override
	public void changeUserPassword(final User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	@Override
	public User updateExistingUser(UpdatedStudent updatedUser) throws EmailExistsException, PasswordMatcherException {
		final User emailOwner = userRepository.findByEmail(updatedUser.getEmail());

		if (emailOwner != null) {

			if (updatedUser.getPassword().equals(updatedUser.getPasswordConfirmation())) {
				// emailOwner.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
				emailOwner.setAccountStatus(AccountStatus.ResetPasswordPending);
				emailOwner.setNewPassword(passwordEncoder.encode(updatedUser.getPassword()));

				userRepository.save(emailOwner);

				return emailOwner;

			} else {

				throw new PasswordMatcherException("Password Confimation does not match");
			}
		} else {

			throw new EmailExistsException("Email not available.");
		}

	}
	
	@Override
	public void registerForClassess(User user, List<Course> courses) {
		List<Integer> regIdClasses = new ArrayList<Integer>();
		List<StudentCourse> registeredCourses = user.getCourses();
		registeredCourses.forEach(e -> {
			regIdClasses.add(e.getCourse().getRegId());
		});
		for (int i = 0; i < courses.size(); i++) {
			if (courseService.isAlaivable(courses.get(i))) {
				StudentCourse userCourse = new StudentCourse(user, courses.get(i));
				userCourse.setUserCourseStatus(StudentCourseStatus.Successfull);
				courseService.setAvailable(courses.get(i).getAvailable() - 1, courses.get(i).getRegId());
				userCourseRepository.save(userCourse);

			} else {
				StudentCourse userCourse = new StudentCourse(user, courses.get(i));
				userCourse.setUserCourseStatus(StudentCourseStatus.Wailisted);
				courseService.setWailist(courses.get(i).getWaitlist() + 1, courses.get(i).getRegId());
				userCourseRepository.save(userCourse);
				

			}
		}
	

	}

	@Override
	public User getCurrentLoggedUser() {
		String userName;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
			User user = userRepository.findByEmail(userName);
			return user;
		}
		return null;
	}

	@Override
	// @Cacheable(value = "userCache")
	public List<Course> getYourClasses() {
		User user = userService.getCurrentLoggedUser();
		List<Course> yourClasses = new ArrayList<Course>();
		List<StudentCourse> userCourses = user.getCourses();
		userCourses.forEach(e -> {
			if (e.getUserCourseStatus().equals(StudentCourseStatus.Successfull)) {
				yourClasses.add(e.getCourse());
			}

		});
		return yourClasses;

	}

	@Override
	public void dropClasses(List<Integer> regIdClasses) {
		List<Course> droppedClasses = courseService.findRegisteredClasses(regIdClasses);
		User user = userService.getCurrentLoggedUser();
		List<StudentCourseId> userCourseIds = new ArrayList<StudentCourseId>();
		droppedClasses.forEach(e -> {
			StudentCourseId userCourseId = new StudentCourseId(user.getUserName(), e.getRegId());
			userCourseIds.add(userCourseId);
		});
		List<StudentCourse> userCourses = new ArrayList<StudentCourse>();

		userCourseIds.forEach(e -> {
			StudentCourse userCourse = userCourseService.findById(e);
			userCourses.add(userCourse);
		});
		userCourses.forEach(e -> {
			courseService.setAvailable(e.getCourse().getAvailable() + 1, e.getCourse().getRegId());
			userCourseService.delete(e);

		});

	}

//	@Override
//	public List<BookItems> findBookItemsById(List<Integer> barcodes ) {
//		List<BookItems> bookItems = new ArrayList<BookItems>();
//		
//		barcodes.forEach(e ->{
//			bookItems.add(bookItemService.findById(e));
//		}); 
//	return bookItems;
//		
//		
//	}

	@Override
	public Orders placeOrder(List<BookItemDto> bookItemDto) {
		List<BookItems> setBook1 = new ArrayList<BookItems>();
		List<BookItems> setBook2 = new ArrayList<BookItems>();
		
		BookItems rentedBookItem = null;
		BookItems soldBookItem = null ;
		
		double total = 0.0;
		
		Orders order = new Orders(userService.getCurrentLoggedUser(), total);
		for (int i = 0; i < bookItemDto.size(); i++) {
			if (bookItemDto.get(i).isRented() == true) {
				RO ro = new RO(calendarService.getDefaultDueDate(), calendarService.getCurrentTime(), null);
				order.addRO(ro);
				rentedBookItem = bookItemService.findById(bookItemDto.get(i).getBarcode()); 
				rentedBookItem.addRO(ro);
				setBook1.add(rentedBookItem);
				total += rentedBookItem.getRentalPrice();
			}
			if (bookItemDto.get(i).isSold() == true) {
				PO po = new PO(bookItemDto.get(i).getSellQuantity());
				order.addPO(po);
				soldBookItem = bookItemService.findById(bookItemDto.get(i).getBarcode());
				soldBookItem.addPO(po);
				setBook2.add(soldBookItem);
				total += soldBookItem.getRentalPrice()*(bookItemDto.get(i).getSellQuantity());
			
			}
			    
		}
		order.setTotalPrice(total);
		
		orderService.save(order);
		bookItemService.saveAll(setBook1);
		bookItemService.saveAll(setBook2);
		return order;
	}
	
	@ServiceActivator(inputChannel = "registrationRequest")
	@Override
	public void test(String test) {
		System.out.println(test);
	}
}
