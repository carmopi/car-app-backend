package com.carmen.app.filters;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.carmen.app.utils.Secured;
import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 
 * @author Carmen Piñera Moreno
 *
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@Secured
public class JwtFilter implements ContainerRequestFilter {

	public static final Key KEY = MacProvider.generateKey();
	
	private String roles;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP authorization header of the request
		String auth = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		try {

			// Get the header token
			String token = auth.substring("Bearer".length()).trim();

			// Validates the token
			Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);

			roles = (String) claims.getBody().get("roles");
			

		} catch (Exception e) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}

		final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
		requestContext.setSecurityContext(new SecurityContext() {
			@Override
			public Principal getUserPrincipal() {
				return currentSecurityContext.getUserPrincipal();
			}

			@Override
			public boolean isUserInRole(String rol) {
				if(roles.contains(rol)) {
					return true;
				}
				return false;
			}

			@Override
			public boolean isSecure() {
				return currentSecurityContext.isSecure();
			}

			@Override
			public String getAuthenticationScheme() {
				return currentSecurityContext.getAuthenticationScheme();
			}

		});
	}

}
