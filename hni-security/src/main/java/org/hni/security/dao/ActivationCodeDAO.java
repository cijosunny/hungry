package org.hni.security.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.security.om.ActivationCode;
import org.hni.user.om.User;

public interface ActivationCodeDAO extends BaseDAO<ActivationCode> {
    ActivationCode getByActivationCode(String actCode);
    List<ActivationCode> getByUser(User user);
    String getNextActivationCode();
}
