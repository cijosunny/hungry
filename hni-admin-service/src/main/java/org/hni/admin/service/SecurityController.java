package org.hni.admin.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hni.provider.om.Provider;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/auth", description = "auth a user and response permissions")
@Component
@Path("/auth")
public class SecurityController {

	// TODO: this is just a placeholder
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns the Provider with the given id"
		, notes = ""
		, response = Provider.class
		, responseContainer = "")
	public Provider getProvider(@PathParam("id") Long id) {
		return null;
	}

}
