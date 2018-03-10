package org.hni.order.service;

import javax.inject.Inject;

import org.hni.order.om.Order;
import org.hni.user.service.VolunteerService;

public class OrderNotificationProcessor implements OrderNotification {
	
	@Inject
	private VolunteerService volunteerService;

	@Override
	public void publishOrder(Order order) {
		volunteerService.getAllVolunteers();
	}

	@Override
	public void notifyUsers() {
		// TODO Auto-generated method stub
		
	}

}
