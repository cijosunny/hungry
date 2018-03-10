package org.hni.provider.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.provider.om.Menu;
import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;

@Component
public class DefaultMenuDAO extends AbstractDAO<Menu> implements MenuDAO {

	protected DefaultMenuDAO() {
		super(Menu.class);
	}

	@Override
	public Collection<Menu> get(Provider provider) {
		try {
			Query q = em.createQuery("SELECT x FROM Menu x WHERE x.provider.id = :providerId")
				.setParameter("providerId", provider.getId());
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<Menu> getMenusByProviderId(Long providerId) {
		try {
			Query query = em.createQuery("SELECT p.menu FROM ProviderLocation p LEFT JOIN p.menu AS m WHERE p.provider.id =:providerId GROUP BY p.menu").setParameter("providerId", providerId);
			return query.getResultList();
			
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}
