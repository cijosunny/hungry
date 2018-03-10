package org.hni.common.dao;

import java.util.List;

import org.hni.common.om.Persistable;

public interface GenericDAO {
	static String WILD_CARD = "%";
	
	<T extends Persistable> T get(Class<T> clazz, Object id);
	<T extends Persistable> T insert(Class<T> clazz, T obj);
	<T extends Persistable> T update(Class<T> clazz, T obj);
	<T extends Persistable> T delete(Class<T> clazz, T obj);
	<T extends Persistable> T save(Class<T> clazz, T obj);
	<T extends Persistable> List<T> find(Class<T> clazz, String query, Object... params);
	<T extends Persistable> T findUnique(Class<T> clazz, String query, Object... params);
	
    static String createStartsWith(String s) {
        return s+WILD_CARD;
    }

    static String createEndsWith(String s) {
        return WILD_CARD+s;
    }

    static String like(String s) {
        return WILD_CARD+s+WILD_CARD;
    }	
}
