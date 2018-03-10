package org.hni.provider.dao;

import org.hni.common.dao.BaseDAO;
import org.hni.provider.om.MenuItem;

public interface MenuItemDAO extends BaseDAO<MenuItem> {

	int deleteMenuItem(Long menuItemId);
}
