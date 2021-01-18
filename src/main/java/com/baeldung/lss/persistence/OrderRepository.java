package com.baeldung.lss.persistence;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.book.Orders;
import com.baeldung.lss.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    

}
