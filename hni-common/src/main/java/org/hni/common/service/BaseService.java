package org.hni.common.service;

import java.util.List;

public interface BaseService<T> {
	T get(Object id);
	T insert(T obj);
	T update(T obj);
	T save(T obj); 	
	T delete(T obj);
	List<T> getAll();
}
