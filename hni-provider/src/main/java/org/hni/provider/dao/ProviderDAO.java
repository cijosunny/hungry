package org.hni.provider.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.order.om.Order;
import org.hni.provider.om.Provider;

public interface ProviderDAO extends BaseDAO<Provider> {

	List<Order> getProviderOrders(Long providerId);
	List<Order> getOpenOrders(Long providerId);
}
