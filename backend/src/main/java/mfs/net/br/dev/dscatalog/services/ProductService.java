package mfs.net.br.dev.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mfs.net.br.dev.dscatalog.dto.ProductDTO;
import mfs.net.br.dev.dscatalog.entities.Product;
import mfs.net.br.dev.dscatalog.repositories.ProductRepository;
import mfs.net.br.dev.dscatalog.services.exceptions.DatabaseException;
import mfs.net.br.dev.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository ;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<Product> list = repository.findAll( pageRequest ) ;
		
		return list.map( x -> new ProductDTO(x)) ;
		
		
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Optional<Product> obj = repository.findById(id) ;
		
		Product entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new ProductDTO(entity, entity.getCAtegories()) ;
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		//entity.setName(dto.getName());
		
		entity = repository.save( entity ) ;
		return new ProductDTO( entity ) ;
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
		  Product entity = repository.getOne(id);
		//  entity.setName(dto.getName());
		  entity = repository.save(entity); 
		  return new ProductDTO(entity);
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

}
