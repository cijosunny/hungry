package org.hni.user.service;

import java.util.List;

import org.hni.common.service.BaseService;
import org.hni.user.om.Dependent;

public interface DependentService extends BaseService<Dependent> {

	Dependent getDependentById(Long id);
	List<Dependent> getAllDependentsOf(Integer id);
}
