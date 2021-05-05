package com.carmen.app.filters;

import java.util.Calendar;
import java.util.Date;

import javax.naming.AuthenticationException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.net.HttpHeaders;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author Carmen Piñera Moreno
 *
 */
@Path("/authenticatiton")
public class Authentication {


	@POST
	public Response authenticateUser(@QueryParam("username") String username,@QueryParam("password") String password) {
		
		try {
			authenticate(username,password);
			
			String token = generateToken(username);
			
			return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
			
		}catch(Exception e) {
			
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	private String generateToken(String username) {
		Date creation = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(creation);
		calendar.add(Calendar.MINUTE, 60);
		Date expired = calendar.getTime();
		
		String jwtToken = Jwts.builder()
				.setSubject(username)
				.setIssuer("http://localhost:8080/car-app/authentication")
				.setIssuedAt(creation)
				.setExpiration(expired)
				.signWith(SignatureAlgorithm.HS512, JwtFilter.KEY)
				.compact();
		return jwtToken;
	}
	private String authenticate(String username, String password) throws AuthenticationException{
		if ("username".equals(username) && "username".equals(password)) {
            return "username";
        } else if ("admin".equals(username) && "admin".equals(password)) {
            return "admin";
        } else {
            throw new AuthenticationException();
        }
	}
	
}
