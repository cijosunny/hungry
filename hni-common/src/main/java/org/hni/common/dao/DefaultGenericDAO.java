package org.hni.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hni.common.om.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultGenericDAO implements GenericDAO {
	protected final static Logger logger = LoggerFactory.getLogger(GenericDAO.class);
	
	
	protected EntityManager em;

	public DefaultGenericDAO() {}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}
	
	@Override
	public <T extends Persistable> T get(Class<T> clazz, Object id) {
		if ( null == id ) {
			return null;
		}
		return (T)em.find(clazz, id);
	}

	@Override
	public <T extends Persistable> T insert(Class<T> clazz, T obj) {
		em.persist(obj);
		return obj;
	}
	
	@Override
	public <T extends Persistable> T update(Class<T> clazz, T obj) {
		T existingObj = em.merge(obj);
		return existingObj;
	}

	@Override
	public <T extends Persistable> T delete(Class<T> clazz, T obj) {
		if ( null == obj ) {
			return obj;
		}
		T objx = get(clazz, obj.getId());
		if ( null != objx) {
			objx = em.merge(objx);
			em.remove(objx);
		}			
		return objx;
	}

	@Override
	public <T extends Persistable> T save(Class<T> clazz, T obj) {
		if ( null == obj ) {
			return null;
		}
		if ( obj.getId() != null ) {
			return update(clazz, obj);
		} else {
			return insert(clazz, obj);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Persistable> List<T> find(Class<T> clazz, String query, Object... params) {
		try { 
			Query q = em.createQuery(query);
			for(int i = 0; i < params.length; i++) { // bind any parameters
				q.setParameter(i+1, params[i]);
			}
			
			return q.getResultList();			
		} catch(NoResultException e) {
			return java.util.Collections.EMPTY_LIST;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Persistable> T findUnique(Class<T> clazz, String query, Object... params) {
		try { 
			Query q = em.createQuery(query);
			for(int i = 0; i < params.length; i++) { // bind any parameters
				q.setParameter(i+1, params[i]);
			}
			
			return (T)q.getSingleResult();			
		} catch(NoResultException e) {
			return null;
		}
	}
	
	protected String getValue(Object field) {
		if (field != null) {
			return String.valueOf(field);
		} else {
			return "-";
		}
	}
	
	protected String getValueForBoolean(Object field) {
		if (field != null) {
			return String.valueOf(field).equalsIgnoreCase("1") ? "Active" : "Not Active";
		} else {
			return "Not Active";
		}
	}
	
	protected String getValueForSheltered(Object field) {
		if (field != null) {
			return String.valueOf(field).equalsIgnoreCase("1") ? "Yes" : "No";
		} else {
			return "No";
		}
	}


}
