package org.hni.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.admin.service.converter.HNIValidator;
import org.hni.admin.service.dto.VolunteerDto;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.AddressDAO;
import org.hni.user.dao.VolunteerDao;
import org.hni.user.om.Address;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("defaultVolunteerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultVolunteerService extends AbstractService<Volunteer> implements VolunteerService {
 
	private VolunteerDao volunteerDao;
	
	private AddressDAO addressDAO;
	
	@Inject
	public DefaultVolunteerService(VolunteerDao volunteerDao,AddressDAO addressDAO) {
		super(volunteerDao);
		this.volunteerDao = volunteerDao;
		this.addressDAO = addressDAO;
	}
	
	@Override
	public Volunteer save(Volunteer volunteer) {
		return volunteerDao.save(volunteer);
	}

	@Override
	public Volunteer getVolunteerDetails(Long volunteerId) {
		return volunteerDao.get(volunteerId);
	}

	@Override
	public List<VolunteerDto> getAllVolunteers() {
		return volunteerDao.getAllVolunteers();
	}

	@Override
	public List<VolunteerDto> getVolunteerByState(String state, boolean flag) {
		return volunteerDao.getVolunteerByState(state, flag);
	}

/*	
	@Override
	public Volunteer get(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volunteer insert(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volunteer update(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public Volunteer delete(Volunteer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Volunteer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public Volunteer registerVolunteer(Volunteer volunteer) {
		return volunteerDao.save(volunteer);
	}*/

}
