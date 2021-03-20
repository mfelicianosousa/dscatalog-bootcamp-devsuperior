package mfs.net.br.dev.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger( UserService.class ) ;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder ;
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private RoleRepository roleRepository ;
	
		
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<User> list = userRepository.findAll( pageRequest ) ;
		
		return list.map( x -> new UserDTO(x)) ;
		
		
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		
		Optional<User> obj = userRepository.findById(id) ;
		
		User entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new UserDTO(entity) ;
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		
		User entity = new User();
		copyDtoToEntity( dto, entity ) ;
		entity.setPassword(  passwordEncoder.encode(dto.getPassword())) ;
		entity = userRepository.save( entity ) ;
		return new UserDTO( entity ) ;
	}

	

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
		  User entity = userRepository.getOne(id);
		  copyDtoToEntity( dto, entity ) ;
	
		  entity = userRepository.save(entity); 
		  return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found "+id ) ;
			
		}
		
	}
    
	
	public void delete(Long id) {
		try {
		  userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not Found "+id) ;
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation") ;
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName( dto.getFirstName());
		entity.setLastName( dto.getLastName());
		entity.setEmail( dto.getEmail());
		//
		
		entity.getRoles().clear();
		for ( RoleDTO roleDto : dto.getRoles()) {
			
			Role role = roleRepository.getOne( roleDto.getId()) ;
			entity.getRoles().add(role);
		} 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		User user = userRepository.findByEmail(username) ;
		if (user == null) {
			logger.error("User not found : "+username);
			throw new UsernameNotFoundException("Email not found") ;
		}
		logger.info("User found : "+username);
		return user;
	}

}
