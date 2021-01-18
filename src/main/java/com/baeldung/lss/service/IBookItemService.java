package com.baeldung.lss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.lss.book.BookItems;

@Service
public interface IBookItemService {

	// get book Items by Id
	BookItems findById(Integer id);
	
	// Persisting Book Items
	void save(BookItems bookItems);
	
	// Save All Book Items
	void saveAll(List<BookItems> bookItems);
}
