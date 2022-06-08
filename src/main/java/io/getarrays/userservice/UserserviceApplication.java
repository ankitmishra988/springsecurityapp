package io.getarrays.userservice;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.getarrays.userservice.models.AppRole;
import io.getarrays.userservice.models.AppUser;
import io.getarrays.userservice.service.AppUserService;


@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	//test
	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		
		return args ->{
			
			appUserService.saveRole(new AppRole(null,"ROLE_ADMIN"));
			appUserService.saveRole(new AppRole(null,"ROLE_USER"));
			appUserService.saveRole(new AppRole(null,"ROLE_SUPER_ADMIN"));
			appUserService.saveRole(new AppRole(null,"ROLE_MANAGER"));

			
			appUserService.saveuser(new AppUser(null,"Ankit","Ankit1","test1","ankit@mawaimail.com",
					new ArrayList<>()));
			
			appUserService.saveuser(new AppUser(null,"Vivek","Vivek1","test2","Vivek@mawaimail.com",
					new ArrayList<>()));
			appUserService.saveuser(new AppUser(null,"Prashant","Prashant1","test1","Prashant@mawaimail.com",
					new ArrayList<>()));
			appUserService.saveuser(new AppUser(null,"Akash","Akash1","test4","Akash@mawaimail.com",
					new ArrayList<>()));
			
			
			appUserService.addRoleToUser("Ankit1", "ROLE_ADMIN");
			appUserService.addRoleToUser("Vivek1", "ROLE_USER");
			appUserService.addRoleToUser("Prashant1", "ROLE_SUPER_ADMIN");
			appUserService.addRoleToUser("Akash1", "ROLE_MANAGER");
				
		};
		
	}
	
}
