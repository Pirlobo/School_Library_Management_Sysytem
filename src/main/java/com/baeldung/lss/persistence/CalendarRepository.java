package com.baeldung.lss.persistence;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Calendar;
import com.baeldung.lss.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    

}
