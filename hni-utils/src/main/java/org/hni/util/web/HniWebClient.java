package org.hni.util.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public abstract class HniWebClient {

	public static final String BASIC = "Basic";
	
	public static final String AUTHORIZATION = "Authorization";
	
    private final static Logger LOGGER = LoggerFactory.getLogger(HniWebClient.class);

	private static final String COLON = ":";

    protected final WebClient getWebClient() {
        final List<Object> providers = new ArrayList<>();
        final JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        provider.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        providers.add(provider);
        return WebClient.create(getBaseUrl(), providers, true)
                .type(getContentType());
    }

    protected abstract String getBaseUrl();
    
    protected abstract String getContentType();
    
    protected final WebClient getBase64HttpClient(String user, String pass) {
    	WebClient webClient = getWebClient();
    	
    	webClient.getHeaders().add(AUTHORIZATION, getBase64EncodedCredentials(user, pass));
    	
    	return webClient;
    }
    
    private String getBase64EncodedCredentials(String user, String password){
		LOGGER.info("Preparing client for the connection");
		String credential = user + COLON + password;
		
		Base64.encode(credential.getBytes());
		
		return new String();
	}
}