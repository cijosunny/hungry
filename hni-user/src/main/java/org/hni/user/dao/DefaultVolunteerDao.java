package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Volunteer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultVolunteerDao extends AbstractDAO<Volunteer> implements VolunteerDao  {
 
	private static final Logger _LOGGER = LoggerFactory.getLogger(DefaultVolunteerDao.class);

	public DefaultVolunteerDao() {
		super(Volunteer.class);
	}

	@Override
	public Volunteer getByUserId(Long userId) {
		try {
			Query q = em.createQuery("SELECT x FROM Volunteer x WHERE x.userId = :userId").setParameter("userId", userId);
			List<Volunteer> volunteers = q.getResultList();
			if (volunteers.isEmpty()) {
				return null;
			} else {
				return volunteers.get(0);
			}
		} catch (NoResultException e) {
			_LOGGER.error("Error in volunteer Query:" + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<VolunteerDto> getAllVolunteers() {
		List<VolunteerDto> listOfVolunteers = new ArrayList<>();
		Query query = em.createNativeQuery("SELECT vol.id, vol.user_id, user.mobile_phone, user.first_name, user.last_name FROM volunteer vol  LEFT JOIN users user ON user.id=vol.user_id LEFT JOIN user_address userAddress ON userAddress.user_id=user.id LEFT JOIN addresses address ON userAddress.address_id=address.id"); 
		List<Object[]> volunteerList = query.getResultList();
		if(volunteerList.isEmpty())
			return null;
		else{
			for(Object[] u : volunteerList){
				VolunteerDto volunteerDto = new VolunteerDto();
				volunteerDto.setVolunteerId(Long.valueOf(u[0].toString()));
				volunteerDto.setUserId(Long.valueOf(u[1].toString()));
				volunteerDto.setPhoneNumber(String.valueOf(u[2]));
				volunteerDto.setName(String.valueOf(u[3])+" "+String.valueOf(u[4]));
				listOfVolunteers.add(volunteerDto);
			}
			return listOfVolunteers;
		}
	}

	@Override
	public List<VolunteerDto> getVolunteerByState(String state, boolean flag) {
		List<VolunteerDto> listOfVolunteers = new ArrayList<>();
		Query query = em.createNativeQuery("SELECT vol.id, vol.user_id, user.mobile_phone, user.first_name, user.last_name FROM volunteer vol  LEFT JOIN users user ON user.id=vol.user_id LEFT JOIN user_address userAddress ON userAddress.user_id=user.id LEFT JOIN addresses address ON userAddress.address_id=address.id WHERE address.state= :state AND vol.available_for_place_order = :flag")
				.setParameter("state", state)
				.setParameter("flag", flag);
		List<Object[]> volunteerList = query.getResultList();
		if(volunteerList.isEmpty())
			return null;
		else{
			for(Object[] u : volunteerList){
				VolunteerDto volunteerDto = new VolunteerDto();
				volunteerDto.setVolunteerId(Long.valueOf(u[0].toString()));
				volunteerDto.setUserId(Long.valueOf(u[1].toString()));
				volunteerDto.setPhoneNumber(String.valueOf(u[2]));
				volunteerDto.setName(String.valueOf(u[3])+" "+String.valueOf(u[4]));
				listOfVolunteers.add(volunteerDto);
			}
			return listOfVolunteers;
		}
	}
}
