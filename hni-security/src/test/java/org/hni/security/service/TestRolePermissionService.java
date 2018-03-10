package org.hni.security.service;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.security.om.RolePermission;
import org.hni.security.om.RolePermissionPK;
import org.hni.security.service.RolePermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@Transactional
public class TestRolePermissionService {
	@Inject
	private RolePermissionService rolePermissionService;

	public TestRolePermissionService() {
		BasicConfigurator.configure();
	}

	@Test
	public void testGet() {
		RolePermissionPK rolePermissionPK = new RolePermissionPK(1L, 1L);
		RolePermission rolePermission = rolePermissionService.get(rolePermissionPK);
		assertTrue(1L == rolePermission.getId().getRoleId());
		assertTrue(1L == rolePermission.getId().getPermissionId());
	}
}