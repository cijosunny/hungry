package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.UserPartialData;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserPartialCreateDAO extends AbstractDAO<UserPartialData> implements UserPartialCreateDAO {

	protected DefaultUserPartialCreateDAO() {
		super(UserPartialData.class);
	}

	@Override
	public UserPartialData getUserPartialDataByUserId(Long userId) {
		List<UserPartialData> userPartialDatas = em.createQuery("select x from UserPartialData x where x.userId=:userId").setParameter("userId", userId).getResultList();
		if(!userPartialDatas.isEmpty()){
			return userPartialDatas.get(0);
		}
		return null;
	}


}
