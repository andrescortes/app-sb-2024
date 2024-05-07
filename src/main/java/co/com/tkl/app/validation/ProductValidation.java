package co.com.tkl.app.validation;

import co.com.tkl.app.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class ProductValidation implements Validator {
    /**
     * @return true if isAssignable else false
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name", "NotBlank");
        if (Objects.isNull(product.getName()) || product.getName().isBlank()) {
            errors.rejectValue("name",null, "Can't be null or empty");
        }
        if (Objects.isNull(product.getDescription()) || product.getDescription().isBlank()) {
            errors.rejectValue("description", null,"Can't be null or empty");
        }
        if (Objects.isNull(product.getPrice())) {
            errors.rejectValue("price", null,"Can't be null");
        } else if (product.getPrice() < 500) {
            errors.rejectValue("price", null,"Can't be less than 500");
        }
    }
}
