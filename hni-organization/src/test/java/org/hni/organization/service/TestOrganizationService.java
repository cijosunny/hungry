/*package org.hni.organization.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.organization.om.Organization;
import org.hni.organization.service.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestOrganizationService {

	@Inject private OrganizationService organizationService;
	
	public TestOrganizationService() {
		BasicConfigurator.configure();
	}

	@Test
	public void getOrg() {
		Organization org = organizationService.get(2L);
		assertNotNull(org);
		assertEquals("Samaritan House", org.getName() );
	}
	
	@Test
	public void testAddOrg() {
		Organization org = new Organization();
		org.setName("Test org");
		organizationService.save(org);
		
		Organization orgValidate = organizationService.get(org.getId());
		assertNotNull(orgValidate);		
	}

	@Test
	public void testGetAll() {
		List<Organization> list = organizationService.getAll();
		assertEquals(3, list.size());
	}
}
*/