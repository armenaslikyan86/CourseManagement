package am.mainserver.coursemanagement.service.impl;

import am.mainserver.coursemanagement.domain.Course;
import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.repository.UserRepository;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.EmailExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByEmail(final String email) {
        Assert.hasText(email, "email cannot be empty");

        final Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new IllegalStateException("User is not found"));
    }

    @Override
    public String getUserFullName(String email) {
        Assert.hasText(email, "email cannot be empty");
        return getByEmail(email).getFirstName() + " " + getByEmail(email).getLastName();
    }

    @Override
    public User register(final UserCreationRequestDto creationRequest) throws EmailExistException {
        if(emailExist(creationRequest.getEmail())) {
            throw new EmailExistException("Email already exists");
        }
        final User user = new User();
        user.setTitle(creationRequest.getTitle());
        user.setFirstName(creationRequest.getFirstName());
        user.setLastName(creationRequest.getLastName());
        user.setAge(creationRequest.getAge());
        user.setEmail(creationRequest.getEmail());
        user.setPhoneNumber(creationRequest.getPhoneNumber());
        user.setPasswordHash(BCrypt.hashpw(creationRequest.getPasswordHash(), BCrypt.gensalt(12)));
        user.setRoleType(creationRequest.getRoleType());
        return userRepository.save(user);
    }


    @SuppressWarnings("Duplicates")
    @Override
    public User update(Long userId, User updatedUser) {
        User user = userRepository.findById(userId);
        user.setTitle(updatedUser.getTitle());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setEmail(updatedUser.getEmail());
        user.setDescription(updatedUser.getDescription());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setPasswordHash(updatedUser.getPasswordHash());
        user.setRoleType(updatedUser.getRoleType());
        final Set<Course> courseSet = updatedUser.getCourses().stream()
                .map(course_element -> {
                    final Course course = new Course();
                    course.setId(course_element.getId());
                    course.setName(course_element.getName());
                    course.setDescription(course_element.getDescription());
                    course.setDuration(course_element.getDuration());
                    course.setPrice(course_element.getPrice());
                    course.setStartDate(course_element.getStartDate());
                    course.setEndDate(course_element.getEndDate());
                    course.setTutorName(course_element.getTutorName());
                    return course;
                }).collect(Collectors.toSet());
        user.setCourses(courseSet);
        return userRepository.save(user);
    }


    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Long getUserId(String email) {
        return userRepository.findByEmail(email).get().getId();
    }

    @Override
    public UserDto convertToUserDto(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setTitle(user.getTitle());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setDescription(user.getDescription());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRoleType(user.getRoleType());
        final Set<CourseDto> courseDtoList = user.getCourses().stream()
                .map(course -> {
                    final CourseDto courseDto = new CourseDto();
                    courseDto.setId(course.getId());
                    courseDto.setName(course.getName());
                    courseDto.setDescription(course.getDescription());
                    courseDto.setDuration(course.getDuration());
                    courseDto.setPrice(course.getPrice());
                    courseDto.setStartDate(course.getStartDate());
                    courseDto.setEndDate(course.getEndDate());
                    courseDto.setTutorName(course.getTutorName());
                    return courseDto;
                }).collect(Collectors.toSet());
        userDto.setCourses(courseDtoList);
        return userDto;
    }


    @SuppressWarnings("Duplicates")
    @Override
    public User convertToUser(UserDto userDto) {
        final User user = new User();
        user.setId(userDto.getId());
        user.setTitle(userDto.getTitle());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setDescription(userDto.getDescription());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRoleType(userDto.getRoleType());
        final Set<Course> courseList = userDto.getCourses().stream()
                .map(course -> {
                    final Course course1 = new Course();
                    course1.setId(course.getId());
                    course1.setName(course.getName());
                    course1.setDescription(course.getDescription());
                    course1.setDuration(course.getDuration());
                    course1.setPrice(course.getPrice());
                    course1.setStartDate(course.getStartDate());
                    course1.setEndDate(course.getEndDate());
                    course1.setTutorName(course.getTutorName());
                    return course1;
                }).collect(Collectors.toSet());
        user.setCourses(courseList);
        return user;
    }
}
