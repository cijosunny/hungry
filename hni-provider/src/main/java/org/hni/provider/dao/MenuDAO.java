package org.hni.provider.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.provider.om.Menu;
import org.hni.provider.om.Provider;

public interface MenuDAO extends BaseDAO<Menu> {

	Collection<Menu> get(Provider provider);

	Collection<Menu> getMenusByProviderId(Long providerId);
}
