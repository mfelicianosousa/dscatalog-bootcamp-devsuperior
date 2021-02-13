package mfs.net.br.dev.dscatalog.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import mfs.net.br.dev.dscatalog.entities.Category;
import mfs.net.br.dev.dscatalog.entities.Product;
import mfs.net.br.dev.dscatalog.entities.User;

public class UserDTO {

	private Long id ;
	private String login ;
	private String email ;
	private String Keyword ; 
	private String firstName ;
	private String lastName ;
	private String active ;
	private Date   dateExpires ;
	private String status ;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
		
	}

	public UserDTO(Long id, String login, String email, String keyword, String firstName,
			String lastName, String active, Date dateExpires, String status) {
		this.id = id;
		this.login = login;
		this.email = email;
		this.Keyword = keyword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.dateExpires = dateExpires;
		this.status = status;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		login = entity.getLogin();
		email = entity.getEmail();
		Keyword = entity.getKeyword() ;
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		active = entity.getActive();
		dateExpires = entity.getDateExpires();
		status = entity.getStatus();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getDateExpires() {
		return dateExpires;
	}

	public void setDateExpires(Date dateExpires) {
		this.dateExpires = dateExpires;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	
	
	
}
