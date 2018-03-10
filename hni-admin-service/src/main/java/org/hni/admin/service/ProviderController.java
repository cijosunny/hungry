package org.hni.admin.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.common.exception.HNIException;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.service.ProviderLocationService;
import org.hni.provider.service.ProviderService;
import org.hni.service.helpers.ProviderResourceHelper;
import org.hni.user.dao.AddressDAO;
import org.hni.user.om.Address;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/providers", description = "Operations on Providers and ProviderLocations")
@Component
@Path("/providers")
public class ProviderController extends AbstractBaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @Inject
    private ProviderService providerService;
    @Inject
    private ProviderLocationService providerLocationService;
    @Inject
    private AddressDAO addressDao;
    @Inject
    private ProviderResourceHelper providerResourceHelper;
    

    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns the Provider with the given id"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Provider getProvider(@PathParam("id") Long id) {
        return providerService.get(id);
    }

    @POST
    @Path("/provider/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Creates a new Provider or saves the Provider with the given id"
            , notes = "An Provider without an ID field will be created"
            , response = Provider.class
            , responseContainer = "")
    public String saveProvider(Provider provider) {
    	if (getLoggedInUser() != null) {
    		return providerResourceHelper.createProvider(provider, getLoggedInUser());
    	}
    	
    	throw new HNIException("You must have elevated permissions to do this.");
    }

    @POST
    @Path("/provider/update")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Creates a new Provider or saves the Provider with the given id"
            , notes = "An Provider without an ID field will be created"
            , response = Provider.class
            , responseContainer = "")
    public Response updateProvider(Provider provider) {
    	logger.debug("Request reached to update provider " + provider);
		User loggedInUser = getLoggedInUser();
		if(getLoggedInUser() != null){
			Map<String, Object> response = new HashMap<>();
			try {
				providerResourceHelper.updateProvider(provider, getLoggedInUser());
				response.put(Constants.RESPONSE, Constants.SUCCESS);
				response.put(Constants.MESSAGE, "Details saved successfully.");
			} catch (Exception e) {
				logger.error("Error in update provider  :" + e.getMessage(), e);
				response.put(Constants.RESPONSE, Constants.ERROR);
				response.put(Constants.MESSAGE, "Save Failed. Please try again.");
			}
			return Response.ok(response).build();
		}
    	throw new HNIException("You must have elevated permissions to do this.");
    }

    
    @POST
    @Path("/{id}/addresses")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Adds an address to a Provider"
            , notes = "Use the /addresses API to update addresses"
            , response = Provider.class
            , responseContainer = "")
    public Provider addAddressToProvider(@PathParam("id") Long id, Address address) {
        if (isPermitted(Constants.PROVIDER, Constants.UPDATE, id)) {
            Provider provider = providerService.get(id);
            if (null != provider) {
                provider.setAddress(address);
                providerService.save(provider);
            }
            return provider;
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }

    @DELETE
    @Path("/{id}/addresses/{addressId}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Removes the address from a provider"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Provider removeAddressFromProvider(@PathParam("id") Long id, @PathParam("addressId") Long addressId) {
        if (isPermitted(Constants.PROVIDER, Constants.DELETE, id)) {
            Provider provider = providerService.get(id);
            if (null != provider) {
                Address address = addressDao.get(addressId);
                if (null != address) {
                    provider.setAddress(null); // Hibernate will manage the mapping table for us.
                    providerService.save(provider);
                }
            }
            return provider;
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }

    /**
     * Provider Locations
     **/

    @GET
    @Path("/{id}/providerLocations")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns a collection of ProviderLocations for the given Provider"
            , notes = ""
            , response = ProviderLocation.class
            , responseContainer = "")
    public Collection<ProviderLocation> getProviderLocations(@PathParam("id") Long id) {
        return providerLocationService.with(new Provider(id));
    }

    @GET
    @Path("/providerLocations")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns a collection of ProviderLocations for the given customer"
            , notes = ""
            , response = ProviderLocation.class
            , responseContainer = "")
    public Collection<ProviderLocation> getProviderLocationsByCustomerAddress(
            @QueryParam("customerId") Long custId,
            @NotNull @QueryParam("address") String customerAddress,
            @QueryParam("itemsPerPage") int itemsPerPage,
            @QueryParam("pageNumber") int pageNum) {
        if (!StringUtils.isBlank(customerAddress)) {
            // ### TODO: The last two arguments are no-ops right now. These are place holders for when the efficient geo-search
            // ### algorithm is brought back into play.
            // Github issue #58 - https://github.com/hungernotimpossible/hni/issues/58
            Address address = providerLocationService.searchCustomerAddress(customerAddress);
            return providerLocationService.providersNearCustomer(address, itemsPerPage, 0, 0);
        }
        return null;
    }

    @POST
    @Path("/provider/{id}/location/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Adds a ProviderLocation for the given Provider"
            , notes = ""
            , response = ProviderLocation.class
            , responseContainer = "")
    public ProviderLocation addProviderLocation(@PathParam("id") Long id, ProviderLocation providerLocation) {
        if (getLoggedInUser() != null) {
        	return providerResourceHelper.addProviderLocation(id, getLoggedInUser(), providerLocation);
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }

    @DELETE
    @Path("/{id}/providerLocations/{plid}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Deletes given ProviderLocation for the given Provider"
            , notes = ""
            , response = ProviderLocation.class
            , responseContainer = "")
    public ProviderLocation addProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid) {
        if (isPermitted(Constants.PROVIDER, Constants.DELETE, id)) {
            ProviderLocation providerLocation = providerLocationService.get(plid);
            if (providerLocation.getProvider().getId().equals(id)) {
                providerLocationService.delete(new ProviderLocation(plid));
            }
            //TODO: throw error?
            return null;
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }

    @POST
    @Path("/{id}/providerLocations/{plid}/addresses")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Adds an address to a ProviderLocation"
            , notes = "Use the /addresses API to update addresses"
            , response = Provider.class
            , responseContainer = "")
    public ProviderLocation addAddressToProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid, Address address) {
        if (isPermitted(Constants.PROVIDER, Constants.UPDATE, id)) {
            ProviderLocation providerLocation = providerLocationService.get(plid);
            if (providerLocation.getProvider().getId().equals(id)) {
                providerLocation.setAddress(address);
                providerLocationService.save(providerLocation);
            }

            return providerLocation;
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }

    @DELETE
    @Path("/{id}/providerLocations/{plid}/addresses/{addressId}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Removes the address from a providerLocation"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public ProviderLocation removeAddressFromProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid, @PathParam("addressId") Long addressId) {
        if (isPermitted(Constants.PROVIDER, Constants.DELETE, id)) {
            ProviderLocation providerLocation = providerLocationService.get(plid);
            if (providerLocation.getProvider().getId().equals(id)) {
                Address address = addressDao.get(addressId);
                if (null != address) {
                    providerLocation.setAddress(null); // Hibernate will manage the mapping table for us.
                    providerLocationService.save(providerLocation);
                }
            }

            return providerLocation;
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }
    
    @GET
	@Path("/{id}/details")
	@Produces("application/json")
	public String getProviderDetails(@PathParam("id") Long providerId) {
    	logger.debug("Request reached to retrieve provider details " + providerId);
		User loggedInUser = getLoggedInUser();
		return  serializeProviderToJson(providerService.getProviderDetails(providerId, loggedInUser));
	}
	
	private String serializeProviderToJson(Provider provider) {
		try {
			String json = mapper.writeValueAsString(JsonView.with(provider)
					.onClass(Provider.class, Match.match().exclude("*").include("id", "name", "websiteUrl", "address"))
					.onClass(Address.class, Match.match().exclude("*").include("address1", "address2", "city", "state", "zip", "latitude", "longitude")));
				
			return json;
		} catch (Exception e) {
			logger.error("Serializing Client object:"+e.getMessage(), e);
		}
		return "{}";
	}
	
	@GET
	@Path("/{id}/locations")
	@Produces("application/json")
	public Response getProviderLocation(@PathParam("id") Long providerId) {
		logger.debug("Request reached to retrieve provider locations " + providerId);
		User loggedInUser = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			List<ProviderLocation> providerLocations = providerService.getProviderLocations(providerId, loggedInUser);
			response.put("headers", HNIUtils.getReportHeaders(80, true));
			response.put("data", providerLocations);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
		} catch (Exception e) {
			logger.error("Error in get ProviderLocation Service:" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/update/locations")
	@Produces("application/json")
	public Response updateProviderLocation(List<ProviderLocation> providerLocations) {
		logger.debug("Request reached to update provider locations " + providerLocations);
		User loggedInUser = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			providerLocationService.updateProviderLocations(providerLocations, loggedInUser);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "Details saved successfully.");
		} catch (Exception e) {
			logger.error("Error in update ProviderLocations :" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
			response.put(Constants.MESSAGE, "Save Failed. Please try again.");
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/providerLocation/activate")
	@Produces("application/json")
	public Response activateProviderLocationStatus(List<ProviderLocation> providerLocations) {
		logger.debug("Request reached to update provider location status ");
		User loggedInUser = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			providerLocationService.updateProviderLocationStatus(providerLocations, true);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "Provider location(s) activated.");
		} catch (Exception e) {
			logger.error("Error in update ProviderLocations :" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
			response.put(Constants.MESSAGE, "Save Failed. Please try again.");
		}
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/providerLocation/de-activate")
	@Produces("application/json")
	public Response deactivateProviderLocationStatus(List<ProviderLocation> providerLocations) {
		logger.debug("Request reached to update provider location status ");
		User loggedInUser = getLoggedInUser();
		Map<String, Object> response = new HashMap<>();
		try {
			providerLocationService.updateProviderLocationStatus(providerLocations, false);
			response.put(Constants.RESPONSE, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "Provider location(s) de-activated.");
		} catch (Exception e) {
			logger.error("Error in update ProviderLocations :" + e.getMessage(), e);
			response.put(Constants.RESPONSE, Constants.ERROR);
			response.put(Constants.MESSAGE, "Save Failed. Please try again.");
		}
		return Response.ok(response).build();
	}
	
	@DELETE
    @Path("/{id}/{plid}/delete")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Deletes given ProviderLocation for the given Provider"
            , notes = ""
            , response = Response.class
            , responseContainer = "")
    public Response deleteProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid) {
		logger.debug("Request reached to delete provider location status ");
		Map<String, Object> response = new HashMap<>();
        if (isPermitted(Constants.PROVIDER, Constants.DELETE, id)) {
            try {
            	providerResourceHelper.deleteProviderLocation(id, plid);
    			response.put(Constants.RESPONSE, Constants.SUCCESS);
    			response.put(Constants.MESSAGE, "Provider location deleted.");
    		} catch (Exception e) {
    			logger.error("Error in delete ProviderLocations :" + e.getMessage(), e);
    			response.put(Constants.RESPONSE, Constants.ERROR);
    			response.put(Constants.MESSAGE, "Deletion Failed. Please try again.");
    		}
    		return Response.ok(response).build();
        }
        throw new HNIException("You must have elevated permissions to do this.");
    }
	
	@POST
    @Path("/provider/delete")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Deletes provider"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Map<String, String> removeProvider(List<Long> providerIds) {
		logger.debug("Request reached to delete provider");
		User loggedInUser = getLoggedInUser();
		if(loggedInUser != null)
			return providerService.deleteProviders(providerIds, loggedInUser);
		else
			 throw new HNIException("You must have elevated permissions to do this.");	
    }
	
	@POST
    @Path("/activate/provider/{value}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Activate/De-activate provider"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Map<String, String> activateProviders(List<Long> providerIds, @PathParam("value") Boolean value) {
		logger.debug("Request reached to delete provider");
		User loggedInUser = getLoggedInUser();
		if(loggedInUser != null)
			return providerService.activateProviders(providerIds, !value, loggedInUser);
		else
			 throw new HNIException("You must have elevated permissions to do this.");	
    }
}
