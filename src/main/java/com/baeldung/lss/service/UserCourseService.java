package com.baeldung.lss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseId;
import com.baeldung.lss.persistence.UserCourseRepository;

@Service
public class UserCourseService implements IUserCourseService {

	@Autowired
	private UserCourseRepository userCourseRepository;
	
	@Override
	public StudentCourse findById(StudentCourseId userCourseId) {
		StudentCourse userCourse = userCourseRepository.findById(userCourseId).orElse(null);
		return userCourse;
	}

	@Override
	public void save(StudentCourse userCourse) {
		userCourseRepository.save(userCourse);
		
	}

	@Override
	public void delete(StudentCourse userCourse) {
		userCourseRepository.delete(userCourse);
		
	}
	
}
