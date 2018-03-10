package org.hni.admin.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class ResponseCorsFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
	    MultivaluedMap<String, Object> headers = responseContext.getHeaders();
	
	    headers.add("Access-Control-Allow-Origin", "*");
	    headers.add("Access-Control-Max-Age", "180"); // cache preflight request for 180 seconds
	    headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	    headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, X-hni-token, origin, content-type, accept");
    }
    
}
