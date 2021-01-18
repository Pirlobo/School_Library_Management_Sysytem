package com.baeldung.lss.persistence;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Classroom;
import com.baeldung.lss.model.PasswordResetToken;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

	
	@Query(value = "select * from calendar as c inner join course as course on c.course_reg_id = course.reg_id where course.title = ?1", nativeQuery = true)
	public List<Classroom> findByTitle(String title);
	


}
