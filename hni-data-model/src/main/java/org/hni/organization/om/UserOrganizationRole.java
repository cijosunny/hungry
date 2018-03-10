package org.hni.organization.om;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.common.om.Role;
import org.hni.user.om.User;

/**
 * Represents an organization which could be an NGO, School or other entity
 * providing support and users to the system.
 * 
 * @author jeff3parker
 *
 */
@Entity
@Table(name = "user_organization_role")
public class UserOrganizationRole implements Persistable, Serializable, Comparable<UserOrganizationRole> {

	private static final long serialVersionUID = 2755700987714341746L;

	@EmbeddedId
	private UserOrganizationRolePK id;

	public UserOrganizationRole() {
	}

	public UserOrganizationRole(UserOrganizationRolePK id) {
		this.id = id;
	}

	public UserOrganizationRole(User user, Organization org, Role role) {
		this(new UserOrganizationRolePK(user, org, role));
	}

	@Override
	public UserOrganizationRolePK getId() {
		return this.id;
	}

	public void setId(UserOrganizationRolePK id) {
		this.id = id;
	}

	@Override
	public int compareTo(UserOrganizationRole o) {
		int result = 0;
		if (null != this.getId() && null != o.getId()) {
			result = this.getId().getUserId().compareTo(o.getId().getUserId());
			if (0 == result) {
				result = this.getId().getRoleId().compareTo(o.getId().getRoleId());
				if (0 == result) {
					result = this.getId().getOrgId().compareTo(o.getId().getOrgId());
				}
			}
		}
		return result;
	}
}