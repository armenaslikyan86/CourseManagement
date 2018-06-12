package am.mainserver.coursemanagement.service.exception;

public class CourseExistException extends Throwable {
    public CourseExistException(String course_already_exists) {
        throw new RuntimeException(course_already_exists);
    }
}
