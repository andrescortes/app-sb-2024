package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("select (count(p) > 0) from Product p where p.sku = ?1")
    boolean existsBySku(String sku);
}
