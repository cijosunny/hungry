package org.hni.organization.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.common.om.Role;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.om.HniServices;
import org.hni.user.om.User;

public interface UserOrganizationRoleDAO extends BaseDAO<UserOrganizationRole> {

	Collection<UserOrganizationRole> getByRole(Organization org, Role user);
	Collection<UserOrganizationRole> get(User user);
	Collection<UserOrganizationRole> byRole(Role role);
	Collection<HniServices> getHniServicesByRole(Long orgId, Long roleId);
	boolean getProfileStatus(User user);
}
