package mfs.net.br.dev.dscatalog.dto;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import mfs.net.br.dev.dscatalog.entities.User;

public class UserDTO {

	private Long id ;
	
	@NotBlank(message = "Preenchimento do campo obrigátorio")
	private String login ;
	
	@Email(message="Favor entrar com um email válido")
	private String email ;
	
	private String Keyword ; 
	
	@NotBlank(message = "Preenchimento do campo obrigátorio")
	private String  firstName ;
	private String  lastName ;
	private Boolean enabled ;
	private Date 	expiryDate ;
	private Boolean accountExpired ;
	private Boolean accountLocked ;
	private Boolean credentialExpired ;
	private Date 	dateCreated ;
	private Date    dateUp ;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
		
	}

	public UserDTO(Long id, String login, String email, String keyword, String firstName,
			String lastName, Boolean enabled, Date expiryDate, Boolean accountExpired, 
			Boolean accountLocked, Boolean credentialExpired, Date 	dateCreated,
			Date    dateUp ) {
		this.id = id;
		this.login = login;
		this.email = email;
		this.Keyword = keyword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
		this.expiryDate = expiryDate;
		this.accountExpired = accountExpired ;
		this.accountLocked = accountLocked ;
		this.credentialExpired = credentialExpired ;
		this.dateCreated = dateCreated ;
		this.dateUp = dateUp ;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		login = entity.getLogin();
		email = entity.getEmail();
		Keyword = entity.getKeyword() ;
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		enabled = entity.getEnabled();
		expiryDate = entity.getExpiryDate();
		accountExpired = entity.getAccountExpired() ;
		accountLocked = entity.getAccountLocked() ;
		credentialExpired = entity.getCredentialExpired() ;
		dateCreated = entity.getDateCreated() ;
		dateUp = entity.getDateUp() ;

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

	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Boolean getCredentialExpired() {
		return credentialExpired;
	}

	public void setCredentialExpired(Boolean credentialExpired) {
		this.credentialExpired = credentialExpired;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUp() {
		return dateUp;
	}

	public void setDateUp(Date dateUp) {
		this.dateUp = dateUp;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	
	
	
}
