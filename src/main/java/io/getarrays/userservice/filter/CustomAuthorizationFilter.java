package io.getarrays.userservice.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.getarrays.userservice.service.AppUserServiceImpl;

import static java.util.Arrays.stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CustomAuthorizationFilter extends OncePerRequestFilter{
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);
	private static final String APPLICATION_JSON_VALUE = "application/json";
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		}else {
			String authrizationHeader=request.getHeader(AUTHORIZATION);
			if(authrizationHeader != null && authrizationHeader.startsWith("Bearer ")) {
				try {
					String token=authrizationHeader.substring("Bearer ".length());
					Algorithm algorithm=Algorithm.HMAC256("@127%&^8QWSDFCVGTRESDWSXSSDKJHNJKVGDYUDCH".getBytes());
					JWTVerifier verifier=JWT.require(algorithm).build();
					DecodedJWT decodedJWT=verifier.verify(token);
					String username=decodedJWT.getSubject();
					String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
					stream(roles).forEach(role -> {
						
						authorities.add(new SimpleGrantedAuthority(role));
					});
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
							new UsernamePasswordAuthenticationToken(username, null,authorities);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					filterChain.doFilter(request, response);
					
					
				} catch (Exception e) {
					log.error("Error logging",e.getMessage());					
				response.setHeader("Error", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				//response.sendError(FORBIDDEN.value());
				Map<String,String> error=new HashMap<>();
				error.put("Error_Meassge", e.getMessage());
				response.setContentType(APPLICATION_JSON_VALUE);
			    new ObjectMapper().writeValue(response.getOutputStream(), error);
				
				
				}
				
			}else {
				filterChain.doFilter(request, response);
			}
		}
			
	
		
	}

}
