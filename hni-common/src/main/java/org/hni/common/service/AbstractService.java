package org.hni.common.service;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T> implements BaseService<T> {
	private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
	private BaseDAO<T> dao;

	public AbstractService(BaseDAO<T> dao) {
		setDao(dao);
	}
	
	public BaseDAO<T> getDao() {
		return dao;
	}

	public void setDao(BaseDAO<T> dao) {
		this.dao = dao;
	}

	@Override
	public T get(Object id) {
		return dao.get(id);
	}

	@Override
	@Transactional
	public T insert(T obj) {
		return dao.insert(obj);
	}

	@Override
	@Transactional
	public T update(T obj) {
		return dao.update(obj);
	}

	@Override
	@Transactional
	public T save(T obj) {
		return dao.save(obj);
	}

	@Override
	@Transactional
	public T delete(T obj) {
		return dao.delete(obj);
	}

	@Override
	public List<T> getAll() {
		return dao.getAll();
	}


}
