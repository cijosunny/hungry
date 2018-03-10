package org.hni.user.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDAO extends AbstractDAO<User> implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public DefaultUserDAO() {
		super(User.class);
	}

	@Override
	public List<User> byMobilePhone(String mobilePhone) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.mobilePhone = :mobilePhone  AND x.isActive = 1 AND deleted = 0").setParameter("mobilePhone", mobilePhone);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<User> byLastName(String lastName) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.lastName = :lastName  AND x.isActive = 1 AND deleted = 0 ").setParameter("lastName", lastName);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public User byEmailAddress(String email) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.email = :email AND x.isActive = 1 AND deleted = 0 ").setParameter("email", email);
			return (User) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long findTypeIdByUser(Long userId, String type) {
		Integer id = null ;
		String query = "select id from "+getTableByType(type) + " where user_id =:userId";
		List<Object> ids = em.createNativeQuery(query).setParameter("userId", userId).getResultList();
		if(ids!=null){
			id =  (Integer) ids.get(0);
		return id.longValue();
		}
		return 0L;
		
			
	}
	private String getTableByType(String type)
	 {
		Map<String,String> tableType = new HashMap<>();
		tableType.put("ngo", "ngo");
		tableType.put("volunteer", "volunteer");
		tableType.put("client", "client");
		return tableType.get(type);
		 
	 }

	@Override
	public String findUserState(Long userId) {
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT address.state from user_address userAddress INNER JOIN addresses address ON userAddress.address_id = address.id where userAddress.user_id = :userId").setParameter("userId", userId);
			return (String) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<String> getParticiapntsFromState(String stateCode) {
		try {
			Long roleId = HNIRoles.CLIENT.getRole();
			Query q = em.createNativeQuery("SELECT u.mobile_phone "
					+ "FROM users u "
					+ "LEFT JOIN user_address ua ON ua.user_id = u.id "
					+ "LEFT JOIN addresses a ON a.id = ua.address_id "
					+ "LEFT JOIN user_organization_role uor ON uor.user_id = u.id "
					+ "WHERE a.state = :stateCode AND u.active = 1 AND uor.role_id = :roleId")
					.setParameter("stateCode", stateCode)
					.setParameter("roleId", roleId);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}
