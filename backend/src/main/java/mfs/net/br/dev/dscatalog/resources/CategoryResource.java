package mfs.net.br.dev.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mfs.net.br.dev.dscatalog.entities.Category;
import mfs.net.br.dev.dscatalog.services.CategoryService;

/*
 * 	Implementa o controlador REST
 *  A api vai ser implementadas por meio dos controladores (resources) REST
 *  Os recursos são disponibilizados, são implementados pelos controladores.
 *  
 * */
@RestController
@RequestMapping(value ="/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service ;
	
	@GetMapping
	public ResponseEntity< List< Category > > findAll() {
		
		List<Category> list = service.findAll() ;
		
		return ResponseEntity.ok().body(list) ;
	}

}
