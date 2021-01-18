package com.baeldung.lss.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.model.Building;
import com.baeldung.lss.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    

}