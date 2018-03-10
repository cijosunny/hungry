package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.VolunteerAvailability;
import org.springframework.stereotype.Component;

@Component
public class DefaultVolunteerAvailabilityDAO extends AbstractDAO<VolunteerAvailability> implements VolunteerAvailabilityDAO {

	protected DefaultVolunteerAvailabilityDAO() {
		super(VolunteerAvailability.class);
	}

	@Override
	public List<VolunteerAvailability> getVolunteerAvailabilityByVolunteerId(int volunteerId) {
			List<VolunteerAvailability> volunteerAvailabilities = em.createQuery("select va from VolunteerAvailability va where volunteerId=:volunteerId")
																	.setParameter("volunteerId", volunteerId)
																	.getResultList();
			return volunteerAvailabilities;
	}

}
