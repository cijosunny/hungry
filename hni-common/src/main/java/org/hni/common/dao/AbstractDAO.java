package org.hni.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hni.common.om.Persistable;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDAO<T extends Persistable> implements BaseDAO<T> {
	protected final static Logger logger = LoggerFactory.getLogger(AbstractDAO.class);
	
	protected Class<T> clazz;

	protected EntityManager entityManager;
	protected EntityManager em;
	
	protected AbstractDAO(Class<T> clazz)
	{
		setClazz(clazz);
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.em = entityManager;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}


	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}


	public T get(Object id) {
		if ( null == id ) {
			return null;
		}
		return (T)em.find(clazz, id);
	}

	@Override
	public T insert(T obj) {
		em.persist(obj);
		return obj;
	}

	/**
	 * To update an entity, we first have to get a copy of
	 * an attached entity.  Then copy the new attributes
	 * and save.
	 */
	@Override
	public T update(T obj) {
		T existingObj = em.merge(obj);
		return existingObj;			
	}

	@Override
	public T save(T obj) {
		if ( null == obj ) {
			return null;
		}
		if ( obj.getId() != null ) {
			return update(obj);
		} else {
			return insert(obj);
		}

	}
	
	@Override
	public T delete(T obj) {
		if ( null == obj ) {
			return obj;
		}
		T objx = get(obj.getId());
		if ( null != objx) {
			objx = em.merge(objx);
			em.remove(objx);
		}			
		return objx;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		try { 
			Query q = em.createQuery("SELECT x FROM " + clazz.getSimpleName() + " x");
			return q.getResultList();			
		} catch(NoResultException e) {
			return java.util.Collections.EMPTY_LIST;
		}
		
	}

	protected List<Long> convertToLongIds(List<Persistable> list) {
		List<Long> set = new ArrayList<Long>();
		for(Persistable obj : list) {
			set.add((Long)obj.getId());
		}
		return set;
	}
}
