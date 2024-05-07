package co.com.tkl.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class RequiredValidation implements ConstraintValidator<IsRequired, String> {
    /**
     * @return true if is not-null and not-empty and not-isBlank
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
//        return Objects.nonNull(value) && !value.isEmpty() && !value.isBlank();
        return StringUtils.hasText(value);
    }
}
