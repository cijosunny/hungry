/**
 * 
 */
package org.hni.admin.service;

import java.io.File;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

/**
 * @author Rahul
 *
 */
@Api(value = "/help")
@SwaggerDefinition(info = @Info(description = "provides methods hni documents and associated resources", version = "v1", title = "Help API"))
@Component
@Path("/help")
public class HniServiceController extends AbstractBaseController {

	private static final String FILE_PATH_VOLUNTEER_INSTRUCTION = "instructions/HNI_Volunteer_Guide_Meal_Orders.pdf";
	private static final String VOLUNTEER_INSTRUCTION_PDF = "volunteer-guide.pdf";
	private static final String FILE_PATH_CLIENT_FAQ = "instructions/HNI_FAQ.pdf";
	private static final String CLIENT_FAQ_PDF = "HungerNotImpossibleFrequentlyAskedQuestions.pdf";
	private static final String FILE_PATH_PRIVACY_POLICY = "instructions/HNI_Privacy_Policy.pdf";
	private static final String HNI_PRIVACY_POLICY = "HungerNotImpossiblePrivacyPolicy.pdf";

	@GET
	@Path("/volunteer/instruction/pdf")
	@Produces("application/pdf")
	public Response getVolunteerInstructionPdf() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        if (classLoader == null) {
	            classLoader = HniServiceController.class.getClassLoader();
	        }
	        DataSource footerDs = new URLDataSource(classLoader.getResource(FILE_PATH_VOLUNTEER_INSTRUCTION));
		File file = new File(footerDs.getName());
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=\" " + VOLUNTEER_INSTRUCTION_PDF + "\"");
			return response.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/client/faq/pdf")
	@Produces("application/pdf")
	public Response getClientFAQPdf() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        if (classLoader == null) {
	            classLoader = HniServiceController.class.getClassLoader();
	        }
	        DataSource footerDs = new URLDataSource(classLoader.getResource(FILE_PATH_CLIENT_FAQ));
		File file = new File(footerDs.getName());
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "inline; filename=\" " + CLIENT_FAQ_PDF + "\"");
			return response.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}
	
	@GET
	@Path("/privacy/policy/pdf")
	@Produces("application/pdf")
	public Response getPrivacyPolicyPdf() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        if (classLoader == null) {
	            classLoader = HniServiceController.class.getClassLoader();
	        }
	        DataSource footerDs = new URLDataSource(classLoader.getResource(FILE_PATH_PRIVACY_POLICY));
		File file = new File(footerDs.getName());
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "inline; filename=\" " + HNI_PRIVACY_POLICY + "\"");
			return response.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.noContent().build();
	}
}
