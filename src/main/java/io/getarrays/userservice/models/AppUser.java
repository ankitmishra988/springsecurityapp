package io.getarrays.userservice.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Userid;
	private String name;
	private String username;
	private String password;
	private String email;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name = "AppUserRole", 
	        joinColumns = { @JoinColumn(name = "Userid") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id")})
	private Collection<AppRole> roles=new ArrayList<>();
	
	public Long getId() {
		return Userid;
	}
	public void setId(Long id) {
		this.Userid = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection<AppRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}

	public AppUser() {
	
	}
	
	public AppUser(Long id, String name, String username, String password, String email, Collection<AppRole> roles) {
		super();
		this.Userid = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	
	
}
