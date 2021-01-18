package com.baeldung.lss.persistence;

import com.baeldung.lss.book.Books;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.PasswordResetToken;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.persistence.FetchType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

//	
//	@Query(value = "SELECT * FROM books where books.title like %?1%", nativeQuery = true)
//    List<Books> findByTitle(String title);

	
	@Query(value = "select * from book as b inner JOIN books_authors as ba on b.id = ba.fk_book inner join author as a on ba.fk_author = a.id where a.name like %?1% or b.title like %?1%", nativeQuery = true)
	Set<Books> findByTitleOrAuthor(String name);

	@Query(value = "select * from book as b inner join course_books as cb on b.id = cb.books_id and cb.course_id = ?1", nativeQuery = true)
	List<Books> findAllByCourse(Integer courseId);
}
