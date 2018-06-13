package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;


@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course getByNameAndAndStartDateAndAndEndDate(String name, Date t1, Date t2);

    Course getCourseById(Long id);
}
