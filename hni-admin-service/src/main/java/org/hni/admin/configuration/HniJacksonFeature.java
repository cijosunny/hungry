/**
 * 
 */
package org.hni.admin.configuration;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * @author Rahul K
 *
 */
public class HniJacksonFeature implements Feature {

	private static final ObjectMapper mapper = new ObjectMapper(){/**
		 * 
		 */
		private static final long serialVersionUID = 6621914698226301876L;

	{
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}};

	private static final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider() {
		{
			setMapper(mapper);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.core.Feature#configure(javax.ws.rs.core.FeatureContext)
	 */
	@Override
	public boolean configure(FeatureContext context) {
		context.register(provider);
		return true;
	}

}
