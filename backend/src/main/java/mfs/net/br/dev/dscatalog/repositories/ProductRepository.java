package mfs.net.br.dev.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mfs.net.br.dev.dscatalog.entities.Category;
import mfs.net.br.dev.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	/**
	 *  Filtro por categorias ou por name do product
	 * @param categories
	 * @param name
	 * @param pageable
	 * @return
	 */
	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
			+ "( COALESCE(:categories) IS NULL OR cats IN :categories ) AND "
			+ "(:name = '' OR LOWER( obj.name ) LIKE LOWER( CONCAT( '%',:name,'%'))) ")
	Page<Product> find( List<Category> categories, String name, Pageable pageable) ;

}
