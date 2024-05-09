package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);
}
