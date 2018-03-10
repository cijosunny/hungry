package org.hni.security.service;

import java.util.List;

import org.hni.common.service.BaseService;
import org.hni.security.om.ActivationCode;
import org.hni.user.om.User;

public interface ActivationCodeService extends BaseService<ActivationCode> {

	/**
	 * Given a 6 digit activation code, retrieve an ActivationCode entry from database
	 * @param authCode
	 * @return
	 */
	ActivationCode getByActivationCode(String authCode);

	/**
	 * Validate whether an activation code is valid or not
	 * @param id
	 * @return
	 */
	boolean validate(String authCode);
	
	/**
	 * Return active validation codes for a user
	 * @param user
	 * @return
	 */
	List<ActivationCode> getByUser(User user);
	
	/**
	 * Create a 6 digit activation code, Saving ActivationCode entries to database
	 * @param user
	 * @param dependentClient
	 * @return
	 */
	List<ActivationCode> saveActivationCodes(User user, int dependentClient);
}
