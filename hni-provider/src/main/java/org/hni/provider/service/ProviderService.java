package org.hni.provider.service;

import java.util.List;
import java.util.Map;

import org.hni.common.service.BaseService;
import org.hni.order.om.Order;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.User;

public interface ProviderService extends BaseService<Provider> {

	Provider getProviderDetails(Long providerId, User loggedInUser);
	List<ProviderLocation> getProviderLocations(Long providerId, User loggedInUser);
	Map<String, String> deleteProviders(List<Long> providerIds, User loggedInUser);
	Map<String, String> activateProviders(List<Long> providerIds, Boolean value, User loggedInUser);
	List<Order> getProviderOrders(Long providerId);
	
}
