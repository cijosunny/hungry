package org.hni.organization.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.organization.om.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrganizationDAO extends AbstractDAO<Organization> implements OrganizationDAO {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationDAO.class);

	public DefaultOrganizationDAO() {
		super(Organization.class);
	}

	@Override
	public boolean isAlreadyExists(Organization org) {
		try {
			Query q = em.createQuery("SELECT x FROM Organization x WHERE x.email= :email").setParameter("email",
					org.getEmail());
			return q.getResultList() != null && !q.getResultList().isEmpty();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return true;
	}

}
