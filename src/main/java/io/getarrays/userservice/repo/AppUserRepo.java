package io.getarrays.userservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.getarrays.userservice.models.AppUser;

@Repository
public interface AppUserRepo  extends JpaRepository<AppUser, Long>{
	AppUser findByUsername(String username);
	
    @Query("Select u from AppUser u WHERE u.Userid=:userid")
	AppUser findByUserid(long userid);

}
