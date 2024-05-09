package co.com.tkl.app.controllers;

import co.com.tkl.app.entities.Product;
import co.com.tkl.app.services.ProductService;
import co.com.tkl.app.validators.CommonValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final CommonValidator validator;

    @GetMapping("/all")
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable(name = "id") Long id) {
        Optional<Product> productOptional = service.findById(id);
        return productOptional
                .map(product -> ResponseEntity.status(HttpStatus.OK).body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validator.validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product, BindingResult result, @PathVariable(name = "id") Long id) {
        if (result.hasFieldErrors()) {
            return validator.validate(result);
        }
        return service.update(id, product)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable(name = "id") Long id) {
        return service.delete(id)
                .map(ResponseEntity.status(HttpStatus.OK)::body)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
