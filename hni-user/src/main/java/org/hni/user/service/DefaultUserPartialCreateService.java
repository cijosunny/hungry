package org.hni.user.service;

import javax.inject.Inject;

import org.hni.common.dao.BaseDAO;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.UserPartialCreateDAO;
import org.hni.user.om.UserPartialData;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserPartialCreateService extends AbstractService<UserPartialData> implements UserPartialCreateService{
	@Inject
	private UserPartialCreateDAO userPartialCreateDAO;
	
	public DefaultUserPartialCreateService(BaseDAO<UserPartialData> dao) {
		super(dao);
	}

	@Override
	public UserPartialData getUserPartialDataByUserId(Long userId) {
		return userPartialCreateDAO.getUserPartialDataByUserId(userId);
	}

}
