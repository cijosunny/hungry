package org.hni.common.dao;

import java.util.List;

public interface BaseDAO<T> {
	T get(Object id);
	T insert(T obj);
	T update(T obj);
	T save(T obj); 	
	T delete(T obj);
	List<T> getAll();
}
