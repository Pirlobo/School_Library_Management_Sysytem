package com.baeldung.lss.service;

import org.springframework.stereotype.Service;

import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseId;

@Service
public interface IUserCourseService {

	// find student_course object by id
	StudentCourse findById(StudentCourseId userCourseId);
	
	// persist student_course
	void save (StudentCourse userCourse);
	
	// delete student_course
	void delete(StudentCourse userCourse);
}
