package org.hni.security.service;

import java.util.Set;

import org.hni.common.service.BaseService;
import org.hni.security.om.Permission;
import org.hni.security.om.RolePermission;

public interface RolePermissionService extends BaseService<RolePermission> {
	Set<Permission> byRoleId(Long roleId, Long organizationId);
}
