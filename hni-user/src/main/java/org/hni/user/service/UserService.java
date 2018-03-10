package org.hni.user.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hni.common.service.BaseService;
import org.hni.user.om.User;

public interface UserService extends BaseService<User> {

	/**
	 * Validates user fields if present
	 */
	default boolean validate(User user) {
		if (user == null) {
			return false;
		}
		if (user.getMobilePhone() != null
				&& (!StringUtils.isNumeric(user.getMobilePhone()) || user.getMobilePhone().length() != 10)) {
			return false;
		}
		if (user.getEmail() != null && (!"none".equalsIgnoreCase(user.getEmail())
				&& !EmailValidator.getInstance().isValid(user.getEmail()))) {
			return false;
		}

		return true;
	}

	default User registerCustomer(User user, String authCode) {
		// do nothing
		return user;
	}

	List<User> byMobilePhone(String byMobilePhone);

	List<User> byLastName(String lastName);

	User byEmailAddress(String emailAddress);

	default User register(User user, Long userType) {
		return user;
	}

	Map<String, String> changePasswordFor(User user, Map<String, String> credentialInfo, String answer);
	

}
