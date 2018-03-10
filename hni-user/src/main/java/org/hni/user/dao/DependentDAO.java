package org.hni.user.dao;

import java.util.List;
import java.util.Set;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Dependent;

public interface DependentDAO extends BaseDAO<Dependent>{

	List<Dependent> getDependentById(Long id);
	List<Dependent> getAllDependentsOf(Integer id);
}
