package am.mainserver.coursemanagement.service;

import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.service.impl.EmailExistException;

public interface UserService {

    User getByEmail(String email);

    User register(UserCreationRequestDto creationRequest) throws EmailExistException;
}
