package co.com.tkl.app.validation;

import co.com.tkl.app.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String> {

    private final ProductService service;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(service)) {
            return true;
        }
        return StringUtils.hasText(s) && !service.existsBySku(s);
    }
}
