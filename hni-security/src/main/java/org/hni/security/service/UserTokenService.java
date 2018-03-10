package org.hni.security.service;

import java.util.Set;

import org.hni.security.om.OrganizationUserRolePermission;
import org.hni.security.om.UserAccessControls;
import org.hni.user.om.User;

import io.jsonwebtoken.Claims;

public interface UserTokenService {
	public static final String TOKEN_HEADER = "x-hni-token";

	// TODO: make these values dynamically injected
	public static final String KEY = "YbpWo521Z/aF7DqpiIpIHQ==";
	public static final String ISSUER = "test-issuer";

	/**
	 * retrieve claims from token
	 */
	Claims getClaimsFromToken(String token);

	/**
	 * retrieve user id from claims
	 */
	Long getUserIdFromClaims(Claims claims);


	/**
	 * retrieve permissions from claims
	 */
	UserAccessControls getPermissionsFromClaims(Claims claims);

	/**
	 * retrieve permissions from token
	 */
	UserAccessControls getPermissionsFromToken(String token);

	/**
	 * retrieve permissions based on user and organization id
	 */
	Set<OrganizationUserRolePermission> getUserOrganizationRolePermissions(User user, Long organizationId);

}
