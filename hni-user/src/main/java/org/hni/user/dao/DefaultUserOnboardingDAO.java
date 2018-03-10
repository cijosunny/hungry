package org.hni.user.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Invitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DefaultUserOnboardingDAO extends AbstractDAO<Invitation> implements UserOnboardingDAO{
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserOnboardingDAO.class);

	public DefaultUserOnboardingDAO() {
		super(Invitation.class);
	}

	public Collection<Invitation> validateInvitationCode(String invitationCode){
		try {
			Query q = em.createQuery("SELECT x FROM Invitation x WHERE x.invitationCode = :invitationCode and x.activated = 0 and x.expirationDate>=:today")
						.setParameter("invitationCode", invitationCode)
						.setParameter("today", new Date(),TemporalType.DATE);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public Invitation getInvitedBy(String email) {
		try {
			Query q = em.createQuery("SELECT x FROM Invitation x WHERE x.email = :email and activated=1 order by createdDate desc").setParameter("email", email);
			return  (q.getResultList()!=null&&!q.getResultList().isEmpty())?(Invitation)q.getResultList().get(0):null;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Invitation updateInvitationStatus(String activationCode) {
		try {
			Query q = em.createQuery("SELECT x FROM Invitation x WHERE x.invitationCode = :invitationCode");
			q.setParameter("invitationCode", activationCode);
			Invitation invitation = (Invitation) q.getSingleResult();
			// 1 = completed
			// 0 = Not Complete
			invitation.setActivated(1); 
			super.update(invitation);
			return invitation;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Invitation> isValidInvitation(String phoneNumber) {
		try {
			Query q = em.createQuery("SELECT x FROM Invitation x WHERE x.phone = :phoneNumber and x.expirationDate>=:today ORDER BY x.id DESC")
					.setParameter("phoneNumber", phoneNumber)
					.setParameter("today", new Date(),TemporalType.DATE);
			return q.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}

}
