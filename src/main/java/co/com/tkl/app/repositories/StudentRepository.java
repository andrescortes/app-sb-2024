package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("select s from Student s left join fetch s.courses where s.id = ?1")
    Optional<Student> findOne(Long id);
}
