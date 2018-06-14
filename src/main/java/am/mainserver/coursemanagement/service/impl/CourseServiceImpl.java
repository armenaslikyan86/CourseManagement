package am.mainserver.coursemanagement.service.impl;

import am.mainserver.coursemanagement.domain.Course;
import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.repository.CourseRepository;
import am.mainserver.coursemanagement.service.CourseService;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.CourseExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createCourse(final CourseDto courseDto) throws CourseExistException {

        if (getByNameAndStartDateAndEndDate(courseDto.getName(), courseDto.getStartDate(), courseDto.getEndDate()) != null) {
            throw new CourseExistException("Course already exists");
        }
        final Course course = new Course();
        course.setName(courseDto.getName());
        course.setDuration(courseDto.getDuration());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());

        Set<User> userSet = new HashSet<>();

        for (UserDto userDto : courseDto.getUsers()) {
            userSet.add(userService.convertToUser(userDto));
        }

        course.setUsers(userSet);

        courseRepository.save(course);

    }

    @Override
    public Course getByNameAndStartDateAndEndDate(String name, Date t1, Date t2) {
        return courseRepository.getByNameAndAndStartDateAndAndEndDate(name, t1, t2);
    }

    @Override
    public Course convertToCourse(CourseDto courseDto) {
        final Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setPrice(courseDto.getPrice());
        course.setStartDate(courseDto.getStartDate());
        course.setEndDate(courseDto.getEndDate());
        course.setTutorName(courseDto.getTutorName());
        final Set<User> userSet = courseDto.getUsers().stream()
                .map(user -> {
                    final User user1 = new User();
                    user1.setId(user.getId());
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setDescription(user.getDescription());
                    user1.setTitle(user.getTitle());
                    user1.setAge(user.getAge());
                    user1.setEmail(user.getEmail());
                    user1.setRoleType(user.getRoleType());
                    user1.setPasswordHash(user.getPasswordHash());
                    user1.setPhoneNumber(user.getPhoneNumber());
                    return user1;
                }).collect(Collectors.toSet());
        course.setUsers(userSet);
        return course;
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courses.add(course);
        }
        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.getCourseById(id);
    }

    @Override
    public Course update(Course updatedCourse, Long courseId) {
        Course course = courseRepository.getCourseById(courseId);
        course.setName(updatedCourse.getName());
        course.setDuration(updatedCourse.getDuration());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());
        course.setStartDate(updatedCourse.getStartDate());
        course.setEndDate(updatedCourse.getEndDate());
        course.setTutorName(updatedCourse.getTutorName());
        final Set<User> userSet = updatedCourse.getUsers().stream()
                .map(user -> {
                    final User user_element = new User();
                    user_element.setTitle(user.getTitle());
                    user_element.setFirstName(user.getFirstName());
                    user_element.setLastName(user.getLastName());
                    user_element.setAge(user.getAge());
                    user_element.setEmail(user.getEmail());
                    user_element.setPhoneNumber(user.getPhoneNumber());
                    user_element.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(12)));
                    user_element.setRoleType(user.getRoleType());
                    return user_element;
                }).collect(Collectors.toSet());
        course.setUsers(userSet);
        return courseRepository.save(course);
    }
}
