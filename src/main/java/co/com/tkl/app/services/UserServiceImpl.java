package co.com.tkl.app.services;

import co.com.tkl.app.entities.Role;
import co.com.tkl.app.entities.User;
import co.com.tkl.app.repositories.RoleRepository;
import co.com.tkl.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static co.com.tkl.app.utils.AppConstants.ROLE_ADMIN;
import static co.com.tkl.app.utils.AppConstants.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(ROLE_USER).ifPresent(roles::add);
        if (user.isAdmin()) {
            roleRepository.findByName(ROLE_ADMIN).ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        log.info("saved: {}", saved);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
