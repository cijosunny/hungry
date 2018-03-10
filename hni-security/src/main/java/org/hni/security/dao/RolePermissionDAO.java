package org.hni.security.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.security.om.Permission;
import org.hni.security.om.RolePermission;

public interface RolePermissionDAO extends BaseDAO<RolePermission> {
	List<Permission> byRoleId(Long roleId);

	List<RolePermission> byRole(Long roleId);
}
