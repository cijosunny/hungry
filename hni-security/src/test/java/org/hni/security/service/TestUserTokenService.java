package org.hni.security.service;

import io.jsonwebtoken.Claims;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.hni.common.Constants;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.om.OrganizationUserRolePermission;
import org.hni.security.om.Permission;
import org.hni.security.realm.token.JWTTokenFactory;
import org.hni.user.om.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@Transactional
public class TestUserTokenService extends TestCase {
	private static final Long TTL_MILLIS = 3600000L;
	@Inject
	private UserTokenService userTokenService;

	@Inject
	private OrganizationUserService userService;

	private static final String TOKEN = JWTTokenFactory.encode(UserTokenService.KEY, UserTokenService.ISSUER, "", TTL_MILLIS, 1L,
			getPermissions());

	@Test
	public void testClaimsFromToken() {
		Claims claims = userTokenService.getClaimsFromToken(TOKEN);
		assertTrue(!claims.isEmpty());
		assertTrue(claims.containsKey(Constants.PERMISSIONS));
		assertTrue(claims.containsKey("exp"));
		assertTrue(claims.containsKey("userId"));
	}



	@Test
	public void testUserIdFromClaims() {
		Claims claims = userTokenService.getClaimsFromToken(TOKEN);
		Long userId = userTokenService.getUserIdFromClaims(claims);
		assertTrue(userId.equals(1L));
	}

	@Test
	public void testGetAuthorizations() {
		User user = userService.get(1L);
		Set<OrganizationUserRolePermission> permissions = userTokenService.getUserOrganizationRolePermissions(user, 2L);
		assertTrue(!permissions.isEmpty());
	}

	private static Permission getPermission() {
		Permission permission = new Permission();
		permission.setDomain("organizations");
		permission.setValue("*");
		return permission;
	}

	private static final Set<OrganizationUserRolePermission> getOrgPermissions() {
		Set<OrganizationUserRolePermission> orgPermissions = new TreeSet<OrganizationUserRolePermission>();
		OrganizationUserRolePermission orgPermission = new OrganizationUserRolePermission();
		orgPermission.setOrganizationId(2L);
		orgPermission.setRoleId(1L);
		orgPermission.setUserId(1L);
		Set<Permission> permissions = new TreeSet<Permission>();

		permissions.add(getPermission());
		orgPermission.setPermissions(permissions);
		orgPermissions.add(orgPermission);
		return orgPermissions;
	}

	private static final String getPermissions() {

		ObjectMapper objectMapper = new ObjectMapper();
		String permissionsString = "";
		try {
			permissionsString = objectMapper.writeValueAsString(getOrgPermissions());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return permissionsString;
	}
}