/*package org.hni.organization.service;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.common.om.Role;
import org.hni.organization.om.Organization;
import org.hni.user.om.User;
import org.hni.user.om.type.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestOrganizationUserService {

	@Inject private OrganizationService organizationService;
	@Inject private OrganizationUserService orgUserService;
	
	public TestOrganizationUserService() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testAddUserToOrg() {
		User user = new User("Bill", "", "479-123-4567");
		user.setGender(Gender.MALE);
		orgUserService.save(user);

		Organization org = organizationService.get(2L);
		Role role = Role.get(5L);
		orgUserService.save(user, org,role);
		
		Collection<User> users = orgUserService.getAllUsers(org); // the USER role
		assertEquals(3, users.size());
	}

}
*/