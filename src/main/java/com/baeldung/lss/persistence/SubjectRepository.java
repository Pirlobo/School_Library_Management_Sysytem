package com.baeldung.lss.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.model.Building;
import com.baeldung.lss.model.Room;
import com.baeldung.lss.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    

}