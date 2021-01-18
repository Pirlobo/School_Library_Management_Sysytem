 package com.baeldung.lss.persistence;

import com.baeldung.lss.model.User;

import javax.persistence.LockModeType;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    
	//@Lock(LockModeType.PESSIMISTIC_READ)
	User findByEmail(String email);
	

	

}