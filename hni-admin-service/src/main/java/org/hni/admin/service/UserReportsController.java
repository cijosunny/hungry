package org.hni.admin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.Constants;
import org.hni.common.DateUtils;
import org.hni.common.HNIUtils;
import org.hni.order.om.Order;
import org.hni.order.service.OrderService;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;
import org.hni.user.service.UserReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;


@Api(value = "/reports/view", description = "Reports for all user type")
@Component
@Path("/reports/view")
public class UserReportsController extends AbstractBaseController {

	private static final Logger _LOGGER = LoggerFactory.getLogger(UserReportsController.class);

	@Inject
	private UserReportService userReportService;

	@Inject
	private OrderService orderService;

	@GET
	@Path("/ngo/all")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting basic detail of NGO", notes = "", response = List.class, responseContainer = "")
	public Response getAllNgo() {

		Map<String, Object> response = new HashMap<>();
		try {
			List<NgoBasicDto> ngo = userReportService.getAllNgo();
			response.put("headers", HNIUtils.getReportHeaders(40, canEditField()));
			response.put("data", ngo);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Ngo Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		return Response.ok(response).build();

	}

	private Boolean canEditField() {
		return true;
	}

	@GET
	@Path("/customers/all")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting all Customer details by Role", notes = "", response = List.class, responseContainer = "")
	public Response getAllCustomersByRole() {

		Map<String, Object> response = new HashMap<>();
		try {
			response.put("headers", HNIUtils.getReportHeaders(60, canEditField()));
			List<Map> customers = userReportService.getAllCustomersByRole();
			response.put("data", customers);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Customers By role Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		return Response.ok(response).build();

	}

	@GET
	@Path("/customers/organization")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting all Customer details under an organization", notes = "", response = Map.class, responseContainer = "")
	public Response getAllCustomersUnderOrganisation() {

		User user = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			List<Map> customers = userReportService.getAllCustomersUnderOrganisation(user);
			response.put("headers", HNIUtils.getReportHeaders(60, canEditField()));
			response.put("data", customers);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Customers under an organization Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		return Response.ok(response).build();

	}

	@GET
	@Path("/volunteers/all")
	@Produces({ MediaType.APPLICATION_JSON })

	public Response getAllVolunteers() {
		Map<String, Object> response = new HashMap<>();
		Long userId = (Long) ThreadContext.get(Constants.USERID);
		List<JSONObject> dataList = new ArrayList<>();
		try {
			List<Volunteer> volunteers = userReportService.getAllVolunteers(userId);
			for (Volunteer volunteer : volunteers) {
				JSONObject json = new JSONObject();
				json.put("firstName", volunteer.getFirstName());
				json.put("lastName", volunteer.getLastName());
				json.put("address", volunteer.getAddress().getAddress1()+","+volunteer.getAddress().getCity()+","+volunteer.getAddress().getState());
				json.put("phone", volunteer.getPhoneNumber());
				json.put("email", volunteer.getEmail());
				json.put("userId", volunteer.getUserId());
				
				dataList.add(json);
			}
			response.put("headers", HNIUtils.getReportHeaders(50, canEditField()));
			response.put("data", dataList);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in getting volunteers : " + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}

		return Response.ok(response).build();

	}

	@GET
	@Path("/customers/ngo")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting all the details of customers created by NGO", notes = "", response = List.class, responseContainer = "")
	public Response getAllCustomersEnrolledByNgo() {

		User user = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			List<Map> customers = userReportService.getAllCustomersEnrolledByNgo(user);
			response.put("headers", HNIUtils.getReportHeaders(Constants.REPORT_ALL_CUSTOMER_NGO, canEditField()));
			response.put("data", customers);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Customers created by an Ngo Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		return Response.ok(response).build();

	}

	@GET
	@Path("/orders/all")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns a collection of orders for the given user between the given dates.  If the endDate is not supplied it will default to current date", notes = "accepted date formats yyyy-mm-dd, yyyy/mm/dd, mm-dd-yyyy, mm/dd/yyyy", response = Order.class, responseContainer = "")
	public Response getUserOrdersBetweenDates(@QueryParam("id") Long id, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		Map<String, Object> response = new HashMap<>();

		List<JSONObject> dataList = new ArrayList<>();
		LocalDate start;
		Collection<Order> OrderList = new ArrayList<>();
		if (startDate == null) {
			start = LocalDate.now();
			start = start.minusDays(30L);
		} else {
			start = DateUtils.parseDate(startDate);
		}
		LocalDate end = LocalDate.now();
		if (!StringUtils.isEmpty(endDate)) {
			end = DateUtils.parseDate(endDate);
		}
		if (id == null) {
			User u = getLoggedInUser();
			id = u.getId();
		}

		try {
			OrderList = orderService.get(new User(id), start, end);
			for (Order order : OrderList) {

				JSONObject or = new JSONObject();

				or.put("orderDate", dateFormat.format(order.getOrderDate()));
				or.put("readyDate", dateFormat.format(order.getReadyDate()));
				or.put("name", order.getUser().getFirstName() + " " + order.getUser().getLastName());
				or.put("orderstatus", order.getOrderStatus().getName());
				or.put("total", order.getTotal());
				// or.put("orderItems", order.getOrderItems());
				dataList.add(or);
			}
			response.put("headers", HNIUtils.getReportHeaders(30, canEditField()));
			response.put("data", dataList);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Order details :" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}

		return Response.ok(response).build();
	}

	@GET
	@Path("/provider/all")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting all the details of providers ", notes = "", response = List.class, responseContainer = "")
	public Response getAllProviders() {

		User user = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			List<ObjectNode> providers = userReportService.getAllProviders(user);
			response.put("headers", HNIUtils.getReportHeaders(20, canEditField()));
			response.put("data", providers);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get Provider Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}

		return Response.ok(response).build();

	}
	@GET
	@Path("/get/customers/all/orders")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Service for getting total customers and orders ", notes = "", response = List.class, responseContainer = "")
	public Response getAllParticipantsAndOrders() {

		Map<String, Object> response = new HashMap<>();
		try {
			Map<String,Object> statAttributes =  userReportService.getCustomersOrderCount();
			response.put("stats", statAttributes);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			_LOGGER.error("Error in get participant and order total:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}

		return Response.ok(response).build();

	}

}
