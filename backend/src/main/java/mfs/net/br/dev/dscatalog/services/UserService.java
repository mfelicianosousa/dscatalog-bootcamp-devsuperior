package mfs.net.br.dev.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mfs.net.br.dev.dscatalog.dto.RoleDTO;
import mfs.net.br.dev.dscatalog.dto.UserDTO;
import mfs.net.br.dev.dscatalog.dto.UserInsertDTO;
import mfs.net.br.dev.dscatalog.dto.UserUpdateDTO;
import mfs.net.br.dev.dscatalog.entities.Role;
import mfs.net.br.dev.dscatalog.entities.User;
import mfs.net.br.dev.dscatalog.repositories.RoleRepository;
import mfs.net.br.dev.dscatalog.repositories.UserRepository;
import mfs.net.br.dev.dscatalog.services.exceptions.DatabaseException;
import mfs.net.br.dev.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder ;
	
	@Autowired
	private UserRepository repository ;
	
	@Autowired
	private RoleRepository roleRepository ;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<User> list = repository.findAll( pageRequest ) ;
		
		return list.map( x -> new UserDTO(x)) ;
		
		
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id) ;
		User entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity ) ;
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity( dto, entity ) ;
		entity.setPassword( passwordEncoder.encode( dto.getPassword()));
		entity = repository.save( entity ) ;
		return new UserDTO( entity ) ;
	}

	

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
		  User entity = repository.getOne(id);
		  copyDtoToEntity( dto, entity ) ;
		  entity = repository.save(entity); 
		  return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found "+id ) ;
			
		}
		
	}  
	
	public void delete(Long id) {
		try {
		  repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not Found "+id) ;
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation") ;
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		
		entity.setFirstName( dto.getFirstName());
		entity.setLastName(dto.getFirstName());
		entity.setLogin( dto.getLogin());
		entity.setEmail(dto.getEmail());
		entity.setKeyword(dto.getKeyword());
		entity.setStatus(dto.getStatus());
		entity.setActive(dto.getActive());
		entity.setDateExpires(dto.getDateExpires());
		
		
		entity.getRoles().clear();
		for ( RoleDTO roleDto : dto.getRoles()) {
			
			Role role = roleRepository.getOne( roleDto.getId()) ;
			entity.getRoles().add(role);
		} 
	}

}
