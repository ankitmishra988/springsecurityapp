package io.getarrays.userservice.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.getarrays.userservice.service.AppUserServiceImpl;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);


	private static final String APPLICATION_JSON_VALUE = "application/json";

	
	@Autowired
	private AuthenticationManager authenticationManager;	
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
//		User user=(User) request.getUserPrincipal();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
//		String username=user.getUsername();
//		String password=user.getPassword();
		log.info("username is",username);
		log.info("password is",password);
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		User user=(User) authentication.getPrincipal();
		Algorithm algorithm=Algorithm.HMAC256("@127%&^8QWSDFCVGTRESDWSXSSDKJHNJKVGDYUDCH".getBytes());
		String access_token=JWT.create().
				withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		
		String referesh_token=JWT.create().
				withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);		
//		response.setHeader("access_token", access_token);
//		response.setHeader("referesh_token", referesh_token);
	
		Map<String,String> token=new HashMap<>();
		token.put("access_token", access_token);
		token.put("referesh_token", referesh_token);
		response.setContentType(APPLICATION_JSON_VALUE);
	 new ObjectMapper().writeValue(response.getOutputStream(), token);
		
	}
	

}
