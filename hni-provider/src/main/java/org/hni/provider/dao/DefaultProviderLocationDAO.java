package org.hni.provider.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.Address;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderLocationDAO extends AbstractDAO<ProviderLocation> implements ProviderLocationDAO {

	protected DefaultProviderLocationDAO() {
		super(ProviderLocation.class);
	}

	@Override
	public Collection<ProviderLocation> with(Provider provider) {
		try {
			Query q = em.createQuery("SELECT x FROM ProviderLocation x WHERE x.provider.id = :providerId")
				.setParameter("providerId", provider.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<ProviderLocation> providersNearCustomer(Address addr, int itemsPerPage) {
		try {

			String queryString = new StringBuilder()
					.append("SELECT pl.* FROM provider_locations pl ")
					.append(" WHERE pl.address_id in ")
					.append(" ( select new_addr.id from ")
					.append(" ( SELECT adr.id, ")
					.append(" ( 6371 * acos( ")
					.append(" cos(radians(:latLkp)) * cos(radians(adr.latitude)) * cos(radians(adr.longitude) - radians(:longLkp)) + ")
					.append(" sin(radians(:latLkp)) * sin(radians(adr.latitude)) ")
					.append(" ) ) AS distance ")
					.append(" FROM addresses adr ")
					.append(" group by adr.id ")
					.append(" HAVING distance < 10 ")
					.append(" ORDER BY distance LIMIT :items ) as new_addr ) ")
					.toString();

			Query q = em.createNativeQuery(queryString, ProviderLocation.class)
					.setParameter("longLkp", addr.getLongitude())
					.setParameter("latLkp", addr.getLatitude())
					.setParameter("items", itemsPerPage);

			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ProviderLocation> locationsOf(Long providerId) {
		try {
			Query q = em.createQuery("SELECT x FROM ProviderLocation x WHERE x.provider.id = :providerId")
				.setParameter("providerId", providerId);
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}
	protected String getValue(Object field) {
		if (field != null) {
			return String.valueOf(field);
		} else {
			return "-";
		}
	}
	
	private Boolean getBooleanValue(Object value){
		if (value != null) {
			return Boolean.valueOf(value.toString());
		} else {
			return false;
		}
	}

}
