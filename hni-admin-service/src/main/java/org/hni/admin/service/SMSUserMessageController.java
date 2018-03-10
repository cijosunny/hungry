package org.hni.admin.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.hni.admin.service.dto.CustomSMSMessageDto;
import org.hni.admin.service.dto.CustomSMSMessageResponseDto;
import org.hni.common.Constants;
import org.hni.common.exception.HNIException;
import org.hni.events.service.EventRouter;
import org.hni.events.service.om.Event;
import org.hni.provider.om.Provider;
import org.hni.sms.service.provider.PushMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Api(value = "/usermessage", description = "endpoint to accept SMS messages")
@Component
@Path("/usermessage")
public class SMSUserMessageController extends AbstractBaseController {
    private static final Logger logger = LoggerFactory.getLogger(SMSUserMessageController.class);

    @Inject
    private EventRouter eventRouter;
    
    @Inject
    private PushMessageService pushMessageService;

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as HTML"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public String respondToMessageHTML(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, Constants.MSG_BODY);
    	String fromNum = getQueryParamValue(params, Constants.MSG_FROM);
    	String fromState = getQueryParamValue(params, Constants.MSG_FROM_STATE);
    	setUserRequestState(fromState);
    	logger.info("In First Method");
    	logger.info("HTML/Received a message, body={}, fromNum={}, fromState={}", body, fromNum, fromState);
        final Event event = Event.createEvent("text/html", parsePhoneNumber(fromNum), body);
        
        return String.format(Constants.MSG_RESPONSE_FORMAT, eventRouter.handleEvent(event));
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as text"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public String respondToMessage(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, Constants.MSG_BODY);
    	String fromNum	 = getQueryParamValue(params, Constants.MSG_FROM);
    	String fromState = getQueryParamValue(params, Constants.MSG_FROM_STATE);
    	
    	setUserRequestState(fromState);
    	logger.info("In 2nd Method");
    	logger.info("HTML/Received a message, body={}, fromNum={}, fromState={}", body, fromNum, fromState);
        
        final Event event = Event.createEvent("text/plain", parsePhoneNumber(fromNum), body);
        
        try {
            return String.format(Constants.MSG_RESPONSE_FORMAT, eventRouter.handleEvent(event));
        } catch (Exception ex) {
            throw new HNIException("Something went wrong. Please try again later.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "processes an SMS workflow and returns the response as JSON"
            , notes = ""
            , response = Provider.class
            , responseContainer = "")
    public Map<String, Object> respondToMessageJson(MultivaluedMap<String, String> params) {
    	String body = getQueryParamValue(params, "Body");
    	String fromNum	 = getQueryParamValue(params, "fromNum");
    	String fromState = getQueryParamValue(params, Constants.MSG_FROM_STATE);
    	
    	setUserRequestState(fromState);
    	logger.info("In 3rd Method");
    	logger.info("JSON/Received a message, body={}, fromNum={}",body,fromNum);
        final Event event = Event.createEvent("text/plain", parsePhoneNumber(fromNum), body);
        
        Map<String, Object> res = new HashMap<>();
        try {
            final String returnMessage = eventRouter.handleEvent(event);
            String[] output = {returnMessage};
            res.put("message", output);
            res.put("status", Response.Status.OK.getStatusCode());
        } catch (HNIException ex) {
            logger.error("Error handling request: {}", ex.getMessage());
            res.put("error", ex.getMessage());
            res.put("status", ex.getResponse().getStatus());
            throw new HNIException(Response.status(ex.getResponse().getStatus()).entity(res).build());
        } catch (Exception ex) {
            logger.error("Internal error handling request: {}", ex.getMessage());
            ex.printStackTrace();
            res.put("error", "Something went wrong. Please try again later.");
            res.put("status", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            throw new HNIException(Response.serverError().entity(res).build());
        }
        return res;
    }
    
    @POST
    @Path("/custom/")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "sends SMS message to all participants."
            , notes = ""
            , response = String.class
            , responseContainer = "")
    public Response sendCustomMessage(CustomSMSMessageDto customSMSMessage) {
    	Map<String, String> response = new HashMap<>();
   		CustomSMSMessageResponseDto customResponse = pushMessageService.sendCustomMessage(customSMSMessage);
   		return Response.ok(customResponse).build();
    }

	private String getQueryParamValue(MultivaluedMap<String, String> params, String key){
    	return params.get(key).get(0);
    }
    
    private String parsePhoneNumber(String phoneNumber) {
    	if (phoneNumber != null) {
    		phoneNumber = phoneNumber.replace("%2B1", "");
    		phoneNumber = phoneNumber.replace("+1", "");
    	}
    	
    	return phoneNumber;
    }
}
