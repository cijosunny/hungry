package org.hni.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.hni.common.service.AbstractService;
import org.hni.order.dao.OrderDAO;
import org.hni.order.om.Order;
import org.hni.order.om.type.OrderStatus;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.security.om.ActivationCode;
import org.hni.security.service.ActivationCodeService;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultOrderService extends AbstractService<Order> implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);
	private static final Long DEFAULT_TIMEOUT = 20L; // lock orders for 15 minutes
	private OrderDAO orderDao;
	private LockingService lockingService;
	private ActivationCodeService activationCodeService;
	@Inject
	private OrderServiceHandler orderServiceHandler;
	
	@Inject
	public DefaultOrderService(OrderDAO orderDao, LockingService lockingService, 
			ActivationCodeService activationCodeService) {
		super(orderDao);
		this.orderDao = orderDao;
		this.lockingService = lockingService;
		this.activationCodeService = activationCodeService;
	}

	@Override
	public Order save(Order order) {
		if (null == order.getStatusId()) {
			order.setStatus(OrderStatus.OPEN);
		}
		if ( null == order.getOrderDate()) {
			order.setOrderDate(new Date());
		}
		return super.save(order);
	}

	@Override
	public Collection<Order> get(User user) {
		return orderDao.get(user);
	}

	@Override
	public Collection<Order> get(User user, LocalDate startDate) {
		return get(user, startDate, startDate.plus(1, ChronoUnit.DAYS));
	}

	@Override
	public Collection<Order> get(User user, LocalDate startDate, LocalDate endDate) {
		return this.orderDao.get(user,  startDate, endDate);
	}

	@Override
	public Collection<Order> get(User user, LocalDateTime startDate, LocalDateTime endDate) {
		return this.orderDao.get(user,  startDate, endDate);
	}
	
	@Override
	public Order next(String stateCode) {
		// Returns an uncompleted order placed within the last 24 hours. Null if none found.
		Collection<Order> orders = orderDao.with(OrderStatus.OPEN, stateCode);

		for(Order order : orders) {
			if (lockAcquired(order)) {
				return order;
			}
		}
		return null;
		/*
		 * unclear to me on streams whether it will attempt to gather locks on everything before finding first.  seems like it will which is undesired
		return orders.stream()
				.filter(order -> lockAcquired(order))
				.sorted((order1, order2) -> Long.compare(order1.getOrderDate().getTime(), order2.getOrderDate().getTime()))
				.findFirst()
				.orElse(null);
		*/		
	}

	@Override
	public Order next(Provider provider, String stateCode) {
		// Returns an uncompleted order placed within the last 24 hours. Null if none found.
		Collection<Order> orders = orderDao.with(provider, OrderStatus.OPEN, stateCode);

		for(Order order : orders) {
			if (lockAcquired(order)) {
				return order;
			}
		}
		return null;
		/*
		 * unclear to me on streams whether it will attempt to gather locks on everything before finding first.  seems like it will which is undesired
		return orders.stream()
				.filter(order -> lockAcquired(order))
				.sorted((order1, order2) -> Long.compare(order1.getOrderDate().getTime(), order2.getOrderDate().getTime()))
				.findFirst()
				.orElse(null);
		*/		
	}

	@Override
	public Order complete(Order order) {
		order.setStatusId(OrderStatus.ORDERED.getId());		
		return releaseLock(save(order));
	}

	@Override
	public Order reset(Order order) {
		order.setStatusId(OrderStatus.OPEN.getId());		
		return releaseLock(save(order));
	}
	
	@Override
	public long countOrders(String stateCode) {
		Collection<Order> orders = orderDao.with(OrderStatus.OPEN, stateCode);
		return orders.stream()
				.filter(order -> !isLocked(order))
				.count();
	}

	@Override
	public long countOrders(Provider provider, String stateCode) {
		Collection<Order> orders =  orderDao.with(provider, OrderStatus.OPEN, stateCode);
		return orders.stream()
				.filter(order -> !isLocked(order))
				.count();
		
	}

	@Override
	public Order releaseLock(Order order) {
		lockingService.releaseLock(getLockingKey(order));
		return order;
	}
	
	private boolean isLocked(Order order) {
		return lockingService.isLocked(getLockingKey(order));
	}
	
	private synchronized boolean lockAcquired(Order order) {
		String key = getLockingKey(order);
		
		if (lockingService.isLocked(key)) {
			return false;
		}
		// lock the order
		lockingService.acquireLock(key, DEFAULT_TIMEOUT);
		return true;
	}

	private static final String getLockingKey(Order order) {
		if (null != order) {
			return String.format("order:%d", order.getId());
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Determines if the user has reached the maximum number of daily orders.
	 * Start looking for meals placed after midnight on the current day. This will count towards the day's meal total.
	 * Currently, they can get 1 meal per associated activation code.
	 * 1 activation code  = 1 meal  per day
	 * 2 activation codes = 2 meals per day
	 * @param user
	 * @return
	 */
	@Override
	public boolean maxDailyOrdersReached(User user) {
		
    	Integer maxOrderRemaning = orderServiceHandler.maxAllowedOrderForTheDay(user);
    	return !(maxOrderRemaning > 0);
	}

	/**
	 * Returns true if the user has one or more valid and active activation codes.
	 * @param user
	 * @return
	 */
	@Override
	public boolean hasActiveActivationCodes(User user) {
		List<ActivationCode> list = activationCodeService.getByUser(user);
		logger.debug("hasActiveActivationCodes size=" + list.size());
		return (list.size() > 0);
	}

	@Override
	public Order cancelOrder(Order order) {
		
		if (lockingService.isLocked(getLockingKey(order))) {
			releaseLock(order);
		}
		order.setStatus(OrderStatus.CANCELLED);
		orderDao.update(order);
		return order;
	}

	@Override
	public Collection<Order> getOpenOrdersFor(User user) {
		return orderDao.getOpenOrdersFor(user);
	}

	@Override
	public List<Order> getOpenOrdersForLocation(
			ProviderLocation providerLocation) {
		return orderDao.getOpenOrdersForLocation(providerLocation);
	}
}
