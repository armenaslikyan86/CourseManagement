package am.mainserver.coursemanagement.service.impl;

public class EmailExistException extends Throwable {
    public EmailExistException(String email_already_exists) {
        System.out.printf(email_already_exists);
    }
}
