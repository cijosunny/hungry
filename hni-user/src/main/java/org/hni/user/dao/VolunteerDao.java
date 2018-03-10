package org.hni.user.dao;

import java.util.List;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Volunteer;

public interface VolunteerDao  extends BaseDAO<Volunteer>  {
	
	Volunteer getByUserId(Long userId);
	List<VolunteerDto> getAllVolunteers();
	List<VolunteerDto> getVolunteerByState(String state, boolean flag);

}
