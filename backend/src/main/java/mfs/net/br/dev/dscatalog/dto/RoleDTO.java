package mfs.net.br.dev.dscatalog.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import mfs.net.br.dev.dscatalog.entities.Category;
import mfs.net.br.dev.dscatalog.entities.Product;
import mfs.net.br.dev.dscatalog.entities.Role;

public class RoleDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id ;
	
	@NotBlank(message = "Campo obrigátorio")
	private String authority ;
	
	public RoleDTO(){
	}
	
	public RoleDTO(Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	public RoleDTO( Role entity) {
		id = entity.getId();
		authority = entity.getAuthority();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
