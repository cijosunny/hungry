package org.hni.user.service;

import org.hni.common.service.BaseService;
import org.hni.user.om.Client;

public interface ClientService extends BaseService<Client>{
	Client getByUserId(Long id);

}
