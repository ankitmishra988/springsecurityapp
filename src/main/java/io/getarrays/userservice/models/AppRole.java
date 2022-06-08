package io.getarrays.userservice.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AppRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Collection<AppUser> users=new ArrayList<>();
	
	public Collection<AppUser> getUsers() {
		return users;
	}
	public void setUsers(Collection<AppUser> users) {
		this.users = users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AppRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public AppRole() {
		
	}
	
}
