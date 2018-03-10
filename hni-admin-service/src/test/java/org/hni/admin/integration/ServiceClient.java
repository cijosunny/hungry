package org.hni.admin.integration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class ServiceClient {
	private static final Log LOG = LogFactory.getLog(ServiceClient.class);
	private HttpClient httpClient = null;

	/**
	 * non-parameterized constructor
	 */
	public ServiceClient() {
		httpClient = HttpClientBuilder.create().build();
	}

	/**
	 * parameterized constructor
	 * 
	 * @param httpConnectionTimeout
	 * @param httpSocketTimeout
	 * @param proxyHost
	 * @param proxyPort
	 * @param followRedirects
	 */
	public ServiceClient(Integer httpConnectionTimeout, Integer httpSocketTimeout, String proxyHost, Integer proxyPort) {
		this(httpConnectionTimeout, httpSocketTimeout, proxyHost, proxyPort, true);
	}

	/**
	 * parameterized constructor
	 * 
	 * @param httpConnectionTimeout
	 * @param httpSocketTimeout
	 * @param proxyHost
	 * @param proxyPort
	 * @param followRedirects
	 */
	public ServiceClient(Integer httpConnectionTimeout, Integer httpSocketTimeout, String proxyHost, Integer proxyPort,
			boolean followRedirects) {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
		if (null != httpConnectionTimeout) {
			requestConfigBuilder.setConnectTimeout(httpConnectionTimeout);
		}
		if (null != httpSocketTimeout) {
			requestConfigBuilder.setSocketTimeout(httpSocketTimeout);
		}
		if ((null != proxyHost && !proxyHost.isEmpty()) && (null != proxyPort)) {
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			requestConfigBuilder.setProxy(proxy);
		}
		if (!followRedirects) {
			requestConfigBuilder.setRedirectsEnabled(false);
		}
		RequestConfig config = requestConfigBuilder.build();

		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	/**
	 * @param url
	 * @param requestHeaderPairs
	 * @param content
	 * @param base
	 * @return ServiceResponse
	 */
	public ServiceResponse callService(String url, List<NameValuePair> requestHeaderPairs, String content, HttpRequestBase base) {
		Long startTime = System.currentTimeMillis();
		ServiceResponse response = new ServiceResponse();
		try {

			HttpEntity stringEntity = null;
			if (null != content && !content.isEmpty()) {
				try {
					stringEntity = new StringEntity(content);
				} catch (UnsupportedEncodingException e) {
					LOG.warn(e.getMessage(), e);
				}
			}
			response = callServiceWithHttpEntity(url, requestHeaderPairs, stringEntity, base);
			return response;
		} finally {
			Long serviceCallEndTime = System.currentTimeMillis();
			Long serviceCallDuration = serviceCallEndTime - startTime;
			LOG.info("Service call to: " + url + " status code: " + response.getStatusCode() + " duration: " + (serviceCallDuration));
		}
	}

	/**
	 * handle get, post, put, delete in one
	 * 
	 * @param url
	 * @param requestHeaderPairs
	 * @param entity
	 * @param base
	 * @return String
	 */
	public ServiceResponse callServiceWithHttpEntity(String url, List<NameValuePair> requestHeaderPairs, HttpEntity entity,
			HttpRequestBase base) {

		ServiceResponse response = new ServiceResponse();
		try {
			base.setURI(new URI(url));
			/* if there's content, set the content */
			if (null != entity && base instanceof HttpEntityEnclosingRequestBase) {
				HttpEntityEnclosingRequestBase enclosingBase = (HttpEntityEnclosingRequestBase) base;
				enclosingBase.setEntity(entity);
			}
			setHeaders(base, requestHeaderPairs);
			HttpResponse httpReponse = httpClient.execute(base);
			int responseCode = httpReponse.getStatusLine().getStatusCode();
			response.setStatusCode(responseCode);
			response.setHeaders(httpReponse.getAllHeaders());
			/* if there's response content, include it */
			if (null != httpReponse.getEntity()) {
				response.setResponseBody(IOUtils.toString(httpReponse.getEntity().getContent()));
			}
		} catch (IOException e) {
			LOG.warn(e.getMessage(), e);
		} catch (URISyntaxException e) {
			LOG.warn(e.getMessage(), e);
		} finally {
			base.releaseConnection();
		}
		return response;
	}

	private void setHeaders(HttpRequestBase base, List<NameValuePair> headers) {
		if (null != headers) {
			for (NameValuePair header : headers) {
				base.setHeader(header.getName(), header.getValue());
			}
		}
	}
}
