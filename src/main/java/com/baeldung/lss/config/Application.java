package com.baeldung.lss.config;

import java.sql.Date;





import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.hibernate.type.CalendarTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.book.Format;
import com.baeldung.lss.model.Building;
import com.baeldung.lss.model.Classroom;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.Day;
import com.baeldung.lss.model.Roles;
import com.baeldung.lss.model.Room;
import com.baeldung.lss.model.Semester;
import com.baeldung.lss.model.SpringSecurityAuditorAware;
import com.baeldung.lss.model.Subject;
import com.baeldung.lss.model.SubjectCode;
//import com.baeldung.lss.model.Schedule;
import com.baeldung.lss.model.SubjectName;
import com.baeldung.lss.model.Teacher;
import com.baeldung.lss.model.Term;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.persistence.BookItemsRepository;
import com.baeldung.lss.persistence.BookRepository;
import com.baeldung.lss.persistence.BuildingRepository;
import com.baeldung.lss.persistence.CalendarRepository;
import com.baeldung.lss.persistence.ClassroomRepository;
import com.baeldung.lss.persistence.CourseRepository;
import com.baeldung.lss.persistence.RoleRepository;
import com.baeldung.lss.persistence.RoomRepository;
import com.baeldung.lss.persistence.SubjectRepository;
//import com.baeldung.lss.persistence.ScheduleRepository;
import com.baeldung.lss.persistence.TeacherRepository;
import com.baeldung.lss.persistence.TermRepository;
import com.baeldung.lss.persistence.UserRepository;
import com.google.common.cache.Cache;

import io.jsonwebtoken.lang.Arrays;

@SpringBootApplication
@ComponentScan("com.baeldung.lss")
@EnableJpaRepositories("com.baeldung.lss")
@EntityScan(basePackages = { "com.baeldung.lss.book", "com.baeldung.lss.model" })
//@EnableScheduling
//@ImportResource("classpath:/integration-config.xml")
public class Application {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	public BookRepository bookRepository;
	
	@Autowired
	private BookItemsRepository bookItemsRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private TermRepository termRepository;
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Class[] { Application.class, LssSecurityConfig.class, LssWebMvcConfiguration.class},
				args);
		
	}
