package com.baeldung.lss.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.lss.model.PasswordResetToken;
import com.baeldung.lss.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	
}
