package org.hni.user.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Client;

public interface ClientDAO extends BaseDAO<Client>{
	
	Client getByUserId(Long userId);

}
