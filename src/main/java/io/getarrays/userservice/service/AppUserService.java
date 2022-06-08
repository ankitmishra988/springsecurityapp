package io.getarrays.userservice.service;

import java.util.ArrayList;
import java.util.List;
import io.getarrays.userservice.models.AppRole;
import io.getarrays.userservice.models.AppUser;

public interface AppUserService {
	AppUser saveuser(AppUser user);
	AppRole saveRole(AppRole role);
	void addRoleToUser(String username,String rolename);
	AppUser getUser(String username);
	List<AppUser> getUsers();
	AppUser findUserById(Long id);
}
