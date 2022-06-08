package io.getarrays.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.getarrays.userservice.models.AppRole;


@Repository
public interface AppRoleRepo extends JpaRepository<AppRole, Long>{
	AppRole findByName(String name);
	
}


