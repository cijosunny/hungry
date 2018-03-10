package org.hni.user.dao;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Dependent;
import org.hni.user.service.DependentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class DefaultDependentDAO extends AbstractDAO<Dependent> implements DependentDAO{
	private static final Logger logger = LoggerFactory.getLogger(DependentService.class);

	protected DefaultDependentDAO() {
		super(Dependent.class);
	}

	@Override
	public List<Dependent> getDependentById(Long id) {
		try {
			Query q = em.createQuery("SELECT x FROM Dependent x WHERE x.id = :id").setParameter("id", id);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Dependent> getAllDependentsOf(Integer id) {
		try {
			Query q = em.createQuery("SELECT x FROM Dependent x WHERE x.client.id = :id").setParameter("id", id);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}
