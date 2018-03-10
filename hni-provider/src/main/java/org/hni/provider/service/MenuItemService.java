package org.hni.provider.service;

import org.hni.common.service.BaseService;
import org.hni.provider.om.MenuItem;

public interface MenuItemService extends BaseService<MenuItem> {
	
	int deleteMenuItem(Long menuItemId);

}
