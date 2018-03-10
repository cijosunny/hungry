package org.hni.user.service;


import java.util.List;

import javax.inject.Inject;

import org.hni.common.dao.BaseDAO;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.DependentDAO;
import org.hni.user.om.Dependent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("defaultDependentService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultDependentService extends AbstractService<Dependent> implements DependentService {

	@Inject
	private DependentDAO dependentDao;
	
	public DefaultDependentService(BaseDAO<Dependent> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dependent getDependentById(Long id) {
		return dependentDao.getDependentById(id).get(0);
	}

	@Override
	public List<Dependent> getAllDependentsOf(Integer id) {
		return dependentDao.getAllDependentsOf(id);
	}

}
