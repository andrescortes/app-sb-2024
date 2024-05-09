package co.com.tkl.app.validation;

import co.com.tkl.app.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {


    private final UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !service.existsByUsername(username);
    }
}
