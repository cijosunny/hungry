package org.hni.user.service;

import java.util.List;

import org.hni.admin.service.dto.VolunteerDto;
import org.hni.common.service.BaseService;
import org.hni.user.om.Volunteer;

public interface VolunteerService extends BaseService<Volunteer> {
	 Volunteer getVolunteerDetails(Long volunteerId);
	 List<VolunteerDto> getAllVolunteers();
	 List<VolunteerDto> getVolunteerByState(String state, boolean flag);
}
