package co.com.tkl.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String> {


    @Override
    public void initialize(IsExistsDb constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s);
//                && !service.existsBySku(s);
    }
}
