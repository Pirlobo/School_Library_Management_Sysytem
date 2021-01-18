package com.baeldung.lss.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Books;


@Service
public interface IBookService {

	
	// find a list of books by title or author's name of the books 
	Set<Books> getBookByTitleOrAuthor(String argument);
       
	
	// find all book items of a book
	List<BookItems> findBookItems(Books book);
	
	// find books by id (isbn)
	Books findById(Integer id);
	
	
}
