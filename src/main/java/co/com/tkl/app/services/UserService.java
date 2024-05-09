package co.com.tkl.app.services;

import co.com.tkl.app.entities.User;

import java.util.Collections;
import java.util.List;

public interface UserService {

    default List<User> findAll() {
        return Collections.EMPTY_LIST;
    }

    User save(User user);

    boolean existsByUsername(String username);
}
