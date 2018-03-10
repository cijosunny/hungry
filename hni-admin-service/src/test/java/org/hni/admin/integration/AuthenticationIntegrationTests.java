package org.hni.admin.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.hni.user.om.User;
import org.junit.Test;

public class AuthenticationIntegrationTests extends IntegrationTests {
	public static final String TOKEN_HEADER = "X-hni-token";
	public static final String USER_EMAIL = "superuser@hni.com";
	public static final String PASSWORD = "test123";

	/*
	 * Note: these tests will run if the local tomcat is enabled, and not if it
	 * isn't.
	 */
	@Test
	public void testAuthentication() {
		try {
			org.junit.Assume.assumeTrue(allowIntegrationTests);
			authenticate();
		} catch (org.junit.AssumptionViolatedException ave) {
			System.out.println("Assumption false, ignoring test.");
		}
	}

	@Test
	public void testServiceTokenUse() {
		try {
			org.junit.Assume.assumeTrue(allowIntegrationTests);

			String token = authenticate();
			String bodyContent = "";
			ServiceClient serviceClient = new ServiceClient();
			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new BasicNameValuePair(TOKEN_HEADER, token));
			headers.add(new BasicNameValuePair("Content-Type", MediaType.APPLICATION_JSON));
			headers.add(new BasicNameValuePair("Accept", MediaType.APPLICATION_JSON));
			ServiceResponse response = serviceClient.callService(USER_URL + "/1", headers, bodyContent, new HttpGet());
			assertTrue(200 == response.getStatusCode());
			assertTrue(null != response && null != response.getResponseBody() && !response.getResponseBody().isEmpty());
			String responseString = response.getResponseBody();
			User user = null;
			try {
				user = objectMapper.readValue(responseString, User.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			assertTrue(null != user);
			assertTrue(USER_EMAIL.equals(user.getEmail()));
		} catch (org.junit.AssumptionViolatedException ave) {
			System.out.println("Assumption false, ignoring test.");
		}
	}

	private String authenticate() {
		String bodyContent = "{\"username\": \"" + USER_EMAIL + "\", \"password\": \"" + PASSWORD + "\"}";
		ServiceClient serviceClient = new ServiceClient();
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new BasicNameValuePair("organizationId", "2"));
		headers.add(new BasicNameValuePair("Content-Type", MediaType.APPLICATION_JSON));
		headers.add(new BasicNameValuePair("Accept", MediaType.APPLICATION_JSON));
		ServiceResponse response = serviceClient.callService(AUTHENTICATION_URL, headers, bodyContent, new HttpPost());
		assertTrue(200 == response.getStatusCode());
		assertTrue(null != response && null != response.getResponseBody() && !response.getResponseBody().isEmpty());
		String token = response.getResponseBody();
		System.out.println("Token: " + token);
		return token;

	}
}