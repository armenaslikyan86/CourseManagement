package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
