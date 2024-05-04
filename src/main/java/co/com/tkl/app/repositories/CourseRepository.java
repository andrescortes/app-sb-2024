package co.com.tkl.app.repositories;

import co.com.tkl.app.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c left join fetch c.students where c.id =?1")
    Optional<Course> findOne(Long id);
}