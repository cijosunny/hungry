package org.hni.security.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.security.om.Permission;
import org.hni.security.om.RolePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultRolePermissionDAO extends AbstractDAO<RolePermission> implements RolePermissionDAO {
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionDAO.class);

	public DefaultRolePermissionDAO() {
		super(RolePermission.class);
	}

	@Override
	public List<Permission> byRoleId(Long roleId) {
		try {
			Query q = em.createQuery(
					"SELECT y FROM RolePermission x, Permission y WHERE x.id.roleId = :roleId and x.id.permissionId = y.id").setParameter(
					"roleId", roleId);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<RolePermission> byRole(Long roleId) {
		try {
			Query q = em.createQuery("SELECT x FROM RolePermission x WHERE x.id.roleId = :roleId")
						.setParameter("roleId", roleId);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}