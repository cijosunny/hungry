package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.user.om.User;
import org.hni.user.om.type.Gender;
import org.hni.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestUserService {

	@Inject private UserService userService;
	
	public TestUserService() {
		BasicConfigurator.configure();
	}
	
	//@Test
	public void testLookupByPhone() {
		List<User> list = userService.byMobilePhone("479-555-1212");
		assertEquals(1, list.size());
	}

	//@Test
	public void testLookupByLastName() {
		List<User> list = userService.byLastName("Multiphone");
		assertEquals(2, list.size());
	}
	
	//@Test
	public void testAddUser() {
		User user = new User("Scott", "", "479-123-4567");
		user.setGender(Gender.MALE);
		userService.save(user);
		
		List<User> list = userService.byMobilePhone("479-123-4567");
		assertEquals(1, list.size());
		User cust = list.get(0);
		assertEquals("Scott", cust.getFirstName());
	}

	//@Test
	public void testPhoneNumberValidation() {
		User user = new User();
		user.setEmail("johndoe@gmail.com");
		user.setMobilePhone("1234567890");
		assertTrue(userService.validate(user));

		user.setMobilePhone("8888888888");
		assertTrue(userService.validate(user));

		user.setMobilePhone("1");
		assertFalse(userService.validate(user));

		user.setMobilePhone("nondigits");
		assertFalse(userService.validate(user));
	}

	//@Test
	public void testEmailValidation() {
		User user = new User();
		user.setEmail("None");
		user.setMobilePhone("1234567890");
		assertTrue(userService.validate(user));

		user.setEmail("johndoe@gmail.com");
		assertTrue(userService.validate(user));
	}
}