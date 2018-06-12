package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course getByNameAndAndStartDateAndAndEndDate(String name, Timestamp t1, Timestamp t2);
}
