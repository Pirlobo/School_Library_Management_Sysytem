package com.baeldung.lss.web.controller;

import java.sql.Date;


import java.sql.Time;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.apache.naming.java.javaURLContextFactory;
import org.aspectj.apache.bcel.classfile.Module.Require;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Classroom;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.UpdatedStudent;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseId;
import com.baeldung.lss.model.StudentCourseStatus;
import com.baeldung.lss.persistence.ClassroomRepository;
import com.baeldung.lss.persistence.CourseRepository;
import com.baeldung.lss.persistence.UserCourseRepository;
import com.baeldung.lss.persistence.UserRepository;
import com.baeldung.lss.service.CalendarService;
import com.baeldung.lss.service.CourseService;
import com.baeldung.lss.service.UserCourseService;
import com.baeldung.lss.service.UserService;

@Controller
public class CoursesControler {
	
//	use ticket;
//	SELECT catagory, COUNT(*) FROM ticket GROUP BY catagory having count(*) > 1 ;
	
	// chọn ra tổng giá của nhiều ticket mà cùng catagory với nhau. 
	//select sum(amount), catagory from ticket as t group by t.catagory;
	
	// ví du có 2 class implement cùng 1 thằng (Bo.class) và để annotation @Component
	// khi @Autowired 1 trong 2 thằng đã implement Bo.class thì sẽ throw error, để @Primary cho cái class nào mà
	// mún để đc inject vào. Hoặc dùng @Qualifier ("name") tùy vào Bo thui :)) 
	
	
	
	//  @DependsOn Annotation, guarantee FileReader Bean is created before FileProcessor is created
	// @Scope("singletone or prototype") whereas singletone is default, singletone return the same object, prototyoe return a brand new object
	
	
//	@Bean
	// @Lazy , the bean will be loaded lazyly because beans are loaded eagerly by default
	// @Profile("bo") meaning that the component is activated only if bo profile is active:
	// Or @Profile(" ! bo") , this bean belongs to any other profiles except bo profile,  meaning that if bo is active, bean is not created 
//    @DependsOn({"fileReader","fileWriter"})
//    public FileProcessor fileProcessor(){
//        return new FileProcessor();
//    }
//    
//    @Bean("fileReader")
//    public FileReader fileReader() {
//        return new FileReader();
//    }

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CalendarService calendarService;

	@GetMapping("/searchCourses")
	public String searchCourses() {
		return "searchCoursePage.html";
	}

	@RequestMapping("/searchCoursesByTitle")
	public String getCourseByTitle(Model model, @RequestParam("title") @ModelAttribute("title") String title,
			RedirectAttributes redirectAttributes) {
		List<Course> courseList = courseService.searchCourses(title);
		List<Integer> regIdClasses = new ArrayList<Integer>();
		if (courseList.size() == 0) {
			redirectAttributes.addFlashAttribute("error", "Can't find any course with the given title");
			return "redirect:/searchCourses";
		} else {
			model.addAttribute("courseList", courseList);
			model.addAttribute("registerClasses", regIdClasses);
			return "courseList.html";
		}
	}

	@RequestMapping("/searchCoursesBySubject")
	public String getCourseByArea(Model model, @RequestParam("subject") @ModelAttribute("subject") String subject,
			RedirectAttributes redirectAttributes) {
		List<Course> courseList = courseService.findBySubject(subject);
		List<Integer> regIdClasses = new ArrayList<Integer>();
		if (courseList.size() == 0) {
			redirectAttributes.addFlashAttribute("error", "Can't find any course with the given area");
			return "redirect:/searchCourses";
		} else {
			model.addAttribute("courseList", courseList);
			model.addAttribute("registerClasses", regIdClasses);
			return "courseList.html";
		}
	}

	@PostMapping("/registerForClassess")
	public String registerForClassess(
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		if (!(courseService.isAnyCourseSelected(regIdClasses, redirectAttributes))) {
			redirectAttributes.addFlashAttribute("error", "You haven't selected any course to register");
			return "redirect:/searchCourses";
		}
		
		if (calendarService.isBeforeDefaultTime()) {
			if (!(courseService.isDuplicated(regIdClasses))) {
				if (!(courseService.isCourseConflicted(regIdClasses, redirectAttributes))) {
					if (courseService.checkRegisteredCourses(regIdClasses, redirectAttributes) == 1) {
						redirectAttributes.addFlashAttribute("error", "Successfull");
						return "redirect:/searchCourses";
					}
					else if (courseService.checkRegisteredCourses(regIdClasses, redirectAttributes) == 2) {
						redirectAttributes.addFlashAttribute("error", "Reqiure Pre Course");
						return "redirect:/searchCourses";
					}
					else {
						redirectAttributes.addFlashAttribute("error",
								"Courses are already registered");
						return "redirect:/searchCourses";
					}
				}
				else {
					if (courseService.checkRegisteredCourses(regIdClasses, redirectAttributes) == 1) {
						redirectAttributes.addFlashAttribute("error", "Courses are conflicted, others is/are registered ");
						return "redirect:/searchCourses";
					}
					else if (courseService.checkRegisteredCourses(regIdClasses, redirectAttributes) == 2) {
						redirectAttributes.addFlashAttribute("error", "Courses are conflicted, required pre course, other is/are registered");
						return "redirect:/searchCourses";
					}
					else if(courseService.checkRegisteredCourses(regIdClasses, redirectAttributes) == 3) {
						redirectAttributes.addFlashAttribute("error",
								"Courses are conflicted, already registered, others is/are registered");
						return "redirect:/searchCourses";
					}
					redirectAttributes.addFlashAttribute("error","Courses are conflicted");
					return "redirect:/searchCourses";
					
				}
				
			} 	
			else {
				List<Course> unDuplicatedCourses = courseService.getFilterdUnDuplicatedCourses(regIdClasses);
				userService.registerForClassess(userService.getCurrentLoggedUser(), unDuplicatedCourses);
				redirectAttributes.addFlashAttribute("error", "Dupicate Courses are selected");
				return "redirect:/searchCourses";
			}
		}

		else {
			redirectAttributes.addFlashAttribute("error", "The semester is already began");
			return "redirect:/searchCourses";
		}
		
		
	
	}

	@GetMapping("/dropClasses")
	public String getYourClasses(Model model, RedirectAttributes redirectAttributes) {
		List<Course> yourClasses = userService.getYourClasses();
		if (yourClasses.size() == 0) {
			redirectAttributes.addAttribute("error", "You have no class to drop");
			return "redirect:/loginSuccessful";
		}
		model.addAttribute("yourClasses", yourClasses);
		return "yourClasses.html";
	}

	@PostMapping("/dropClasses")
	public String dropClasses(Model model,
			@RequestParam(value = "regIdClasses", required = false) @ModelAttribute("regIdClasses") List<Integer> regIdClasses,
			RedirectAttributes redirectAttributes) {
		if (regIdClasses == null) {
			redirectAttributes.addFlashAttribute("error", "You haven't select any course to drop");
			return "redirect:/dropClasses";
		}
		userService.dropClasses(regIdClasses);
		return "redirect:/loginSuccessful";
	}
	
	
}
