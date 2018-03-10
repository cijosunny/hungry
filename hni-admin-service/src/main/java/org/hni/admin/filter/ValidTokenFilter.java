package org.hni.admin.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.MissingClaimException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.hni.common.Constants;
import org.hni.security.realm.token.JWTTokenFactory;

/**
 * This is a Shiro filter that is referenced in the shiro configuration for
 * URL's. This filter intercepts requests requiring a token, grabs the token,
 * validates it and binds the claims onto the ThreadLocal which will be used in
 * the Authorization phase of shiro security within the TokenRealm.
 * 
 * @author jeff3parker
 *
 */
@Deprecated
// JWTTokenAuthenticatingFilter
public class ValidTokenFilter extends AuthorizationFilter {
	private static final Log LOG = LogFactory.getLog(ValidTokenFilter.class);
	private static final String TOKEN_HEADER = "X-hni-token";

	// TODO: make these values dynamically injected
	private static final String KEY = "YbpWo521Z/aF7DqpiIpIHQ==";
	private static final String ISSUER = "test-issuer";

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String tokenValue = httpRequest.getHeader(TOKEN_HEADER);
		return isValid(tokenValue);
	}

	private boolean isValid(String tokenValue) {
		boolean response = false;
		try {
			// if the token is valid we'll put the claims onto the ThreadLocal
			// for processing by the TokenRealm
			Claims claims = JWTTokenFactory.decode(tokenValue, KEY, ISSUER);
			ThreadContext.put(Constants.USERID, new Long(claims.get(Constants.USERID, Integer.class).longValue()));
			ThreadContext.put(Constants.PERMISSIONS, claims.get(Constants.PERMISSIONS, String.class));
			response = true;
		} catch (MissingClaimException | IncorrectClaimException | ExpiredJwtException mce) {
			LOG.warn(mce, mce);
		}
		return response;
	}
}