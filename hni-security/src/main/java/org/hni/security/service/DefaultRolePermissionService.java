package org.hni.security.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.security.dao.RolePermissionDAO;
import org.hni.security.om.Permission;
import org.hni.security.om.RolePermission;
import org.hni.security.om.RolePermissionPK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultRolePermissionService extends AbstractService<RolePermission> implements RolePermissionService {
	private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);
	private RolePermissionDAO rolePermissionDao;

	@Inject
	public DefaultRolePermissionService(RolePermissionDAO rolePermissionDao) {
		super(rolePermissionDao);
		this.rolePermissionDao = rolePermissionDao;
	}

	@Override
	public Set<Permission> byRoleId(Long roleId, Long organizationId) {
		List<Permission> permissions = rolePermissionDao.byRoleId(roleId);
		Set<Permission> permissionSet = new TreeSet<Permission>();

		for (Permission permission : permissions) {
			RolePermissionPK rolePermissionPK = new RolePermissionPK();
			rolePermissionPK.setPermissionId(permission.getId());
			rolePermissionPK.setRoleId(roleId);
			RolePermission rolePermission = get(rolePermissionPK);
			if (!rolePermission.isAllInstances()) {
				permission.setInstance(String.valueOf(organizationId));
			}
			permissionSet.add(permission);
		}
		return permissionSet;
	}
}