package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.VolunteerAvailability;

public interface VolunteerAvailabilityDAO extends BaseDAO<VolunteerAvailability> {
	public List<VolunteerAvailability> getVolunteerAvailabilityByVolunteerId(int volunteerId);
}
