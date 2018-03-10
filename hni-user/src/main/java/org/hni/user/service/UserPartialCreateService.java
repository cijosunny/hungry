package org.hni.user.service;

import org.hni.common.service.BaseService;
import org.hni.user.om.UserPartialData;

public interface UserPartialCreateService extends BaseService<UserPartialData>{
	public UserPartialData getUserPartialDataByUserId(Long userId);
}
