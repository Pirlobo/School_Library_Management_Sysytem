package com.baeldung.lss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.lss.book.Orders;
import com.baeldung.lss.persistence.OrderRepository;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	public void save(Orders orders) {
		orderRepository.save(orders);
	}
}
