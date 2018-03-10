package org.hni.events.service;

import org.apache.log4j.BasicConfigurator;
import org.hni.common.Constants;
import org.hni.common.om.Role;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.om.ActivationCode;
import org.hni.security.service.ActivationCodeService;
import org.hni.user.om.User;
import org.hni.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 *
 */
// @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@Transactional
public class TestCustomerService {
    @Inject
    @Qualifier("customerService")
    private UserService customerService;

    @Inject
    private ActivationCodeService activationCodeService;

    @Inject
    @Qualifier("orgUserService")
    private OrganizationUserService organizationUserService;

    @Before
    public void setUp() {
        BasicConfigurator.configure();
    }

    /**
     * Happy Path for registering customer
     */
    // @Test
    public void testRegisterCustomer() {
        User user = new User();
        user.setFirstName("Sourabh");
        user.setLastName("Taletiya");
        user.setMobilePhone("9876543210");
        user.setEmail("sourabh.taletiya// @Test.com");
        String authCode = "123456";

        customerService.registerCustomer(user, authCode);
        ActivationCode activationCode = activationCodeService.getByActivationCode(authCode);
        assertThat(activationCode.getUser().getId(), is(user.getId()));
        assertThat(activationCode.getUser().getMobilePhone(), is("9876543210"));
    }

    /**
     * Critical case if user is already registered than it should not register
     * The USER table will not get updated
     * But authcode would be associated with customer
     */
    // @Test
    public void testRegisterSameCustomer() {
        User user = customerService.get(3l);
        String authCode = "123456";
        customerService.registerCustomer(user, authCode);

        assertThat(customerService.byMobilePhone("479-555-4321").size(), is(1));
        ActivationCode activationCode = activationCodeService.getByActivationCode(authCode);
        assertThat(user.getId(), is(activationCode.getUser().getId()));
        List<UserOrganizationRole> uors = organizationUserService.getUserOrganizationRoles(user).stream()
                .filter(userOrganizationRole -> userOrganizationRole.getId().getRoleId().equals(Role.get(Constants.CLIENT).getId()))
                .filter(userOrganizationRole -> userOrganizationRole.getId().getOrgId().equals(activationCode.getOrganizationId()))
                .collect(Collectors.toList());
        // assert that UOR table gets the mapping
        assertThat(uors.size(), is(1));
    }

    /**
     * Register customer with multiple auth code
     */
    // @Test
    public void testRegisterCustomerWithMultipleAuthCode() {
        User user = customerService.get(3l);
        String authCode = "123456";
        customerService.registerCustomer(user, authCode);
        ActivationCode activationCode = activationCodeService.getByActivationCode(authCode);
        List<UserOrganizationRole> uors = organizationUserService.getUserOrganizationRoles(user).stream()
                .filter(userOrganizationRole -> userOrganizationRole.getId().getRoleId().equals(Role.get(Constants.CLIENT).getId()))
                .filter(userOrganizationRole -> userOrganizationRole.getId().getOrgId().equals(activationCode.getOrganizationId()))
                .collect(Collectors.toList());
        //assert Activation code table
        assertThat(activationCode.getUser().getId(), is(user.getId()));

        // assert UORS table
        assertThat(uors.size(), is(1));
    }

}
