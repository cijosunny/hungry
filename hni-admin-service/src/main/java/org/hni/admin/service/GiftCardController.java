package org.hni.admin.service;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.admin.service.dto.PaymentInstrumentDto;
import org.hni.common.exception.HNIException;
import org.hni.payment.om.PaymentInstrument;
import org.hni.service.helpers.GiftCardServiceHelper;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Api(value = "/giftCards", description = "Operations on gift cards and to manage gift card operations")
@Component
@Path("/giftCards")
public class GiftCardController extends AbstractBaseController {
	
	private static final Logger _LOGGER = LoggerFactory.getLogger(GiftCardController.class);
	
	@Inject
	private GiftCardServiceHelper giftCardServiceHelper;

	@GET
	@Path("/{providerId}/provider")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the giftcard associated with the provider id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response getGiftCards(@PathParam("providerId") Long providerId) {
		_LOGGER.debug("Request reached to retrieve gift cards, provider:  " +providerId );
		User user = getLoggedInUser();
		if(user == null){
			_LOGGER.debug("You must have elevated permissions to do this.");
			throw new HNIException("You must have elevated permissions to do this.");
		}
		return Response.ok(giftCardServiceHelper.getGiftCardsFor(providerId), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/deactivate/")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deactivate the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response deactivateGiftCard(Long giftCardId) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.deactivateGiftCard(giftCardId), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/activate/")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deactivate the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response activateGiftCard(Long giftCardId) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.activateGiftCard(giftCardId), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/cards/update")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deletes the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response updateGiftCard(List<PaymentInstrumentDto> newCards) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.updateGiftCards(newCards), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/save")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Deletes the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response saveGiftCard(PaymentInstrument paymentInstrument) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.saveGiftCards(paymentInstrument, user), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/getCard/{giftCardId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response getGiftCard(@PathParam("giftCardId") Long giftCardId) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.getGiftCards(giftCardId), MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Path("/{giftCardId}/recharge/{amount}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Returns the gift card with the gift card id", notes = "", response = PaymentInstrument.class, responseContainer = "")
	public Response rechargeGiftCard(@PathParam("giftCardId") Long id, @PathParam("amount") Long amount) {
		User user = getLoggedInUser();
		if(user == null)
			throw new HNIException("You must have elevated permissions to do this.");
		return Response.ok(giftCardServiceHelper.rechargeGiftCard(id, amount, user), MediaType.APPLICATION_JSON).build();
	}
}
