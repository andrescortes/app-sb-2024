package co.com.tkl.app.validation;

import co.com.tkl.app.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String> {


    @Override
    public void initialize(IsExistsDb constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private final ProductService service;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !service.existsBySku(s);
    }
}
