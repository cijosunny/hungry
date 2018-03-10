package org.hni.sms.service.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.hni.sms.service.dao.SmsServiceLoaderDao;
import org.hni.sms.service.provider.om.SmsProvider;
import org.springframework.stereotype.Component;
@Component
public class SmsServiceLoader {
	
	@Inject
	private SmsServiceLoaderDao smsServiceLoaderDao;
	private static Map<String, SmsProvider> providers = new HashMap<>();
	
	@PostConstruct
	public void loadRegisteredProviders() {
		List<SmsProvider> providerList = new ArrayList();
		providerList = smsServiceLoaderDao.getAllProvider();	
		for (SmsProvider provider : providerList) {
			providers.put(provider.getStateCode().toUpperCase(),provider);
		}
	}
	
	public static Map<String, SmsProvider> getProviders() {
		return providers;
	}
	

	public List<SmsProvider> getSmsProvidersByState(String stateCode) {
		
		return smsServiceLoaderDao.getAllProviderBySate(stateCode);
	}

}
