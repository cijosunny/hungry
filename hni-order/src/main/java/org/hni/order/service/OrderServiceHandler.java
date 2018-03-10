/**
 * 
 */
package org.hni.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.shiro.util.ThreadContext;
import org.hni.common.Constants;
import org.hni.order.om.Order;
import org.hni.user.om.Client;
import org.hni.user.om.User;
import org.hni.user.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author U45914
 *
 */
@Component
public class OrderServiceHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderServiceHandler.class);
	@Inject
	private ClientService clientService;

    @Inject
    private OrderService orderService;

    static Map<String, Integer> TIMEZONE = new HashMap<>();
    
    static {
    	TIMEZONE.put("MO", -5);
    	TIMEZONE.put("OR", -7);
    }
    
	public Integer maxAllowedOrderForTheDay(User user) {
		
		Optional<Client> client = Optional.of(clientService.getByUserId(user.getId()));
		
		Integer totalMealsCompletedToday = getTotalMealsCompletedToday(user);
		
		if (client.get().getMaxMealsAllowedPerDay() > totalMealsCompletedToday) {
			return client.get().getMaxMealsAllowedPerDay() - totalMealsCompletedToday;
		} else {
			return -1;
		}
	}

	private Integer getTotalMealsCompletedToday(User user) {
		Collection<Order> orders = getOrdersForToday(user);
		Integer totalMealCount = 0;
		if (!orders.isEmpty()) {
			for(Order order : orders) {
				totalMealCount += order.getOrderItems().size();
			}
		}
		return totalMealCount;
	}
	
	private Collection<Order> getOrdersForToday(User user) {
		String userState = (String) ThreadContext.get(Constants.STATE);
		if (userState == null) {
			userState = "MO";
		}
		/*
		Integer timeDiff = TIMEZONE.get(userState);
		LocalDate midNight = null;
		if (timeDiff > 0) {
			midNight = LocalDate.now().atTime(0, 0).plusHours(timeDiff).toLocalDate();
		} else {
			midNight = LocalDate.now().atTime(0, 0).minusHours(timeDiff).toLocalDate();
		}
		
		*/
		LocalDateTime startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0); //.minus(Duration.ofHours(mealOffset));
		LocalDateTime endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);  // set duration to be all all day
 		
    	
    	return orderService.get(user, startDate, endDate);		
	}

}
