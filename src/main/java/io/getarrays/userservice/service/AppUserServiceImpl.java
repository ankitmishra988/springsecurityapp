package io.getarrays.userservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.getarrays.userservice.models.AppRole;
import io.getarrays.userservice.models.AppUser;
import io.getarrays.userservice.repo.AppRoleRepo;
import io.getarrays.userservice.repo.AppUserRepo;


@Service
@Transactional
public class AppUserServiceImpl implements AppUserService,UserDetailsService{
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);
	
	@Autowired
	private AppUserRepo appUserRepo;
	@Autowired
	private AppRoleRepo appRoleRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public AppUser saveuser(AppUser user) {
		log.info("Saving user to database"+user.getName());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));		
		return appUserRepo.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		log.info("Saving role to database"+role.getName());
		return appRoleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		log.info("adding role{} to user{}",username,rolename);
		AppUser user=appUserRepo.findByUsername(username);
		AppRole role=appRoleRepo.findByName(rolename);
		user.getRoles().add(role);
		
	}

	@Override
	public AppUser getUser(String username) {
		log.info("fetching user{}",username );
		return appUserRepo.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {
		log.info("fetching ALL userS{}");
		return appUserRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user= (AppUser) appUserRepo.findByUsername(username);
		if(user!=null) {
			log.info("user found"+username);
		}else {
			throw new UsernameNotFoundException("User not found"+username);
		}
		Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
		user.getRoles().forEach(role-> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});	
		UserDetails userdetail = new User(user.getUsername(), user.getPassword(), authorities);
		log.info("role"+authorities);
		return userdetail;
	}

	@Override
	public AppUser findUserById(Long id) {
			return appUserRepo.findByUserid(id);
		
	}

}
