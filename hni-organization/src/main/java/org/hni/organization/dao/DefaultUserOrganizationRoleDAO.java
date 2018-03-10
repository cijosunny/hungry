package org.hni.organization.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.common.om.Role;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.om.HniServices;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserOrganizationRoleDAO extends AbstractDAO<UserOrganizationRole> implements UserOrganizationRoleDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserOrganizationRoleDAO.class);
	
	public DefaultUserOrganizationRoleDAO() {
		super(UserOrganizationRole.class);
	}

	@Override
	public Collection<UserOrganizationRole> getByRole(Organization org, Role role) {
		try {
			Query q = em.createQuery("SELECT x FROM UserOrganizationRole x WHERE x.id.orgId = :orgId AND x.id.roleId = :roleId")
				.setParameter("orgId", org.getId())
				.setParameter("roleId", role.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<UserOrganizationRole> get(User user) {
		try {
			Query q = em.createQuery("SELECT x FROM UserOrganizationRole x WHERE x.id.userId = :userId")
				.setParameter("userId", user.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<UserOrganizationRole> byRole(Role role) {
		try {
			Query q = em.createQuery("SELECT x FROM UserOrganizationRole x WHERE x.id.roleId = :roleId")
				.setParameter("roleId", role.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public Collection<HniServices> getHniServicesByRole(Long orgId, Long roleId) {
		try {
			//"SELECT x FROM HniServices x WHERE x.orgId = :orgId AND x.roleId = :roleId"
			Query q = em.createQuery("SELECT x FROM HniServices x WHERE x.roleId = :roleId")
				//.setParameter("orgId", orgId)
				.setParameter("roleId", roleId);
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean getProfileStatus(User user) {
		Long userId = user.getId();
		Query q = em.createQuery("SELECT u from UserPartialData u where u.userId = :userId ORDER BY lastUpdated desc")
				.setParameter("userId", userId);
		List<UserPartialData> userPartialDataList = q.getResultList();
		if (!userPartialDataList.isEmpty()) {
			UserPartialData userPartialData = userPartialDataList.get(0);
			return userPartialData.getStatus() != null
					? userPartialData.getStatus().equalsIgnoreCase("Y") ? true : false : true;
		}
		return true;
	}


}
