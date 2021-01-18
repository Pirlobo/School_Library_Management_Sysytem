package com.baeldung.lss.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.model.Teacher;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    

}