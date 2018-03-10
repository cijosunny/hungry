package org.hni.provider.service;

import org.hni.common.service.BaseService;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.user.om.Address;
import org.hni.user.om.User;

import java.util.Collection;
import java.util.List;

public interface ProviderLocationService extends BaseService<ProviderLocation> {

	Collection<ProviderLocation> with(Provider provider);
	Address searchCustomerAddress(String customerAddress);
	Collection<ProviderLocation> providersNearCustomer(Address customerAddress, int itemsPerPage, double distance, double radius);
	List<ProviderLocation> locationsOf(Long providerId);
	void updateProviderLocations(List<ProviderLocation> providerLocations, User loggedInUser);
	void updateProviderLocationStatus(List<ProviderLocation> providerLocations, Boolean status);
	void deleteProviderLocation(Long providerLocationId);
}
