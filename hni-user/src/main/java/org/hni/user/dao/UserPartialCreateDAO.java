package org.hni.user.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.UserPartialData;

public interface UserPartialCreateDAO extends BaseDAO<UserPartialData> {
	public UserPartialData getUserPartialDataByUserId(Long userId);
}
