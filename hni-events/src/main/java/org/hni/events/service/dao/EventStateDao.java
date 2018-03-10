package org.hni.events.service.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.events.service.om.EventState;

/**
 * Created by walmart on 11/21/16.
 */
public interface EventStateDao extends BaseDAO<EventState>{

    EventState byPhoneNumber(String phoneNumber);
}
