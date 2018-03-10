package org.hni.admin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.converter.HNIConverter;
import org.hni.common.Constants;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.utils.HNISecurityUtils;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.type.HNIRoles;
import org.hni.user.om.Address;
import org.hni.user.om.Client;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewSerializer;

public class AbstractBaseController {

	private static final String EMPTY = "";
	protected static final String SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN = "Something went wrong, please try again";
	protected static final String ERROR_MSG = "errorMsg";
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static final String RESPONSE = "response";
	protected static final String USER_NAME = "userName";
	protected static final String DATA = "data";
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static Map<String, String> tempDataStore = new HashMap<>();
	@Inject
	protected OrganizationUserService organizationUserService;

	protected ObjectMapper mapper = new ObjectMapper();
	protected SimpleModule module = new SimpleModule();

	@PostConstruct
	public void configureJackson() {
		module.addSerializer(JsonView.class, new JsonViewSerializer());
		mapper.registerModule(module);
	}

	protected User getLoggedInUser() {
		Long userId = (Long) ThreadContext.get(Constants.USERID);
		return organizationUserService.get(userId);
	}

	protected boolean isPermitted(String domain, String permission, Long id) {
		if (SecurityUtils.getSubject().isPermitted(createPermission(domain, permission, id))) {
			return true;
		}
		return false;

	}

	private String createPermission(String domain, String action, Long instance) {
		return String.format("%s:%s:%d", domain, action, instance);
	}

	protected Response createResponse(String message, Response.Status status) {
		return Response.status(status).entity(String.format("{\"message\":\"%s\"}", message.toString()))
				.type(MediaType.APPLICATION_JSON).build();
	}

	protected Response createResponse(String message) {
		return createResponse(message, Response.Status.OK);
	}

	protected User setPassword(User user) {
		if (user != null) {
			user.setCreated(new Date());
			user.setSalt(HNISecurityUtils.getSalt());
			user.setHashedSecret(HNISecurityUtils.getHash(user.getPassword(), user.getSalt()));
		}
		return user;
	}
	
	protected Long convertUserTypeToRole(String type) {
		if (type.equalsIgnoreCase("ngo")) {
			return HNIRoles.NGO.getRole();
		} else if (type.equalsIgnoreCase("volunteer")) {
			return HNIRoles.VOLUNTEERS.getRole();
		} else if (type.equalsIgnoreCase("client")) {
			return HNIRoles.CLIENT.getRole();
		}
		return HNIRoles.USER.getRole();
	}

	protected Set<Address> getAddressSet(Set<Address> userAddress, Address address) {
		Long id = null;
		if (userAddress != null && !userAddress.isEmpty()) {
			id = userAddress.iterator().next().getId();
		}
		address.setId(id);
		Set<Address> addresses = new HashSet<>(1);
		addresses.add(address);
		
		return addresses;
	}
	
	protected String getRoleName(Long roleId) {
		if (roleId.equals(HNIRoles.SUPER_ADMIN.getRole())) {
			return "Super Admin";
		} else if (roleId.equals(HNIRoles.NGO_ADMIN.getRole())) {
			return "NGOAdmin";
		} else if (roleId.equals(HNIRoles.NGO.getRole())) {
			return "NGO";
		} else if (roleId.equals(HNIRoles.VOLUNTEERS.getRole())) {
			return "Volunteer";
		} else if (roleId.equals(HNIRoles.CLIENT.getRole())) {
			return "Client";
		} else {
			return "User";
		}
	}
	
	
	protected String getInitialData(User user, Long role) throws JsonProcessingException {
		String initialProfileData = "{}";
		if (role.equals(HNIRoles.NGO_ADMIN.getRole()) && role.equals(HNIRoles.NGO.getRole())) {
			ObjectNode parentJSON = mapper.createObjectNode();
			ObjectNode overViewNode = mapper.createObjectNode();
			if (user != null) {
				overViewNode.set("address", HNIConverter.getAddress(mapper.createObjectNode(), user.getAddresses()));
				overViewNode.put("name", user.getFirstName() + " " + user.getLastName());
				overViewNode.put("mobilePhone", user.getMobilePhone());
				overViewNode.put("genderCode", user.getGenderCode());				 
			}
			parentJSON.set("overview", overViewNode);
			
			initialProfileData = mapper.writeValueAsString(mapper.writeValueAsString(parentJSON));
			
		} else if (role.equals(HNIRoles.VOLUNTEERS.getRole())) {
			Volunteer volunteer = new Volunteer();
			volunteer.setUser(maskUserInfo(user));
			volunteer.setAddress(createAddress(user.getAddresses()));
			initialProfileData = mapper.writeValueAsString(volunteer);
		} else if (role.equals(HNIRoles.CLIENT.getRole())) {
			Client client = new Client();
			client.setUser(maskUserInfo(user));
			client.setAddress(createAddress(user.getAddresses()));
			initialProfileData = mapper.writeValueAsString(client);
		} else {
			initialProfileData = EMPTY;
		}
		return initialProfileData;
	}
	
	protected Address createAddress(Set<Address> addresses) {
		Address addr = new Address();
		
		if (addresses != null && !addresses.isEmpty()) {
			addr = addresses.iterator().next();
		}
		
		return addr;
	}
	
	private User maskUserInfo(User user) {
		user.setId(null);
		
		return user;
	}

	public String getValueFromDataStore(String key) {
		
		return tempDataStore.get(key);
	}
	
	public String putValueToDataStore(String key, String value) {
		
		return tempDataStore.put(key, value);
	}
	
	protected String formatInvitationMessageWithPhoneNumber(String message, List<SmsProvider> smsProviders) {
		message = message != null ? message : "";
		String phoneNumber = "314-300-0305";
		if (smsProviders != null && !smsProviders.isEmpty()) {
			phoneNumber = smsProviders.get(0).getLongCode();
			try {
				phoneNumber = HNIConverter.convertPhoneNumberToUiFormat(phoneNumber);
			} catch (Exception e) {
				// TODO: Find alternative solution for phone number displaying
			}
		}
		
		message += "<br /> Once you have completed the registration, you will be able to text to this number everyday from June 1st to September 1st to order meals: <b>" +
				phoneNumber
				+ "</b>."+
				"<br /><br />To place an order text: <b>HUNGRY</b> "+
			"<br /><br />For questions email hunger@notimpossiblelabs.com";
		return message;
	}
	
    
    public void setUserRequestState(String fromState) {
    	ThreadContext.put(Constants.STATE, fromState);
	}
}
