package io.getarrays.userservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.getarrays.userservice.models.AppRole;
import io.getarrays.userservice.models.AppUser;
import io.getarrays.userservice.service.AppUserService;
import io.getarrays.userservice.service.AppUserServiceImpl;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserREsource {
	
	@Autowired
	private AppUserService appUserService;
	private static final String APPLICATION_JSON_VALUE = "application/json";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);
	
	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		return ResponseEntity.ok().body(appUserService.getUsers());
					
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser){
		URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
				path("/api/user/save").toUriString());
		
		return ResponseEntity.created(uri).body(appUserService.saveuser(appUser));					
	}
	
	@PostMapping("/role/save")
	public ResponseEntity<AppRole> saveRole(@RequestBody AppRole appRole){
		URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().
				path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(appUserService.saveRole(appRole));					
	}

	@PostMapping("/role/addtouser")
	public ResponseEntity<AppRole> saveRole(@RequestBody RoleToUser roleToUser){
		appUserService.addRoleToUser(roleToUser.getUsername(),roleToUser.getRolename());
		return ResponseEntity.ok().build();					
	}
	
	@GetMapping("/users/{userid}")
	public AppUser getUserbyUserid(@PathVariable("userid") Long userid){
		return  appUserService.findUserById(userid);			
	}
//	
//	@GetMapping("/role/addtouser")
//	public void refereshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException{
//		String authrizationHeader=request.getHeader(AUTHORIZATION);
//		if(authrizationHeader != null && authrizationHeader.startsWith("Bearer ")) {
//			try {
//				String token=authrizationHeader.substring("Bearer ".length());
//				Algorithm algorithm=Algorithm.HMAC256("@127%&^8QWSDFCVGTRESDWSXSSDKJHNJKVGDYUDCH".getBytes());
//				JWTVerifier verifier=JWT.require(algorithm).build();
//				DecodedJWT decodedJWT=verifier.verify(token);
//				String username=decodedJWT.getSubject();
//				
//				String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
//				Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
//				stream(roles).forEach(role -> {
//					
//					authorities.add(new SimpleGrantedAuthority(role));
//				});
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
//						new UsernamePasswordAuthenticationToken(username, null,authorities);
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//				
//				
//				
//			} catch (Exception e) {
//				log.error("Error logging",e.getMessage());					
//			response.setHeader("Error", e.getMessage());
//			response.setStatus(FORBIDDEN.value());
//			//response.sendError(FORBIDDEN.value());
//			Map<String,String> error=new HashMap<>();
//			error.put("Error_Meassge", e.getMessage());
//			response.setContentType(APPLICATION_JSON_VALUE);
//		    new ObjectMapper().writeValue(response.getOutputStream(), error);
//			
//			
//			}
//			
//		}
//				
//	}


}


 class RoleToUser {
	
	private String username;
	private String rolename;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
}
