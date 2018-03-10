package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.user.dao.NGOGenericDAO;
import org.hni.user.om.Ngo;
import org.springframework.stereotype.Component;

@Component
public class NGOGenericServiceImpl implements NGOGenericService {

	@Inject
	private NGOGenericDAO ngoGenericDao;
	@Override
	public Ngo get(Object id) {
		return ngoGenericDao.get(Ngo.class, id);
	}

	@Override
	public Ngo insert(Ngo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ngo update(Ngo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ngo save(Ngo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ngo delete(Ngo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ngo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
