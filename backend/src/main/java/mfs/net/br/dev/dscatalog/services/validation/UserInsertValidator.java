package mfs.net.br.dev.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import mfs.net.br.dev.dscatalog.dto.UserInsertDTO;
import mfs.net.br.dev.dscatalog.entities.User;
import mfs.net.br.dev.dscatalog.repositories.UserRepository;
import mfs.net.br.dev.dscatalog.resources.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
	@Autowired
	private UserRepository repository ;
	
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		User user ;
		List<FieldMessage> list = new ArrayList<>();
		
		user = repository.findByEmail(dto.getEmail()) ;
		if( user != null) {
			
			list.add(new FieldMessage("email","Email já cadastrado")) ;
			
		}
		
		user = repository.findByLogin(dto.getLogin()) ;
		if( user != null) {
			
			list.add(new FieldMessage("login","Login já cadastrado")) ;
			
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}