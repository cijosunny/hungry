package org.hni.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao extends DefaultGenericDAO {
	
	
	public List<Map> getAllCustomersByRole() {
		List<Map> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();

		List<Object[]> user = em
				.createNativeQuery("SELECT u.id, u.active, u.first_name,u.last_name,u.gender_code,u.mobile_phone,"
						+ "u.email, a.address_line1, c.max_meals_allowed_per_day, COUNT(o.id) AS ordCount, c.sheltered, ng.contact_name, COUNT(d.id) AS noOfDependents "
						+ "FROM users u "
						+ "LEFT JOIN user_organization_role uor ON uor.user_id = u.id "
						+ "LEFT JOIN client c ON c.user_id = u.id "
						+ "LEFT JOIN dependents d ON d.client_id = c.id "
						+ "LEFT JOIN ngo ng ON c.ngo_id = ng.id "
						+ "LEFT JOIN user_address ua ON ua.user_id = u.id "
						+ "LEFT JOIN addresses a ON a.id = ua.address_id "
						+ "LEFT JOIN orders o ON o.user_id=u.id "
						+ "WHERE uor.role_id = :roleId AND u.deleted = 0 "
						+ "group by u.id;")
				.setParameter("roleId", role).getResultList();
		for (Object[] u : user) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("userId",getValue(u[0]));
			map.put("active", getValueForBoolean(u[1]));
			map.put("firstName",getValue(u[2]));
			map.put("lastName",getValue(u[3]));
			map.put("mobilePhone",HNIConverter.convertPhoneNumberToUiFormat(getValue(u[5])));
			map.put("address",getValue(u[7]));
			map.put("mealsPerDay", getValue(u[8]));
			map.put("noDependents", "0");
			map.put("orders", getValue(u[9]));
			map.put("sheltered", getValueForSheltered(u[10]));
			map.put("contactName", getValue(u[11]));
			map.put("noOfDependents", getValue(u[12]));
			customers.add(map);
		}
		return customers;

	}
	

	public List<Map> getAllCustomersUnderOrganisation(User u) {
		List<Map> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();
		Long userId = null;
		if (u != null) {
			userId = u.getId();
			List<Object[]> user = em
					.createNativeQuery(
							"select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,c.race,ad.address_line1,"
							+ " COUNT(o.id)  as ordCount,u.id from users u INNER JOIN user_organization_role x ON u.id=x.user_id "
							+ "INNER JOIN `client` c ON c.user_id=u.id INNER JOIN user_address uad ON uad.user_id=u.id "
							+ "INNER JOIN addresses ad ON ad.id=uad.address_id INNER JOIN orders o ON o.user_id=u.id"
							+ " where x.role_id=:role")
					.setParameter("role", role).getResultList();
			for (Object[] usr : user) {
				Map<String,String> map=new HashMap<String,String>();
				map.put("firstName",getValue(usr[0]));
				map.put("lastName",getValue(usr[1]));
				map.put("mobilePhone",HNIConverter.convertPhoneNumberToUiFormat(getValue(usr[3])));
				map.put("race",getValue(usr[5]));
				map.put("address",getValue(usr[6]));
				map.put("orders", getValue(usr[7]));
				map.put("userId",getValue(usr[8]));
				customers.add(map);
				
			}
		}
		return customers;
	}

	@SuppressWarnings("unchecked")
	public List<Map> getAllCustomersEnrolledByNgo(User user) {
		List<Map> customers = new ArrayList<>();
		
		List<Object[]> users = em
				.createNativeQuery(
						"SELECT u.id, u.active, u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,"
						+ " a.address_line1, c.max_meals_allowed_per_day, COUNT(o.id) AS ordCount, c.sheltered"
						+ " FROM users u LEFT JOIN user_organization_role uor ON uor.user_id = u.id "
						+ "LEFT JOIN client c ON c.user_id = u.id "
						+ "LEFT JOIN user_address ua ON ua.user_id = u.id "
						+ "LEFT JOIN addresses a ON a.id = ua.address_id "
						+ "LEFT JOIN orders o ON o.user_id=u.id "
						+ "LEFT JOIN ngo ng ON ng.id=c.ngo_id "
						+ "WHERE uor.role_id = :roleId AND u.deleted = 0 AND ng.user_id=:userId GROUP BY u.id;")
				.setParameter("userId", user.getId())
				.setParameter("roleId", HNIRoles.CLIENT.getRole())
				.getResultList();
		for (Object[] u : users) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("userId",getValue(u[0]));
			map.put("active", getValueForBoolean(u[1]));
			map.put("firstName",getValue(u[2]));
			map.put("lastName",getValue(u[3]));
			map.put("mobilePhone",HNIConverter.convertPhoneNumberToUiFormat(getValue(u[5])));
			map.put("address",getValue(u[7]));
			map.put("mealsPerDay", getValue(u[8]));
			map.put("noDependents", "0");
			map.put("orders", getValue(u[9]));
			map.put("sheltered", getValueForSheltered(u[10]));
			customers.add(map);
		}
		return customers;
	}
	
	public Map<String, Object> getCustomerOrderCount(){
		Long role = HNIRoles.CLIENT.getRole();
		Query query1 = em.createNativeQuery("SELECT COUNT(oi.id) as TotalOrders FROM order_items oi");
		
		Query query2 = em.createNativeQuery("SELECT COUNT(*) FROM user_organization_role uor WHERE uor.role_id = :roleId")
						 .setParameter("roleId", role);
		
		Map<String, Object> attributes = new HashMap<>();
		
		attributes.put("TotalOrders", query1.getSingleResult());
		attributes.put("TotalParticipants", query2.getSingleResult());

		
		return attributes;
	}
}