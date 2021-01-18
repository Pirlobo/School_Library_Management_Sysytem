package com.baeldung.lss.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IStandaloneElementTag;

import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.IsPassed;
import com.baeldung.lss.model.SubjectCode;
import com.baeldung.lss.model.SubjectName;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseStatus;
import com.baeldung.lss.persistence.BookRepository;
import com.baeldung.lss.persistence.CourseRepository;

import antlr.debug.NewLineEvent;

@Service
public class CourseService implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserService userService;

	@Autowired
	CourseService courseService;

	@Override
	public List<Course> searchCourses(String title) {
		return courseRepository.findByTitle(title);

	}

	@Override
	// @Cacheable(value = "courseCache")
	public List<Books> getAllRequiredBooks() {
		List<Integer> id = new ArrayList<Integer>();
		User user = userService.getCurrentLoggedUser();
		List<Course> courses = new ArrayList<Course>();
		List<StudentCourse> UserCourse = user.getCourses();
		UserCourse.forEach(e -> {
			if (e.getUserCourseStatus().equals(StudentCourseStatus.Successfull)) {
				courses.add(e.getCourse());
			}
		});
		courses.forEach(e -> {
			id.add(e.getRegId());
		});
		List<Books> bookList = new ArrayList<Books>();
		id.forEach(e -> {
			List<Books> bookListForEachCourse = new ArrayList<Books>();
			bookListForEachCourse = bookRepository.findAllByCourse(e);
			bookListForEachCourse.forEach(i -> {
				bookList.add(i);
			});

		});
		return bookList;
	}

	@Override
	// @Cacheable(value = "courseCache")
	public List<Course> findRegisteredClasses(List<Integer> regIdClasses) {
		List<Course> checkedCourses = new ArrayList<Course>();
		for (Integer integer : regIdClasses) {
			Course checkCourse = courseRepository.findById(integer).orElse(null);
			checkedCourses.add(checkCourse);
		}
		return checkedCourses;
	}

	@Override
	public boolean isAlaivable(Course course) {
		if (course.getAvailable() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void save(Course course) {
		courseRepository.save(course);

	}

	@Override
	public Set<Course> getFailedRegisteredClasses(User user, List<Course> courses, List<Integer> regIdClasses) {
		Set<Course> failedRegisteredClasses = new HashSet<Course>();
		List<Course> registeredCourses = userService.getYourClasses();
		List<Course> courses2 = findRegisteredClasses(regIdClasses);
		courses2.forEach(e -> {
			if (!(isPreregquisited(e))) {
				failedRegisteredClasses.add(e);
			}
		});

		for (int i = 0; i < regIdClasses.size(); i++) {
			for (int j = 0; j < registeredCourses.size(); j++) {
				if (regIdClasses.get(i) == registeredCourses.get(j).getRegId()) {
					Course course = courseRepository.findById(regIdClasses.get(i)).orElse(null);
					failedRegisteredClasses.add(course);

				} else {
					Course course = courseRepository.findById(regIdClasses.get(i)).orElse(null);

					if (!(isRepeated(course))) {
						failedRegisteredClasses.add(course);
					}
				}
			}
		}
		return failedRegisteredClasses;
	}

	// get successfullRegisteredClasses by extracting from the method
	// getFailedRegisteredClasses()
	@Override
	public List<Course> getSuccessfullRegisteredClasses(User user, List<Course> courses, List<Integer> regIdClasses) {
		List<Course> registeredCourses = userService.getYourClasses();
		Set<Course> failedRegisteredCourses = getFailedRegisteredClasses(user, registeredCourses, regIdClasses);
		List<Course> successfullRegisteredClasses = findRegisteredClasses(regIdClasses);

		List<Course> a = new ArrayList<>(failedRegisteredCourses);
		for (int i = 0; i < failedRegisteredCourses.size(); i++) {
			for (int j = 0; j < successfullRegisteredClasses.size(); j++) {
				if (a.get(i).getRegId() == successfullRegisteredClasses.get(j).getRegId()) {
					successfullRegisteredClasses.remove(j);
				}
			}
		}

		return successfullRegisteredClasses;
	}

	@Override
	public void setAvailable(Integer available, Integer id) {
		courseRepository.setAvailable(available, id);

	}

	@Override
	public void setWailist(Integer waitlist, Integer id) {
		courseRepository.setWailist(waitlist, id);

	}

	@Override
	public List<Course> findBySubject(String subjectName) {
		return courseRepository.findByArea(subjectName);

	}

	// must be true to register for for course which has prerequisite course
	@Override
	public boolean isPreregquisited(Course course) {
		List<Course> registeredCourses = userService.getYourClasses();

		if (course.getSubject().getPrerequisite() != null) {

			for (int i = 0; i < registeredCourses.size(); i++) {
				StudentCourse userCourse = new StudentCourse(userService.getCurrentLoggedUser(), registeredCourses.get(i));
				if (registeredCourses.get(i).getSubject().getSubjectCode() == course.getSubject().getPrerequisite()
						.getSubjectCode() && userCourse.getIsPassed() == IsPassed.TRUE) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean isPassed(Course course) {
		StudentCourse userCourse = new StudentCourse(userService.getCurrentLoggedUser(), course);
		if (userCourse.getIsPassed() == (IsPassed.FALSE)) {
			return false;
		} else if (userCourse.getIsPassed() == (IsPassed.TRUE)) {
			return true;
		}
		return true;
	}

	// cho repeat nếu true, otherwise false
	@Override
	public boolean isRepeated(Course course) {
		List<Course> currentCourses = userService.getYourClasses();
		for (int i = 0; i < currentCourses.size(); i++) {
			if (currentCourses.get(i).getSubject().getSubjectCode().toString()
					.equals(course.getSubject().getSubjectCode().toString())) {
				if (!(isPassed(currentCourses.get(i)))) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	// register mutiple courses with the same subject_code at the same time
	@Override
	public boolean isDuplicated(List<Integer> regIdClasses) {
		List<Course> checkedCourses = findRegisteredClasses(regIdClasses);

		for (int i = 0; i < checkedCourses.size(); i++) {

			for (int j = i + 1; j < checkedCourses.size(); j++) {
				if (checkedCourses.get(i).getSubject().getSubjectCode().toString()
						.equals(checkedCourses.get(j).getSubject().getSubjectCode().toString())) {
					return true;
				}

			}

		}
		return false;
	}

	@Override
	public List<Course> getFilterdUnDuplicatedCourses(List<Integer> regIdClasses) {
		List<Course> unDuplicatedCourses = findRegisteredClasses(regIdClasses);
		System.out.println(unDuplicatedCourses.size());
		List<Course> removedCourses = findRegisteredClasses(regIdClasses);
		for (int i = 0; i < removedCourses.size(); i++) {

			for (int j = i + 1; j < removedCourses.size(); j++) {
				if (removedCourses.get(i).getSubject().getSubjectCode().toString()
						.equals(removedCourses.get(j).getSubject().getSubjectCode().toString())) {

					Course removedCourse = removedCourses.get(i);

					removedCourses.forEach(e -> {
						if (e.getSubject().getSubjectCode() == removedCourse.getSubject().getSubjectCode()) {
							unDuplicatedCourses.remove(e);
						}
					});

				}

			}

		}
		List<Course> registeredCourses = userService.getYourClasses();

		List<Integer> regId = new ArrayList<Integer>();
		unDuplicatedCourses.forEach(e -> {
			regId.add(e.getRegId());
		});

		List<Course> successfullRegisteredCourses = getSuccessfullRegisteredClasses(userService.getCurrentLoggedUser(),
				registeredCourses, regId);
		return successfullRegisteredCourses;

	}

	// return true if course is not conflicted
	@Override
	public boolean isNotTimeConflicted(Course course, List<Integer> regIdClasses) {
		List<Course> courses = findRegisteredClasses(regIdClasses);
		List<Course> registeredCourses = userService.getYourClasses();
		registeredCourses.addAll(courses);
		for (int i = 0; i < registeredCourses.size(); i++) {
			if (course.getRegId() != registeredCourses.get(i).getRegId()) {
				if (course.getCalendars().get(0).getStartTime()
						.before(registeredCourses.get(i).getCalendars().get(0).getStartTime())
						&& course.getCalendars().get(0).getEndTime()
								.before(registeredCourses.get(i).getCalendars().get(0).getStartTime())) {
				
					continue;
				} else if (course.getCalendars().get(0).getStartTime()
						.after(registeredCourses.get(i).getCalendars().get(0).getEndTime())
						&& course.getCalendars().get(0).getEndTime()
								.after(registeredCourses.get(i).getCalendars().get(0).getEndTime())) {
					continue;
				} else {

					return false;
				}
			}

			else {
				continue;
			}

		}
		return true;

	}

	public boolean isCourseConflicted(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		int a = 0;
		List<Course> unConflictedCourses = new ArrayList<Course>();
		List<Course> registeredClasses = courseService.findRegisteredClasses(regIdClasses);
		for (int i = 0; i < registeredClasses.size(); i++) {
			if (courseService.isNotTimeConflicted(registeredClasses.get(i), regIdClasses)) {
				unConflictedCourses.add(registeredClasses.get(i));
			} else {

				a++;

			}
		}
		if (a == 0) {
			return false;
		} else {
			// redirectAttributes.addFlashAttribute("error", "C");
			return true;
		}
	}

	public List<Course> getUnconflictedCourse(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		int a = 0;
		List<Course> unConflictedCourses = new ArrayList<Course>();
		List<Course> registeredClasses = courseService.findRegisteredClasses(regIdClasses);
		for (int i = 0; i < registeredClasses.size(); i++) {
			if (courseService.isNotTimeConflicted(registeredClasses.get(i), regIdClasses)) {
				unConflictedCourses.add(registeredClasses.get(i));

			}
		}
		return unConflictedCourses;
	}

	// 1 -> getUnconflictedCourses are all registered
	// 2 -> getUnconflictedCourses require pre course
	// 3-> One or more of the selected courses is already registered
	// 4 -> getUnconflictedCourses.size() = 0
	public Integer checkRegisteredCourses(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		int a = 0;
		List<Course> registeredClasses = getUnconflictedCourse(regIdClasses, redirectAttributes);
		if (registeredClasses.size() == 0) {
			return 4;
		}
		System.out.println(registeredClasses.size());
		List<Course> courses = userService.getYourClasses();
		if ((courseService.getFailedRegisteredClasses(userService.getCurrentLoggedUser(), courses, regIdClasses))
				.size() == 0) {

			userService.registerForClassess(userService.getCurrentLoggedUser(), registeredClasses);
			return 1;
		} else {
			for (int i = 0; i < registeredClasses.size(); i++) {
				if (!(courseService.isPreregquisited(registeredClasses.get(i)))) {
					a++;
				} else {
					List<Course> c = new ArrayList<Course>();
					c.add(registeredClasses.get(i));
					userService.registerForClassess(userService.getCurrentLoggedUser(), c);

				}

			}
			if (a > 0) {

				return 2;
			}
			List<Course> courses2 = courseService.getSuccessfullRegisteredClasses(userService.getCurrentLoggedUser(),
					courses, regIdClasses);
			userService.registerForClassess(userService.getCurrentLoggedUser(), courses2);

			return 3;
		}
	}

	public boolean isAnyCourseSelected(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		if (regIdClasses == null) {
			return false;
		}
		return true;
	}
}
