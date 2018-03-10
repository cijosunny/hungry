package org.hni.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.hni.common.service.AbstractService;
import org.hni.security.dao.ActivationCodeDAO;
import org.hni.security.om.ActivationCode;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultActivationCodeService extends AbstractService<ActivationCode> implements ActivationCodeService {
	private static final Logger logger = LoggerFactory.getLogger(ActivationCodeService.class);
	private ActivationCodeDAO activationCodeDao;

	private static final int LARGE_BASE = 100000;
	private static final int LARGE_PRIME = 999983;
	private static final int OFFSET = 151647;
	private static Integer ACTIVATION_CODE_START_IDX = 0;
	private static boolean NEXT_ACTIVATION_CODE = false; 
	
	@Inject
	public DefaultActivationCodeService(ActivationCodeDAO activationCodeDao) {
		super(activationCodeDao);
		this.activationCodeDao = activationCodeDao;
	}

	@Override
	public boolean validate(String authCode) {

		ActivationCode code = getByActivationCode(authCode);
		return code != null
			&& code.getUser() == null
            && code.getOrganizationId() != null
			&& code.isEnabled()
			&& code.getMealsRemaining() > 0
			&& code.getMealsAuthorized() > 0;
	}

	@Override
    public ActivationCode getByActivationCode(String authCode) {
        return activationCodeDao.getByActivationCode(authCode);
    }

	@Override
	public List<ActivationCode> getByUser(User user) {
		return activationCodeDao.getByUser(user);
	}
    /**
     * WARNING This implementation WILL NOT work with JPA generated Ids. This method was implemented
     * with the assumption that authCodes are already available, and decoded values of authCodes will be
     * inserted to the database manually
     * @param authCodeId
     * @return
     */
	public Long encode(String authCodeId) {
        byte[] authCodeIdBytes = authCodeId.getBytes();
        String authCodeStr = new String(Base64.decodeBase64(authCodeIdBytes));
        return Long.valueOf(authCodeStr);
	}

	public String decode(Long authCode) {
        byte[] authCodeStr = String.valueOf(authCode).getBytes();
        return new String(Base64.encodeBase64(authCodeStr));
	}

	@Override
	public List<ActivationCode> saveActivationCodes(User user, int dependentClient) {
		int dependentClientActivated = (dependentClient + 1) * 2;
		List<ActivationCode> activationCodes = new ArrayList<>(dependentClientActivated);
		for (int i = 0; i < dependentClientActivated; i++) {
			ActivationCode actCode = new ActivationCode();
			if(!NEXT_ACTIVATION_CODE){
				ACTIVATION_CODE_START_IDX = Integer.parseInt(activationCodeDao.getNextActivationCode());
				NEXT_ACTIVATION_CODE = true;
			}
			actCode.setActivationCode(Integer.toString(++ACTIVATION_CODE_START_IDX));
			actCode.setUser(user);
			actCode.setMealsAuthorized(180L);
			actCode.setMealsRemaining(180L);
			actCode.setEnabled(true); 
			actCode.setOrganizationId(user.getOrganizationId());
			actCode.setCreated(new Date());
			activationCodeDao.save(actCode);
			activationCodes.add(actCode);
		}
		return activationCodes;
	}
}