package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.user.dao.ClientDAO;
import org.hni.user.om.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImpl implements ClientService {
	
	@Inject
	private ClientDAO clientDAO;

	@Override
	public Client get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client insert(Client obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client update(Client obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client save(Client obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client delete(Client obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getByUserId(Long id) {
		Client client = clientDAO.getByUserId(id);
		//client.getUser().setMobilePhone(HNIConverter.convertPhoneNumberToUiFormat(client.getUser().getMobilePhone()));
		return client;
	}

}
