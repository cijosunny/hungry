package org.hni.organization.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hni.common.om.Role;
import org.hni.user.om.User;

/**
 * Allows the system to associate any user to any organization with any role
 * @author jeff3parker
 *
 */
@Embeddable
public class UserOrganizationRolePK implements Serializable {

	private static final long serialVersionUID = 8903197877766551557L;

	@Column(name="user_id")	private Long userId;
	@Column(name="organization_id")	private Long orgId;
	@Column(name="role_id")	private Long roleId;

	public UserOrganizationRolePK() {}
	public UserOrganizationRolePK(Long userId, Long orgId, Long roleId) {
		this.userId = userId;
		this.orgId = orgId;
		this.roleId = roleId;
	}
	public UserOrganizationRolePK(User user, Organization org, Role role) {
		this(user.getId(), org.getId(), role.getId());
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOrganizationRolePK other = (UserOrganizationRolePK) obj;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
}
