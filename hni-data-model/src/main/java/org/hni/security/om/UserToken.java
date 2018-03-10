package org.hni.security.om;

import java.util.Set;

public class UserToken {
	private String userIdentifier;
	private Long organiationId;
	private Long createTime;
	private Long expirationTime;
	private Set<OrganizationUserRolePermission> organizationUserRolePermissions;

	public Long getOrganiationId() {
		return organiationId;
	}

	public void setOrganiationId(Long organiationId) {
		this.organiationId = organiationId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public Set<OrganizationUserRolePermission> getOrganizationUserRolePermissions() {
		return organizationUserRolePermissions;
	}

	public void setOrganizationUserRolePermissions(Set<OrganizationUserRolePermission> organizationUserRolePermissions) {
		this.organizationUserRolePermissions = organizationUserRolePermissions;
	}
}
