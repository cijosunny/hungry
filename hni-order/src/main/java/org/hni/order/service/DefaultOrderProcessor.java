package org.hni.order.service;

import org.apache.commons.lang.StringUtils;
import org.hni.events.service.EventRouter;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.EventName;
import org.hni.order.dao.DefaultPartialOrderDAO;
import org.hni.order.om.Order;
import org.hni.order.om.OrderItem;
import org.hni.order.om.PartialOrder;
import org.hni.order.om.TransactionPhase;
import org.hni.order.om.type.OrderStatus;
import org.hni.provider.om.GeoCodingException;
import org.hni.provider.om.Menu;
import org.hni.provider.om.MenuItem;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.service.ProviderLocationService;
import org.hni.security.om.ActivationCode;
import org.hni.security.service.ActivationCodeService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.Address;
import org.hni.user.om.User;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.redisson.api.RemoteInvocationOptions;
import org.redisson.remote.RemoteServiceAckTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Transactional
public class DefaultOrderProcessor implements OrderProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultOrderProcessor.class);

    public static String MSG_ENDMEAL = "ENDMEAL";
    public static String MSG_STATUS = "STATUS";
    public static String MSG_MEAL = "MEAL";
    public static String MSG_ORDER = "ORDER";
    public static String MSG_HUNGRY = "HUNGRY";
    public static String MSG_CONFIRM = "CONFIRM";
    public static String MSG_REDO = "REDO";
    public static String RESTAURANT = "RESTAURANT";

    public static String REPLY_NOT_CURRENTLY_ORDERING = "You're not currently ordering, please respond with HUNGRY to place an order.";
    public static String REPLY_ORDER_CANCELLED = "You've cancelled your order.";
    public static String REPLY_ORDER_GET_STARTED = "OK! Let's get you something to eat. ";
    public static String REPLY_ORDER_REQUEST_ADDRESS = "What is your nearest address? You'll need a complete address. (e.g. 1234 Main St. 63113) Or, text ENDMEAL to cancel.";
    public static String REPLY_PROVIDERS_UNAVAILABLE = "Providers currently unavailable. Reply with new location or try again later. Reply ENDMEAL to quit. ";
    public static String REPLY_NO_LOCATION_FOUND = "There seems to be a problem with your address. Please try again with street address, city, and State";
    public static String REPLY_NO_PROVIDERS_NEAR_BY = "There are no providers near your location {0}. Reply with new location or ENDMEAL to quit.";
    // In this flow, user chooses meal here : Reply 1) Ham sandwich 2) Tacos 3) Chicken Salad
    public static String REPLY_MULTIPLE_ORDERS = "You can order up to %d meals. How many would you like?";
    public static String REPLY_CONFIRM_ORDER = "You've chosen %s at %s. Reply CONFIRM to place this order, REDO to try again or ENDMEAL to quit.";
    public static String REPLY_ORDER_COMPLETE = "Great choice! You will receive a text with a confirmation code and pickup instructions in about 30 minutes.";
    public static String REPLY_NEED_VALID_RESPONSE = "Please respond with CONFIRM, REDO, or ENDMEAL";
    public static String REPLY_ORDER_PENDING = "Your order is still open, please respond with STATUS in 5 minutes to check again.";
    public static String REPLY_ORDER_READY = "Your order has been placed and should be ready to pick up shortly from %s at %s %s %s.";
    public static String REPLY_ORDER_CLOSED = "Your order has been marked as closed.";
    public static String REPLY_ORDER_NOT_FOUND = "I can't find a recent order for you, please reply HUNGRY to place an order.";
    public static String REPLY_GREATE_HERE_YOUR_OPTIONS = "Great! Here are your options: ";
    public static String REPLY_ORDER_ITEM = "%d) %s . ";
    public static String REPLY_ORDER_CHOICE = " Reply %s to choose your %s. ";
    public static String REPLY_ORDER_SELECT = " Reply %s to select your %s. ";
    public static String REPLY_YOU_GOT_IT = "You got it! ";
    public static String REPLY_HERE_ARE_YOUR_OPTIONS = "Here are your meal options: ";

    public static String REPLY_NO_UNDERSTAND = "I don't understand that. Reply with HUNGRY to place an order.";
    public static String REPLY_INVALID_INPUT = "Invalid input! ";
    public static String REPLY_EXCEPTION_REGISTER_FIRST = "You will need to reply with ENROLL to sign up first.";
    public static String REPLY_MAX_ORDERS_REACHED = "You've reached the maximum number of orders for today. Please come back tomorrow.";
    private static final RemoteInvocationOptions REDISSON_REMOTE_INVOCATION_OPTION =
            RemoteInvocationOptions.defaults().expectAckWithin(10, TimeUnit.SECONDS).noResult();

    @Inject
    private UserDAO userDao;

    @Inject
    private DefaultPartialOrderDAO partialOrderDAO;

    @Inject
    private ProviderLocationService locationService;

    @Inject
    private OrderService orderService;

    @Inject
    private ActivationCodeService activationCodeService;

    @Inject
    private EventRouter eventRouter;

    @Inject
    private LockingService<RedissonClient> redissonClient;

    @Inject
    private PushMessageService pushMessageService;
    
    @Inject 
    OrderServiceHandler orderServiceHelper;
    
    @PostConstruct
    void init() {
        if (eventRouter.getRegistered(EventName.MEAL) != this) {
            eventRouter.registerService(EventName.MEAL, this);
        }
    }

    public String processMessage(User user, String message) {
        PartialOrder order = partialOrderDAO.byUser(user);
        boolean cancellation = message.equalsIgnoreCase(MSG_ENDMEAL);

        if (order == null && cancellation) {
            return REPLY_NOT_CURRENTLY_ORDERING;
        } else if (order == null && !message.equalsIgnoreCase(MSG_STATUS)) {

            if (orderService.maxDailyOrdersReached(user)) {
                return REPLY_MAX_ORDERS_REACHED;
            }
            order = new PartialOrder();
            order.setTransactionPhase(TransactionPhase.MEAL);
            order.setUser(user);
        } else if (order == null) {
            //status check
            return checkOrderStatus(user);
        } else if (cancellation) {
            partialOrderDAO.delete(order);
            return REPLY_ORDER_CANCELLED;
        }

        TransactionPhase phase = order.getTransactionPhase();
        String output = "";

        switch (phase) {
            case MEAL:
                output = requestingMeal(user, message, order);
                break;
            case PROVIDING_ADDRESS:
                output = findNearbyMeals(message, order);
                break;
            case CHOOSING_LOCATION:
                output = chooseLocation(user, message, order);
                break;
            case CHOOSING_MENU_ITEM:
                //this is chosen w/ provider for now
            	output = chooseProvider(user, message, order);
                break;
            case MULTIPLE_ORDER:
                output = handleMultipleOrders(user, message, order);
                break;
            case CONFIRM_OR_REDO:
                return confirmOrContinueOrder(message, order);
            default:
                //shouldn't get here
        }
        partialOrderDAO.save(order);
        return output;
    }

    public String processMessage(Long userId, String message) {
        return processMessage(userDao.get(userId), message);
    }

    private String requestingMeal(User user, String request, PartialOrder order) {
        if (request.equalsIgnoreCase(MSG_MEAL) || request.equalsIgnoreCase(MSG_ORDER) || request.equalsIgnoreCase(MSG_HUNGRY)) {
            order.setTransactionPhase(TransactionPhase.PROVIDING_ADDRESS);
            return REPLY_ORDER_GET_STARTED + REPLY_ORDER_REQUEST_ADDRESS;
        } else {
            return REPLY_NO_UNDERSTAND;
        }
    }

    private String findNearbyMeals(String addressString, PartialOrder order) {
        String output = "";
        try {
            // ### TODO: The last two arguments are no-ops right now. These are place holders for when the efficient geo-search
            // ### algorithm is brought back into play.
            // Github issue #58 - https://github.com/hungernotimpossible/hni/issues/58
            Address customerAddress = locationService.searchCustomerAddress(addressString);
            if (customerAddress == null) {
                output = REPLY_NO_LOCATION_FOUND;
            } else {
                Collection<ProviderLocation> nearbyProviders = locationService.providersNearCustomer(customerAddress, 3, 0, 0);
                if (!nearbyProviders.isEmpty()) {
                    order.setAddress(addressString);
                    List<ProviderLocation> nearbyWithMenu = new ArrayList<>();
                    List<MenuItem> items = new ArrayList<>();
                    //TODO : check for same provider with multiple branches in same location.
                    for (ProviderLocation location : nearbyProviders) {
                        Optional<Menu> currentMenu = Optional.ofNullable(location.getMenu());
                        if(!location.getProvider().isDeleted() && location.getProvider().isActive() && location.getIsActive()){
                        	 if (currentMenu.isPresent() && isCurrent(currentMenu.get())) {
                                 nearbyWithMenu.add(location);
                             }
                        }
                    }
                    if (!nearbyWithMenu.isEmpty()) {
                        order.setProviderLocationsForSelection(nearbyWithMenu);
                        output += providerLocationMenuOutput(order);
                        order.setTransactionPhase(TransactionPhase.CHOOSING_LOCATION);
                    } else {
                        output = REPLY_PROVIDERS_UNAVAILABLE;
                    }
                } else {
                    String address = new StringBuilder().append(customerAddress.getAddress1())
                            .append(StringUtils.isBlank(customerAddress.getAddress2()) ? ", " : " " + customerAddress.getAddress2() + ", ")
                            .append(customerAddress.getCity())
                            .append(" ")
                            .append(customerAddress.getState())
                            .toString();
                    output = MessageFormat.format(REPLY_NO_PROVIDERS_NEAR_BY, address);
                }
            }
        } catch (GeoCodingException e) {
            output = e.getMessage();
        }

        return output;
    }


    private String chooseLocation(User user, String message, PartialOrder order) {
        String output = "";
        try{
        	int input = Integer.parseInt(message);
        	List<MenuItem> items = new ArrayList<>();
        	ProviderLocation location = order.getProviderLocationsForSelection().get(input-1);
        	Optional<Menu> currentMenu = Optional.ofNullable(location.getMenu());
            if (currentMenu.isPresent() && isCurrent(currentMenu.get())) {
            	for(MenuItem mi : currentMenu.get().getMenuItems()){
            		if(mi.isActive()){
            			items.add(mi);
            			if(items.size() == 5)
            				break;
            		}
            	}
            }
        	 order.setMenuItemsForSelection(items);
        	 order.setChosenProvider(location);
        	 order.setTransactionPhase(TransactionPhase.CHOOSING_MENU_ITEM);
        	 output += providerLocationMenuItemOutput(order);
        
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
        output += REPLY_INVALID_INPUT;
        }
        return output;
    }
    
    private String providerLocationMenuItemOutput(PartialOrder order){
    	 // Note: spaces are significant in the Strings below!
        String options = "";
        String meals = "";
        for (int i = 0; i < order.getMenuItemsForSelection().size(); i++) {
            MenuItem menuItem = order.getMenuItemsForSelection().get(i);
            options += (i + 1) + ", ";
            meals += String.format(REPLY_ORDER_ITEM, (i + 1), menuItem.getName());
        }
        // remove training comma and space
        options = options.substring(0, options.length() - 2);

        // if there's more than 1 option remove the last number, space and comma and replace with or #
        if (options.length() > 1) {
            options = options.substring(0, options.length() - 3);
            options += " or " + order.getMenuItemsForSelection().size();
        }
        return String.format(REPLY_YOU_GOT_IT + REPLY_HERE_ARE_YOUR_OPTIONS +  meals + REPLY_ORDER_SELECT, options , MSG_MEAL.toLowerCase());
    }
    
    private String chooseProvider(User user, String message, PartialOrder order){
    	String output = "";
        try {
            int index = Integer.parseInt(message);
            if (index < 1 || index > 5) {
                throw new IndexOutOfBoundsException();
            }
            MenuItem chosenItem = order.getMenuItemsForSelection().get(index - 1);
            order.getMenuItemsSelected().add(chosenItem);

            // If this user has multiple auth codes we'll want to ask them how many of this item 
            Integer maxAllowedOrderForTheDay = orderServiceHelper.maxAllowedOrderForTheDay(user);
            
            if (maxAllowedOrderForTheDay > 1) {
                order.setTransactionPhase(TransactionPhase.MULTIPLE_ORDER);
                output = String.format(REPLY_MULTIPLE_ORDERS, maxAllowedOrderForTheDay);
            } else {
                order.setTransactionPhase(TransactionPhase.CONFIRM_OR_REDO);
                output = String.format(REPLY_CONFIRM_ORDER, chosenItem.getName(), order.getChosenProvider().getProvider().getName());
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            output += REPLY_INVALID_INPUT;
            output += providerLocationMenuItemOutput(order);
        }
        return output;
    }

    private String handleMultipleOrders(User user, String message, PartialOrder order) {
        String output = "";

        int num;
        try {
            num = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            // if they can't type in a valid number, set to ZERO and next phase is REDO
            num = 0;
        }

        //List<ActivationCode> activationCodes = activationCodeService.getByUser(user);
        Integer maxAllowedOrderForTheDay = orderServiceHelper.maxAllowedOrderForTheDay(user);
        LOGGER.debug("# Orders remaning for the day =" + maxAllowedOrderForTheDay);
        if (num <= 0) {
            LOGGER.info("Reset order choices for PartialOrder {} by user request", order.getId());
            //clear out previous choices
            output = findNearbyMeals(order.getAddress(), order);
        } else {
            if (num > maxAllowedOrderForTheDay) {
                // Too many - order them the maximum and move on
                LOGGER.debug("User requested more meals than they have. Req=" + num + " actual=" + maxAllowedOrderForTheDay);
                num = maxAllowedOrderForTheDay;
            }
            Collection<MenuItem> menuItems = order.getMenuItemsSelected();
            MenuItem menuItem = menuItems.iterator().next();

            for (int x = 0; x < num - 1; x++) {
                LOGGER.debug("Adding menuItem to partialOrder");
                menuItems.add(menuItem);
            }
            // Set next phase to confirm order
            order.setTransactionPhase(TransactionPhase.CONFIRM_OR_REDO);
            output = String.format(REPLY_CONFIRM_ORDER, menuItem.getName(), order.getChosenProvider().getProvider().getName());
        }
        return output;

    }

    private String confirmOrContinueOrder(String message, PartialOrder order) {
        String output = "";

        if (message.equalsIgnoreCase(MSG_CONFIRM)) {
            //create a final order and set initial info
            Order finalOrder = new Order();
            finalOrder.setUserId(order.getUser().getId());
            finalOrder.setOrderDate(new Date());
            finalOrder.setProviderLocation(order.getChosenProvider());
            //set items being ordered
            Collection<MenuItem> chosenItems = order.getMenuItemsSelected();
            Set<OrderItem> orderedItems = chosenItems.stream().map(mItem -> new OrderItem(1L, mItem.getPrice(), mItem))
                    .collect(Collectors.toSet());
            orderedItems.forEach(item -> item.setOrder(finalOrder));
            finalOrder.setOrderItems(orderedItems);
            finalOrder.setSubTotal(orderedItems.stream().map(item -> (item.getAmount() * item.getQuantity())).reduce(0.0, Double::sum));
            finalOrder.setStatus(OrderStatus.OPEN);
            final Order savedOrder = orderService.save(finalOrder);
            partialOrderDAO.delete(order);
            savedOrder.setUser(order.getUser());
            invokeRemoteOrderEventConsumer(savedOrder);
            LOGGER.info("Successfully created order {}", finalOrder.getId());
            output = REPLY_ORDER_COMPLETE;
        } else if (message.equalsIgnoreCase(MSG_REDO)) {
            // reset selected menu items
            order.setMenuItemSelected(Collections.EMPTY_SET);
            order.setChosenProvider(null);
            // use the existing order address, provider location and menu data
            output += providerLocationMenuOutput(order);
            order.setTransactionPhase(TransactionPhase.CHOOSING_LOCATION);
            partialOrderDAO.save(order);
        } else {
            output += REPLY_NEED_VALID_RESPONSE;
        }
        return output;
    }

    private String checkOrderStatus(User user) {
        Collection<Order> orders = orderService.get(user, LocalDate.now());
        Optional<Order> order = orders.stream().sorted((a, b) -> b.getOrderDate().compareTo(a.getOrderDate())).findFirst();
        if (order.isPresent()) {
            OrderStatus status = order.get().getOrderStatus();
            if (status.equals(OrderStatus.OPEN)) {
                return REPLY_ORDER_PENDING;
            } else if (status.equals(OrderStatus.ORDERED)) {
                return String.format(REPLY_ORDER_READY, order.get().getProviderLocation().getProvider().getName(),order.get().getProviderLocation().getAddress().getAddress1(), order.get().getProviderLocation().getAddress().getCity(), order.get().getProviderLocation().getAddress().getState());

            } else {
                //TODO should we say anything for if they suspect an error
                return REPLY_ORDER_CLOSED;
            }
        } else {
            return REPLY_ORDER_NOT_FOUND;
        }
    }

    /**
     * This method loops through the providerLocations of an order to create a string output of them the menu items
     * they contain.
     *
     * @param order
     * @return
     */
    private String providerLocationMenuOutput(PartialOrder order) {

        // Note: spaces are significant in the Strings below!
        String options = "";

        String meals = "";
        for (int i = 0; i < order.getProviderLocationsForSelection().size(); i++) {
            ProviderLocation location = order.getProviderLocationsForSelection().get(i);
            options += (i + 1) + ", ";
            meals += String.format(REPLY_ORDER_ITEM, (i + 1), getAddressString(location));
        }
        // remove training comma and space
        options = options.substring(0, options.length() - 2);

        // if there's more than 1 option remove the last number, space and comma and replace with or #
        if (options.length() > 1) {
            options = options.substring(0, options.length() - 3);
            options += " or " + order.getProviderLocationsForSelection().size();
        }
        return String.format(REPLY_GREATE_HERE_YOUR_OPTIONS + meals + REPLY_ORDER_CHOICE, options, RESTAURANT.toLowerCase()) ;
    }

    private String getAddressString(ProviderLocation location) {
		StringBuilder sb = new StringBuilder();
		sb.append(location.getProvider().getName());
		sb.append(", ");
		sb.append(location.getAddress().getAddress1());
		sb.append(", ");
		sb.append(location.getAddress().getCity());
		sb.append(", ");
		sb.append(location.getAddress().getState());
		
		return sb.toString();
	}
    private boolean isCurrent(Menu menu) {
        //TODO this has issues between 11:00 and 11:59 pm because minutes are not stored in db
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(menu.getStartHourAvailable().intValue(), 0);
        LocalTime end = LocalTime.of(menu.getEndHourAvailable().intValue(), 0);

        if (start.compareTo(end) < 0) {
            //eg start at 06:00 and end at 12:00
            return start.compareTo(now) < 0 && now.compareTo(end) < 0;
        } else {
            //eg start at 11:00 end at 04:00
            return (start.compareTo(now) < 0 && now.compareTo(LocalTime.MAX) < 0) ||
                    (now.compareTo(LocalTime.MIN) > 0 && now.compareTo(end) < 0);
        }
    }


    private void invokeRemoteOrderEventConsumer(Order order) {
        RRemoteService remoteService = redissonClient.getNativeClient().getRemoteService();
        try {
            // invoke the remote service
        	try{
        		pushMessageService.createPushMessageAndSend(order);
        	} catch(Exception e){
        		LOGGER.error("No providers found for this order", e);
        	}
            OrderEventConsumerAsync orderEventConsumer = remoteService.get(OrderEventConsumerAsync.class, REDISSON_REMOTE_INVOCATION_OPTION);
            orderEventConsumer.process(order);
        } catch (RemoteServiceAckTimeoutException ex) {
            LOGGER.warn("Processing order confirm event for order {} ack timeout", order);
            LOGGER.warn("Processing order confirm event throws RemoteServiceAckTimeoutException", ex);
        }
    }

    @Override
    public String handleEvent(Event event) {

        // Look up the user
        String phoneNumber = event.getPhoneNumber();
        List<User> users = userDao.byMobilePhone(phoneNumber);

        if (users != null && !users.isEmpty()) {
            // process the text message
            return processMessage(users.get(0), event.getTextMessage().trim());
        } else {
            String message = "OrderProcessor failed to lookup user by phone " + phoneNumber;
            LOGGER.error(message);
            return REPLY_EXCEPTION_REGISTER_FIRST;
        }
    }
}
