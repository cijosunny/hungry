/**
 * 
 */
package org.hni.sms.service.provider;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.hni.sms.service.provider.twilio.TwilioSmsProvider;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class SMSProvider {
	
	private Map<ServiceProvider, Provider> smsServiceProviders = new HashMap<>();
	
	@Inject
	private TwilioSmsProvider twilioSmsProvider;
	
	@PostConstruct
	public void loadSMSProviders() {
		smsServiceProviders.put(ServiceProvider.TWILIO, twilioSmsProvider);
	}
	
	protected Provider get(ServiceProvider provider) {
		return smsServiceProviders.get(provider);

	}
	
	
}
