package org.hni.admin.integration;

import junit.framework.TestCase;

import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IntegrationTests extends TestCase {
	public static boolean allowIntegrationTests = false;

	public static final ObjectMapper objectMapper = new ObjectMapper();

	public static final String TEST_URL = "http://localhost:8080/hni-admin-service/api/v1";
	public static final String AUTHENTICATION_URL = getUrl() + "/security/authentication";
	public static final String USER_URL = getUrl() + "/users";

	static {
		ServiceClient serviceClient = new ServiceClient();
		ServiceResponse serviceResponse = serviceClient.callService(getUrl(), null, null, new HttpGet());
		allowIntegrationTests = (200 == serviceResponse.getStatusCode() || 404 == serviceResponse.getStatusCode());
	}

	public static String getUrl() {
		return TEST_URL;
	}

	@Test
	public void testBasic() {
		assertTrue(true);
	}
}
