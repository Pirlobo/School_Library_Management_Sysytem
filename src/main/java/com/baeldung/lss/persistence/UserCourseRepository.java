package com.baeldung.lss.persistence;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.PasswordResetToken;
import com.baeldung.lss.model.StudentCourse;
import com.baeldung.lss.model.StudentCourseId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {

    

}
