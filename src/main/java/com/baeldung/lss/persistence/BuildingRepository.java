package com.baeldung.lss.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    

}

