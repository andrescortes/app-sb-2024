package co.com.tkl.app.services;

import co.com.tkl.app.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);
    Optional<Product> update(Long id, Product product);
    boolean existsBySku(String sku);


    Optional<Product> delete(Long id);
}
