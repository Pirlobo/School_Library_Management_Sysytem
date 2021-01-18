package com.baeldung.lss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.SubjectName;
import com.baeldung.lss.model.User;

@Service
public interface ICourseService {

	// search classes by subject 
	List<Course> searchCourses(String subject);

	// get all required books of a student
	List<Books> getAllRequiredBooks();

	// get all registered classes of a student
	public List<Course> findRegisteredClasses(List<Integer> regIdClasses);

	// is selected classe available to register
	boolean isAlaivable(Course course);

	// save course
	void save(Course course);

	// set available
	void setAvailable(Integer available, Integer id);

	// set waitist
	void setWailist(Integer wailist, Integer id);

	// get all selected courses can not be registered 
	Set<Course> getFailedRegisteredClasses(User user, List<Course> courses, List<Integer> regIdClasses);

	// get all selected course can be registered
	List<Course> getSuccessfullRegisteredClasses(User user, List<Course> courses, List<Integer> regIdClasses);

	// is course repeated
	boolean isRepeated(Course course);

	// check whether selected classes is/are duplicated
	boolean isDuplicated(List<Integer> regIdClasses);

	List<Course> getFilterdUnDuplicatedCourses(List<Integer> regIdClasses);

	// check whether  the taken course is passed
	boolean isPassed(Course course);

	
	boolean isNotTimeConflicted(Course course, List<Integer> regIdClasses);

	// check whether the selected course is preregquisited
	boolean isPreregquisited(Course course);

	// search classes by subject
	List<Course> findBySubject(String subjectName);

	// check is any selected course conflicted 
	public boolean isCourseConflicted(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes);

	// return a list of courses which are not conflicted to be able to register
	public List<Course> getUnconflictedCourse(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes);

	
	// check whether selected courses is/are already registered ? 
	public Integer checkRegisteredCourses(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes);

	// check at least one class is selected in order to register
	public boolean isAnyCourseSelected(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes);

}

//1 -> getUnconflictedCourses are all registered
	// 2 -> getUnconflictedCourses require pre course
	// 3-> One or more of the selected courses is already registered
	// 4 -> getUnconflictedCourses.size() = 0
