package org.hni.user.dao;

import java.util.List;

import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDAOImpl extends AbstractDAO<Client> implements ClientDAO  {

	protected ClientDAOImpl() {
		super(Client.class);
	}

	@Override
	public Client getByUserId(Long userId) {
			Query q = em.createQuery("SELECT x FROM Client x WHERE x.user.id = :userId").setParameter("userId", userId);
			List<Client> clients = q.getResultList();
			if (clients.isEmpty()) {
				return null;
			} else {
				return clients.get(0);
			}
	}

}
