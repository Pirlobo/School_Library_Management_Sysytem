package com.baeldung.lss.service;

import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Orders;
import com.baeldung.lss.dto.BookItemDto;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.PasswordResetToken;
import com.baeldung.lss.model.UpdatedStudent;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.VerificationToken;
import com.baeldung.lss.validation.EmailExistsException;
import com.baeldung.lss.validation.PasswordMatcherException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

	// sign up for an account
	User registerNewUser(User user) throws EmailExistsException;

	// find student by email
	User findUserByEmail(String email);

	// get verification token of a student
	VerificationToken getVerificationToken(final String token);

	
	// persist a registed student 
	void saveRegisteredUser(User user);

	// change password of a student
	void changeUserPassword(final User user, final String password);

	// create verification token for a student
	void createVerificationTokenForUser(User user, String token);

	// create password reset token for a student
	void createPasswordResetTokenForUser(User user, String token);

	// get reset password token of a studnet
	PasswordResetToken getPasswordResetToken(final String token);

	// reset password of a student
	User updateExistingUser(UpdatedStudent updatedUser) throws EmailExistsException, PasswordMatcherException;

	// register for classes
	void registerForClassess(User user, List<Course> courses);

	// get current logged student
	User getCurrentLoggedUser();
	
	// get all taken or registered classes
	List<Course> getYourClasses();
	
	// drop classes
	void dropClasses(List<Integer> regIdClasses);
	
	
	// place order from buying or renting Book Items
	Orders placeOrder(List<BookItemDto> bookItemDto);

	void test(String test);
	

	//List<BookItems> findBookItemsById(List<Integer> barcodes);

}
