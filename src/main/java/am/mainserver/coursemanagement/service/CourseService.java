package am.mainserver.coursemanagement.service;


import am.mainserver.coursemanagement.domain.Course;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.service.exception.CourseExistException;

import java.sql.Timestamp;
import java.util.List;

public interface CourseService {

    void createCourse(CourseDto courseDto) throws CourseExistException;

    Course getByNameAndStartDateAndEndDate(String name, Timestamp t1, Timestamp t2);

    Course convertToCourse(CourseDto courseDto);

    List<Course> getCourses();
}
