package org.hni.admin.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.shiro.SecurityUtils;
import org.hni.common.Constants;
import org.hni.common.exception.HNIException;
import org.hni.order.om.Order;
import org.hni.order.service.OrderService;
import org.hni.payment.om.OrderPayment;
import org.hni.payment.om.PaymentInfo;
import org.hni.payment.om.PaymentInstrument;
import org.hni.payment.service.OrderPaymentService;
import org.hni.payment.service.PaymentsExceededException;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.om.ProviderLocationHour;
import org.hni.provider.service.ProviderService;
import org.hni.service.helpers.PaymentServiceHelper;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api(value = "/payments")
@SwaggerDefinition( info = @Info(
		description = "provides methods of payment and associating thsoe payments with an order/user",
		version = "v1",
		title = "Payments API"
))
@Component
@Path("/payments")
public class PaymentController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	private static final String OK = "OK";
	
	@Inject private ProviderService providerService;
	@Inject private OrderService orderService;
	@Inject private OrderPaymentService orderPaymentService;
	@Inject private PaymentServiceHelper paymentServiceHelper;
	
	@GET
	@Path("/payment-instruments/")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of payment instruments that can be used to pay for an order"
		, notes = "encrypted"
		, response = Order.class
		, responseContainer = "")
	public String getPaymentInstrumentsForProvider(@QueryParam("orderId") Long orderId, @QueryParam("providerId") Long providerId, @QueryParam("amount") Double amount) {
		//Provider provider = providerService.get(providerId);
		Order order = orderService.get(orderId);
		if ( null != order ) {
			Collection<OrderPayment> payments;
			try {
				//payments = orderPaymentService.paymentFor(order, provider, amount, getLoggedInUser());
				Optional<OrderPayment> payment = orderPaymentService.paymentFor(order, getLoggedInUser());
				return serializeOrderPaymentToJson(payment);
			} catch (PaymentsExceededException e) {
				// this exception indicates the user requested 25% more than the expected amount for the order.
				// this will do an automatic lockout
				User user = getLoggedInUser();
				if (!SecurityUtils.getSubject().hasRole(Constants.SUPER_USER.toString())) {					
					logger.warn(String.format("** Funds request exceeded! Locking account for user[%d] %s %s (%s)", user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
					organizationUserService.lock(getLoggedInUser());
					logger.warn(String.format("Release locking on order %d", order.getId()));
					orderService.releaseLock(order);
				} else {
					logger.warn(String.format("** Funds request exceeded! NOT Locking account for SUPER-USER [%d] %s %s (%s)", user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
				}
				throw new HNIException(e.getMessage(), Status.FORBIDDEN);
			}
			
		}
		throw new HNIException("The provider specified is not valid", Status.NO_CONTENT);
	}

	@PUT
	@Path("/order-payments/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of payment instruments that can be used to pay for an order"
		, notes = "encrypted"
		, response = Order.class
		, responseContainer = "")
	public Response orderAndPaymentComplete(@QueryParam("orderId") Long id, 
			@QueryParam("orderConfirmationId") String orderConfirmationId, 
			@QueryParam("orderAmt") Double orderAmt) {
		String response = null;
		try {
			User user = getLoggedInUser();
			response = paymentServiceHelper.completeOrder(id, orderConfirmationId, orderAmt, user);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response = e.getMessage();
		}
		
		return createResponse(response);
	}
	
	@POST
	@Path("/order-payments/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of payment instruments that can be used to pay for an order"
		, notes = "encrypted"
		, response = Order.class
		, responseContainer = "")
	public Response setPaymentInstrumentsForOrder(Set<PaymentInfo> paymentInfos) {
		logger.info("Payments received");
		for(PaymentInfo pi : paymentInfos){
			logger.info("  - "+pi);
		}
		Order order = orderPaymentService.assignPayment(paymentInfos, getLoggedInUser());
		logger.info(String.format("Marking order %d complete", order.getId()));
		orderService.complete(order);
		
		return createResponse(OK);
	}
	
	private String serializeOrderPaymentToJson(Collection<OrderPayment> orderPayments) {
		try {
			String json = mapper.writeValueAsString(JsonView.with(orderPayments)
					.onClass(Order.class, Match.match().exclude("*").include("id", "subtotal"))
					.onClass(User.class, Match.match().exclude("*").include("id", "firstName", "lastName"))
					.onClass(ProviderLocation.class, Match.match().exclude("*"))
					.onClass(ProviderLocationHour.class, Match.match().exclude("*").include("dow", "openHour", "closeHour"))
					.onClass(Provider.class, Match.match().exclude("*").include("id", "name"))
					.onClass(PaymentInstrument.class, Match.match().exclude("*").include("id", "cardNumber", "pinNumber")));
			return json;
		} catch (JsonProcessingException e) {
			logger.error("Serializing User object:"+e.getMessage(), e);
		}
		return "{}";
	}
	
	private String serializeOrderPaymentToJson(Optional<OrderPayment> orderPayment) {
		if (orderPayment.isPresent()) {
			try {
				String json = mapper.writeValueAsString(JsonView.with(orderPayment.get())
						.onClass(Order.class, Match.match().exclude("*").include("id", "subtotal"))
						.onClass(User.class, Match.match().exclude("*").include("id", "firstName", "lastName"))
						.onClass(ProviderLocation.class, Match.match().exclude("*"))
						.onClass(ProviderLocationHour.class, Match.match().exclude("*").include("dow", "openHour", "closeHour"))
						.onClass(Provider.class, Match.match().exclude("*").include("id", "name"))
						.onClass(PaymentInstrument.class, Match.match().exclude("*").include("id", "cardNumber", "pinNumber", "balance")));
				return json;
			} catch (JsonProcessingException e) {
				logger.error("Serializing User object:"+e.getMessage(), e);
			}
		}
		return "{}";
	}
	
}
