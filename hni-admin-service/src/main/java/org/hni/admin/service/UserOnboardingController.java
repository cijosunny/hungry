package org.hni.admin.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.common.Constants;
import org.hni.organization.om.Organization;
import org.hni.service.helpers.UserOnboardingServiceHelper;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.hni.user.service.UserOnboardingService;
import org.hni.user.service.UserPartialCreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;

@Api(value = "/onboard", description = "Operations on NGO")
@Component
@Path("/onboard")
public class UserOnboardingController extends AbstractBaseController {

	private static final String ORG_ID = "orgId";

	private static final Logger _LOGGER = LoggerFactory.getLogger(UserOnboardingController.class);

	private static final String FIRST_NAME = "firstName";

	@Inject
	private UserOnboardingService userOnBoardingService;

	@Inject
	private UserDAO userDao;

	@Inject
	private UserPartialCreateService userPartialCreateService;

	@Inject
	private UserOnboardingServiceHelper onBoardingServiceHelper;
	
	@POST
	@Path("/ngo/invite")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response sendNGOActivationLink(Organization org) {
		_LOGGER.info("Request reached to create NGO invitation and organization creation");
		return Response.ok().entity(onBoardingServiceHelper.inviteNGO(org, getLoggedInUser())).build();
	}

	@POST
	@Path("/{userType}/user/invite")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response sendNGOActivationLinkToUser(@PathParam("userType") String userType, Invitation inviteRequest) {
		_LOGGER.info("Request reached to send activation link  for role {0} ", userType);
		return Response.ok().entity(onBoardingServiceHelper.inviteUser(inviteRequest, getLoggedInUser(), userType)).build();
	}

	@GET
	@Path("/activate/{userType}/{invitationCode}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> activateNGO(@PathParam("userType") String userType,
			@PathParam("invitationCode") String invitationCode)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		List<Invitation> invitations = (List<Invitation>) userOnBoardingService.validateInvitationCode(invitationCode);

		if (!invitations.isEmpty()) {
			Invitation invite = invitations.get(0);
			
			map.put(RESPONSE, SUCCESS);
			map.put(ORG_ID, invite.getOrganizationId());
			map.put(USER_NAME, invite.getEmail());
			//map.put(DATA, invite.getData());
			map.put(Constants.PHONE, invite.getPhone());
			map.put(FIRST_NAME, invite.getName());
			map.put("dependants", invite.getDependantsCount() != null ? String.valueOf(invite.getDependantsCount()) : null);

			return map;
		}
		return map;
	}

	@POST
	@Path("/{userType}/save")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Map<String, String> savePartialData(JSONObject json, @PathParam("userType") String userType) {
		Map<String, String> response = new HashMap<>();
		response.put(RESPONSE, ERROR);
		if (userType != null && json != null) {
			UserPartialData userPartialDataUpdate = userPartialCreateService
					.getUserPartialDataByUserId(getLoggedInUser().getId());
			if (userPartialDataUpdate == null) {
				UserPartialData userPartialData = new UserPartialData();
				userPartialData.setType(userType);
				userPartialData.setUserId(getLoggedInUser().getId());
				userPartialData.setData(json.toString());
				userPartialData.setCreated(new Date());
				userPartialData.setLastUpdated(new Date());
				userPartialData.setStatus(Constants.N);
				userPartialCreateService.save(userPartialData);
				response.put(RESPONSE, SUCCESS);
			} else {
				userPartialDataUpdate.setData(json.toString());
				userPartialDataUpdate.setLastUpdated(new Date());
				userPartialCreateService.save(userPartialDataUpdate);
				response.put(RESPONSE, SUCCESS);
			}
		}
		return response;
	}

	@POST
	@Path("/ngo/ngoSave")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response ngoSave(String onboardDataJson) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode objectNode = mapper.readTree(onboardDataJson);
		Map<String, String> map = new HashMap<>();
		map.put(RESPONSE, ERROR);
		try {

			Map<String, String> errors = userOnBoardingService.ngoSave((ObjectNode) objectNode, getLoggedInUser());
			if (errors != null && errors.isEmpty()) {
				map.put(RESPONSE, SUCCESS);
			} else {
				if (map != null)
					map.putAll(errors);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(map).build();
	}

	@POST
	@Path("/validate/username")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response isUserNameAvailable(Map<String, String> userNameInfo) {
		Map<String, String> response = new HashMap<>();
		response.put("available", "false");
		try {
			User userInfo = userDao.byEmailAddress(userNameInfo.get("username"));
			if (userInfo == null) {
				response.put("available", "true");
			}
		} catch (Exception e) {
			_LOGGER.error("Exception while processing request @ isUserNameValid", e);
			response.put(ERROR, SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN);
		}
		return Response.ok(response).build();
	}

	@GET
	@Path("/ngo/get/{ngoId}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "", notes = "", response = Map.class, responseContainer = "")
	public Response getNGO(@PathParam("ngoId") Long ngoId) {
		Map<String, String> response = new HashMap<>();
		response.put(RESPONSE, ERROR);
		try {
			ObjectNode ngoDetailJSON = userOnBoardingService.getNGODetail(ngoId, null);
			return Response.ok(ngoDetailJSON).build();
		} catch (Exception e) {
			e.printStackTrace();
			response.put(ERROR, SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN);
		}
		return Response.ok(response).build();
	}

}
