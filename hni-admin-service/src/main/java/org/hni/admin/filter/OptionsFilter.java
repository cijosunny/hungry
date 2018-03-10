package org.hni.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionsFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(OptionsFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if ( httpRequest.getMethod().equalsIgnoreCase("OPTIONS") ) {
			logger.info("responding with OK due to OPTIONS");
			httpResponse.setStatus(Response.Status.OK.getStatusCode());
			httpResponse.setContentType(MediaType.APPLICATION_JSON);

			httpResponse.addHeader("Access-Control-Allow-Origin", "*");
			httpResponse.addHeader("Access-Control-Max-Age", "180"); // cache preflight request for 180 seconds
			httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
			httpResponse.addHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, X-hni-token, origin, content-type, accept, user-type, act-code, invite-code");

		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
