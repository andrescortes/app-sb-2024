package co.com.tkl.app.validators;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface CommonValidator {
    ResponseEntity<?> validate(BindingResult result);
}
