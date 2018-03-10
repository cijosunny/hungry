package org.hni.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.hni.common.HNIUtils;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("defaultUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserService extends AbstractService<User> implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private UserDAO userDao;

	@Inject
	public DefaultUserService(UserDAO userDao) {
		super(userDao);
		this.userDao = userDao;
	}

	@Override
	public List<User> byMobilePhone(String byMobilePhone) {
		return userDao.byMobilePhone(byMobilePhone);
	}

	@Override
	public List<User> byLastName(String lastName) {
		return userDao.byLastName(lastName);
	}

	@Override
	public User byEmailAddress(String emailAddress) {
		return userDao.byEmailAddress(emailAddress);
	}

	@Override
	public Map<String, String> changePasswordFor(User user, Map<String, String> credentialInfo, String answer) {
		Map<String, String> result = new HashMap<>(2);
		if (credentialInfo.get("password") != null 
				&& credentialInfo.get("oldPassword") != null
				&& credentialInfo.get("answer") != null) {
			
			if (answer.equalsIgnoreCase(credentialInfo.get("answer"))) {
				User actualUser = userDao.get(user.getId());
				
				String hashForOldPassword = HNIUtils.getHash(credentialInfo.get("oldPassword"), actualUser.getSalt());
				
				if (StringUtils.equals(hashForOldPassword, user.getHashedSecret())) {
					actualUser.setSalt(HNIUtils.getSalt());
					actualUser.setHashedSecret(HNIUtils.getHash(credentialInfo.get("password"), actualUser.getSalt()));
					try {
						userDao.update(actualUser);
						result.put("success", "Password has been updated successfully");
					} catch (Exception e) {
						result.put("error", "Failed to update password, please try again");
						logger.error("Exception while updating user password", e);
					}
				} else {
					result.put("error", "Incorrect old password, please try again");
				}
			} else  {
				result.put("error", "Incorrect answer for security question");
			}
			
			
		} else {
			result.put("error", "You are missing one of the required field in the submitted request, Please try again");
		}
		
		return result;
	}

}
