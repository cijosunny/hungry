package org.hni.events.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.common.Constants;
import org.hni.common.om.Role;
import org.hni.common.service.AbstractService;
import org.hni.organization.om.Organization;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.om.ActivationCode;
import org.hni.security.service.ActivationCodeService;
import org.hni.security.utils.HNISecurityUtils;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.hni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Component("customerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CustomerService extends AbstractService<User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDAO userDao;
    private OrganizationUserService orgUserService;
    private ActivationCodeService activationCodeService;

    @Inject
    public CustomerService(UserDAO userDao, OrganizationUserService orgUserService, ActivationCodeService activationCodeService) {
        super(userDao);
        this.userDao = userDao;
        this.orgUserService = orgUserService;
        this.activationCodeService = activationCodeService;
    }


    /**
     * This method registers customer and maps customer with the authCode.
     * Maps User with Activation_Codes and User_Organization_Role
     * This also helps you register multiple authCode
     */
    public User registerCustomer(User user, String authCode) {
        // Creating customer in USER table
        user = save(user);
        // get Activation_Code record
        ActivationCode activationCode = activationCodeService.getByActivationCode(authCode);
        // Set User
        if (activationCode != null && activationCode.getUser() == null) {
            activationCode.setUser(user);
            // Update Activation code
            activationCode = activationCodeService.save(activationCode);
            //update User_Organization_role
            Organization organization = new Organization();
            organization.setId(activationCode.getOrganizationId());
            user = orgUserService.save(user, organization, Role.get(Constants.CLIENT));
        }
        return user;
    }


    /**
     * Saves customer
     */
    @Override
    public User save(User customer) {
        List<User> customers = byMobilePhone(customer.getMobilePhone());
        if (customers.isEmpty()) {
            return super.save(customer);
        } else {
            // return the first customer from list
            // ideally there should be only one customer per mobile number
            return customers.get(0);
        }
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
		// TODO Auto-generated method stub
		return null;
	}

}
