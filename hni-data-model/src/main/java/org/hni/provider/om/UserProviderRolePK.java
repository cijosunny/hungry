package org.hni.provider.om;

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
public class UserProviderRolePK implements Serializable {

	private static final long serialVersionUID = 8903197877766551557L;

	@Column(name="user_id")	private Long userId;
	@Column(name="provider_id")	private Long providerId;
	@Column(name="role_id")	private Long roleId;

	public UserProviderRolePK() {}
	public UserProviderRolePK(Long userId, Long providerId, Long roleId) {
		this.userId = userId;
		this.providerId = providerId;
		this.roleId = roleId;
	}
	public UserProviderRolePK(User user, Provider provider, Role role) {
		this(user.getId(), provider.getId(), role.getId());
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getProviderId() {
		return providerId;
	}
	public void setProviderId(Long providerId) {
		this.providerId = providerId;
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
		result = prime * result + ((providerId == null) ? 0 : providerId.hashCode());
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
		UserProviderRolePK other = (UserProviderRolePK) obj;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
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
