package org.hni.common.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.template.om.HniTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HniTemplateDAOImpl extends AbstractDAO<HniTemplate> implements HniTemplateDAO {
	
	protected HniTemplateDAOImpl() {
		super(HniTemplate.class);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(HniTemplateDAOImpl.class);

	@Override
	public HniTemplate getById(Long id) {
		try{
			Query q = em.createQuery("SELECT x FROM HniTemplate x WHERE x.id = :id")
						.setParameter("id", id);
			return (HniTemplate) q.getSingleResult();
		}catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HniTemplate getByType(String type) {
		try{
			Query q = em.createQuery("SELECT x FROM HniTemplate x WHERE x.type = :type")
						.setParameter("type", type);
			return (HniTemplate) q.getSingleResult();
		}catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<HniTemplate> getAll(){
		try{
			Query q = em.createQuery("SELECT x FROM HniTemplate x");
			return  q.getResultList();
		}catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

}
