package com.baeldung.lss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.persistence.BookItemsRepository;

@Service
public class BookItemService implements IBookItemService{
	@Autowired
	private BookItemsRepository bookItemsRepository;
	
	
	
	@Override
	public BookItems findById(Integer id) {
		BookItems bookItem = bookItemsRepository.findById(id).orElse(null);
		return bookItem;
	}

	@Override
	public void save(BookItems bookItems) {
		bookItemsRepository.save(bookItems);
		
	}

	@Override
	public void saveAll(List<BookItems> bookItems) {
		bookItemsRepository.saveAll(bookItems);
		
	}
	
	

}
