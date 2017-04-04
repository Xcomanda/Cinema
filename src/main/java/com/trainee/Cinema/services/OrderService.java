package com.trainee.Cinema.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.trainee.Cinema.models.Order;
import com.trainee.Cinema.storage.DataStorage;

public class OrderService {
	private Map<Long, Order> orders = DataStorage.getOrders();

	public List<Order> getOrders() {
		return new ArrayList<Order>(orders.values());
	}

	public Order getOrder(long id) {
		return orders.get(id);
	}

	public Order addOrder(long movieId, int tickets, Date date) {
		Order order = new Order();
		long maxId = 0;
		for (Order bufOrder : orders.values()) {
			if (bufOrder.getId() > maxId) {
				maxId = bufOrder.getId();
			}
		}
		order.setId(maxId + 1L);
		order.setMovieId(movieId);
		order.setTickets(tickets);
		order.setShowDate(date);
		DataStorage.addOrder(order);
		return order;
	}

	public Order removeOrder(long id) {
		Order order = DataStorage.removeOrder(id);
		return order;
	}
}
