package co.com.tkl.app.validation;

import co.com.tkl.app.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {


    private final UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(service)) {
            return true;
        }
        return !service.existsByUsername(username);
    }
}
