package mfs.net.br.dev.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import mfs.net.br.dev.dscatalog.services.exceptions.DatabaseException;
import mfs.net.br.dev.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler( ResourceNotFoundException.class)
	public ResponseEntity< StandardError > entityNotFound( ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND ;
		StandardError err = new StandardError();
		err.setTimeStamp(Instant.now());
		err.setStatus( status.value() );
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
		
	}
	
	@ExceptionHandler( DatabaseException.class)
	public ResponseEntity< StandardError > database( DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST ;
		StandardError err = new StandardError();
		err.setTimeStamp(Instant.now());
		err.setStatus( status.value() );
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
		
	}
	
	@ExceptionHandler( MethodArgumentNotValidException.class)
	public ResponseEntity< ValidationError > Validation( MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY ;
		ValidationError err = new ValidationError();
		err.setTimeStamp(Instant.now());
		err.setStatus( status.value() );
		err.setError("Validation exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			// Adiciona o erro na lista de erros personalizados
			err.AddError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
		
	}

}
