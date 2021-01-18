package com.baeldung.lss.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.persistence.BookRepository;

@Service
public class BookService implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	//@Cacheable(value = "bookCache")
	public Set<Books> getBookByTitleOrAuthor(String argument) {
		return bookRepository.findByTitleOrAuthor(argument);
	}

	@Override
	public List<BookItems> findBookItems(Books book) {
		List<BookItems>  bookItems = book.getBookItems();
		return bookItems;
	}

	@Override
	public Books findById(Integer id) {
		Books bookItem = bookRepository.findById(id).orElse(null);
		return bookItem;
	}

}
