package org.hni.provider.dao;


import java.util.Collections;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.provider.om.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class DefaultMenuItemDAO extends AbstractDAO<MenuItem> implements
		MenuItemDAO {

	protected DefaultMenuItemDAO() {
		super(MenuItem.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int deleteMenuItem(Long menuItemId) {
		try {
			Query q = em.createQuery("DELETE FROM MenuItem x WHERE x.id = :menuItemId")
				.setParameter("menuItemId", menuItemId);
			return q.executeUpdate();
		} catch(NoResultException e) {
			return 0;
		}
	}

}
