package co.com.tkl.app.controllers;

import co.com.tkl.app.entities.User;
import co.com.tkl.app.services.UserService;
import co.com.tkl.app.validators.CommonValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final CommonValidator validator;

    @GetMapping
    public ResponseEntity<List<User>> users() {
        return ResponseEntity
                .ok(service.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validator.validate(result);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validator.validate(result);
        }
        user.setAdmin(false);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(user));
    }
}
