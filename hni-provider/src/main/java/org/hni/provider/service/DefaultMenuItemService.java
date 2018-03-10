package org.hni.provider.service;


import org.hni.common.service.AbstractService;
import org.hni.provider.dao.MenuItemDAO;
import org.hni.provider.om.MenuItem;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultMenuItemService extends AbstractService<MenuItem> implements
		MenuItemService {
	
	private MenuItemDAO menuItemDao;

	public DefaultMenuItemService(MenuItemDAO menuItemDao) {
		super(menuItemDao);
		this.menuItemDao = menuItemDao;
	}

	@Override
	public int deleteMenuItem(Long menuItemId) {
		return menuItemDao.deleteMenuItem(menuItemId);
	}

}
