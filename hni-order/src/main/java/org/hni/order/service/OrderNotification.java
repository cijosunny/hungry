package org.hni.order.service;

import org.hni.order.om.Order;

public interface OrderNotification {
	void publishOrder(Order order);
	void notifyUsers();
}
