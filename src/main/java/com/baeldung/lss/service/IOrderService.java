package com.baeldung.lss.service;

import org.springframework.stereotype.Service;

import com.baeldung.lss.book.Orders;

@Service
public interface IOrderService {

	// persist order
	void save(Orders orders);
}
