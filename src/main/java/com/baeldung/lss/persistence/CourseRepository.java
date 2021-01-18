package com.baeldung.lss.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.SubjectName;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	
	@Query(value = "select * from course as c inner join subject as s on s.id = c.subject_id where s.subject_code = ?1", nativeQuery=true)
    List<Course> findByTitle(String title);
    
   
   
    @Modifying
	@Query(value = "UPDATE `school`.`course` SET `available` = ?1 WHERE (`id` = ?2);", nativeQuery = true)
    void setAvailable(Integer available, Integer id);
    
    @Modifying
   	@Query(value = "UPDATE `school`.`course` SET `waitlist` = ?1 WHERE (`id` = ?2);", nativeQuery = true)
       void setWailist(Integer waitlist, Integer id);
    
    @Modifying
   	@Query(value = "select * from course as c inner join subject as s on c.subject_id = s.id where s.subject_name= ?1", nativeQuery = true)
       List<Course> findByArea(String subjectName);		
    		
    		
}