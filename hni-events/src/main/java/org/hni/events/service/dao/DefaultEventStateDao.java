package org.hni.events.service.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.events.service.om.EventState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

/**
 * Created by walmart on 11/21/16.
 */
@Component
public class DefaultEventStateDao extends AbstractDAO<EventState> implements EventStateDao {
    private static final Logger logger = LoggerFactory.getLogger(EventStateDao.class);

    public DefaultEventStateDao() {
        super(EventState.class);
    }

    @Override
    public EventState byPhoneNumber(String phoneNumber) {
        try {
            Query q = em.createQuery("SELECT x FROM EventState x WHERE x.phoneNumber  = :phoneNumber ")
                    .setParameter("phoneNumber", phoneNumber);
            List resultList = q.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                return (EventState) resultList.get(0);
            }
        } catch (NoResultException e) {
        }
        return null;
    }
}
