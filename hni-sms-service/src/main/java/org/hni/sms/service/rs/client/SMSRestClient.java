package org.hni.sms.service.rs.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.sms.service.model.SmsMessage;
import org.hni.util.web.HniWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SMSRestClient extends HniWebClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SMSRestClient.class);

    @Value("#{hniProperties['twilio.webhook.url']}")
    private String TWILIO_WEBHOOK_URL;
    
    @Value("#{hniProperties['twilio.auth.user']}")
    private String TWILIO_AUTH_USER;

    @Value("#{hniProperties['twilio.api.token']}")
    private String TWILIO_API_KEY;

    
    @Override
    protected String getBaseUrl() {
        return TWILIO_WEBHOOK_URL;
    }
	
    
    public Response pushMessage(Object entity) {
    	LOGGER.info("Sending SMS to user");
    	Response response = getBase64HttpClient(TWILIO_AUTH_USER, TWILIO_API_KEY)
    							.post(entity);
    	
    	return response;
    }
    
   
    public String sendMessageWithTwilio(SmsMessage entity) {
    	Twilio.init(TWILIO_AUTH_USER, TWILIO_API_KEY);
    	LOGGER.debug("Message Information : ", entity.toString());
    	Message message = Message.creator(new PhoneNumber(entity.getToNumber()), 
    						new PhoneNumber(entity.getFromNumber()), 
    							entity.getText())
    								.create();
    	String sid = message.getSid();
    	LOGGER.info(" Message SID : "  + sid);
    	return sid;
    }
    
	@Override
	protected String getContentType() {
		return MediaType.APPLICATION_FORM_URLENCODED;
	}
    

}
