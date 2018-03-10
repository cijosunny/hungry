package org.hni.security.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hni.common.om.Role;

/**
 * Allows the system to associate any user to any organization with any role
 * 
 * @author jeff3parker
 *
 */
@Embeddable
public class RolePermissionPK implements Serializable {

	private static final long serialVersionUID = 8903197877766551557L;

	@Column(name = "role_id")
	private Long roleId;
	@Column(name = "permission_id")
	private Long permissionId;

	public RolePermissionPK() {
	}

	public RolePermissionPK(Long roleId, Long permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public RolePermissionPK(Role role, Permission permission) {
		this(role.getId(), permission.getId());
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
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
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
		RolePermissionPK other = (RolePermissionPK) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
			return false;
		return true;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long perimssionId) {
		this.permissionId = perimssionId;
	}
}