//
//	// delay 10 s đầu rồi ms chạy , 1s chạy lần
//	@Scheduled(initialDelay = 10000, fixedRate = 1000)
//	// second, minutes, hour, day, month , dấu sao for every month 
//	// @Scheduled(cron = "0 15 10 15 * ?"
//	public void name() {
//		System.out.println("aef");
//	}
	
	
	
	@PostConstruct
	public void init() {

		List<Books> books = new ArrayList<Books>();
		
		Books book1 = new Books(1, "978-3-16-148410-0", "Java 1", "Anh T Nguyen","English", 100);
		Books book2 = new Books(2, "978-3-16-148410-0", "Java 2", "Anh T Nguyen","English", 200);
		
		
		
		List<BookItems> bookItems = new ArrayList<BookItems>();
		BookItems bookItem1 = new BookItems(1234, 150.0, 300.0, true, Format.Paper_Pack);
		BookItems bookItem2 = new BookItems(5678, 200.0, 400.0, true, Format.Hard_Cover);
		
		bookItems.add(bookItem1);
		bookItems.add(bookItem2);
		bookItemsRepository.saveAll(bookItems);
		
		Authors author1 = new Authors(1, "Phan Manh Quynh", "Love");
		Authors author2 = new Authors(2, "Pham Hong Phuoc", "Science Fiction");

		books.add(book1);
		books.add(book2);
		
		
		book1.addAuthor(author1);
		book1.addAuthor(author2);

		book2.addAuthor(author1);
		
		book1.addBookItem(bookItem1);
		book1.addBookItem(bookItem2);
		bookRepository.save(book1);
		bookRepository.save(book2);
		

		
		
		
		// persisting default Date
		com.baeldung.lss.model.Calendar calendar = new com.baeldung.lss.model.Calendar(1, new Date(120, 12, 25));
		calendarRepository.save(calendar);
		
		// new an object of Teacher

		Building building1 = new Building(1, "MS");
		List<Room> rooms = new ArrayList<Room>();
		Room room1 = new Room(1, "MS302", building1);
		Room room2 = new Room(2, "MS303", building1);
		
		rooms.add(room1);
		rooms.add(room2);

		buildingRepository.save(building1);
		roomRepository.saveAll(rooms);
		Teacher teacher1 = new Teacher(1, "Hendry Estrada");
		teacherRepository.save(teacher1);

		Set<Roles> rSet1 = new HashSet<Roles>();
		Set<Roles> rSet2 = new HashSet<Roles>();
		Roles role1 = new Roles(1, "User");
		Roles role2 = new Roles(2, "User");
		
		rSet1.add(role1);
		rSet2.add(role2);
		
		roleRepository.save(role1);
		roleRepository.save(role2);
		
		
		
		Date startDay = new Date(120, 8, 15);
		Date endDay = new Date(120, 11, 20);
		
		Date startDay1 = new Date(121, 12, 25);
		Date endDay1 = new Date(121, 4, 27);
		
		
		// new Subject
		Subject subject2 = new Subject(2, SubjectName.Computer_Science, SubjectCode.COMSC_75, null);
		subjectRepository.save(subject2);
		Subject subject = new Subject(1, SubjectName.Computer_Science, SubjectCode.COMSC_76, subject2);
		subjectRepository.save(subject);
		Subject subject3 = new Subject(3, SubjectName.Computer_Science, SubjectCode.COMSC_77, subject2);
		subjectRepository.save(subject3);
		
		
		
		Term term = new Term(1, Semester.Fall, 2020);
		Term term2 = new Term(2, Semester.Spring, 2021);
		termRepository.save(term);
		termRepository.save(term2);
		
		
		// new courses with fk_teacher
		Course course1 = new Course(10101, 201, 35, startDay, endDay, teacher1, room1, term);
		Course course2 = new Course(10102, 202, 35,startDay, endDay,  teacher1, room1, term);
		Course course4 = new Course(10103, 301, 35, startDay, endDay, teacher1, room1, term);
		Course course3 = new Course(10104, 302, 35, startDay1, endDay1, teacher1, room1, term2);
		Course course5 = new Course(10105, 401, 35, startDay1, endDay1, teacher1, room1, term2);
		course1.setSubject(subject);
		course2.setSubject(subject);
		course3.setSubject(subject2);
		course4.setSubject(subject2);
		course5.setSubject(subject3);
		course1.addBook(book1);
		courseRepository.save(course1);
		course3.addBook(book2);
		
		courseRepository.save(course3);
		courseRepository.save(course2);
		courseRepository.save(course4);
		courseRepository.save(course5);
		// Course1 and book list
		
		
	   
				
		Time startTime1 = new Time(13, 45, 00);
		Time endTime1 = new Time(15, 00, 00);
		
		Time startTime2 = new Time(15, 15, 00);
		Time endTime2 = new Time(16, 30, 00);
		
		Time startTime3 = new Time(17, 00, 00);
		Time endTime3 = new Time(18, 15, 00);
		
		
		
		List<Date> dates = createSchedule(15, 9, 2020);
		
		Date day1 = new Date(120, 8, 16);
		Date day2 = new Date(120, 8, 18);
		
		Date day3 = new Date(121, 12, 25);
		Date day4 = new Date(121, 4, 27);
		 
		List<com.baeldung.lss.model.Classroom> calendars1 = new ArrayList<com.baeldung.lss.model.Classroom>();
		List<com.baeldung.lss.model.Classroom> calendars2 = new ArrayList<com.baeldung.lss.model.Classroom>();
		List<com.baeldung.lss.model.Classroom> calendars3 = new ArrayList<com.baeldung.lss.model.Classroom>();
		List<com.baeldung.lss.model.Classroom> calendars4 = new ArrayList<com.baeldung.lss.model.Classroom>();
		List<com.baeldung.lss.model.Classroom> calendars5 = new ArrayList<com.baeldung.lss.model.Classroom>();
		com.baeldung.lss.model.Classroom calendar3 = new com.baeldung.lss.model.Classroom(29, startTime2, endTime2, day1,  course2);
		com.baeldung.lss.model.Classroom calendar4 = new com.baeldung.lss.model.Classroom(30, startTime2, endTime2, day2,  course2);
		
		com.baeldung.lss.model.Classroom calendar5 = new com.baeldung.lss.model.Classroom(31, startTime2, endTime2, day3,  course3);
		com.baeldung.lss.model.Classroom calendar6 = new com.baeldung.lss.model.Classroom(32, startTime2, endTime2, day4,  course3);
		
		com.baeldung.lss.model.Classroom calendar7 = new com.baeldung.lss.model.Classroom(33, startTime3, endTime3, day1,  course4);
		com.baeldung.lss.model.Classroom calendar8 = new com.baeldung.lss.model.Classroom(34, startTime3, endTime3, day2,  course4);
		
		com.baeldung.lss.model.Classroom calendar9 = new com.baeldung.lss.model.Classroom(35, startTime3, endTime3, day1,  course5);
		com.baeldung.lss.model.Classroom calendar10 = new com.baeldung.lss.model.Classroom(36, startTime3, endTime3, day2,  course5);
		
		
		
		int a= 0;
		for (int i = 0; i < 29; i++) {
			//int b = a++;
			com.baeldung.lss.model.Classroom period = new com.baeldung.lss.model.Classroom(startTime1, endTime1, dates.get(i), course1);
			calendars1.add(period);
		}
		
		
		calendars2.add(calendar3);
		calendars2.add(calendar4);
		calendars3.add(calendar5);
		calendars3.add(calendar6);
		calendars4.add(calendar7);
		calendars4.add(calendar8);
		calendars5.add(calendar9);
		calendars5.add(calendar10);
		classroomRepository.saveAll(calendars1);
		classroomRepository.saveAll(calendars2);
		classroomRepository.saveAll(calendars3);
		classroomRepository.saveAll(calendars4);
		classroomRepository.saveAll(calendars5);
	}
	

	
	
	
	public static List<Date> createSchedule(int startDay, int month, int year) {
		
	    int firstDay = startDay;
	       
	     int b = 2;
	     int firstMonth = month -1;
	     int y = year - 1900;
	     int start = 0;
	   List<Date> dates = new ArrayList<Date>();
	   Date day1 = new Date(y, firstMonth, firstDay);
	   dates.add(day1);
	   for (int i = 0; i < 24; i++) {
	       
	       Date even = new Date(y, firstMonth,  firstDay + b);
	       start++;
	       dates.add(even);
	       if (start==1) {
	           for (int j = 0; j < 18; j+=5) {
	               Date odd = new Date(120, even.getMonth(), even.getDate() + 5 + j);
	               dates.add(odd);
	               firstMonth = odd.getMonth();
	               start = 0;
	               firstDay = odd.getDate();
	               y = odd.getYear();
	               break;
	           }
	       } else {      
	       }  
	   }
	return dates;


	}

}
