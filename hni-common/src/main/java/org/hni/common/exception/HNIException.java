package org.hni.common.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//https://jersey.java.net/documentation/latest/representations.html 
public class HNIException extends WebApplicationException {

	public HNIException() {
		this(String.format("{\"message\":\"%s\"}", "could not find the resource"));
	}
	
	public HNIException(String message) {
		super(Response.status(Response.Status.UNAUTHORIZED).entity(String.format("{\"message\":\"%s\"}", message.toString())).type(MediaType.APPLICATION_JSON).build());
	}
	

	public HNIException(String message, Response.Status status) {
		super(Response.status(status).entity(String.format("{\"message\":\"%s\"}", message.toString())).type(MediaType.APPLICATION_JSON).build());
	}

	public HNIException(Response response) {
		super(response);
	}
	
}
