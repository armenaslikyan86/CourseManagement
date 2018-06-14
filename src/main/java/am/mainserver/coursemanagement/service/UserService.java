package am.mainserver.coursemanagement.service;

import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.service.exception.EmailExistException;

public interface UserService {

    User getByEmail(String email);

    User register(UserCreationRequestDto creationRequest) throws EmailExistException;

    User update(Long userId, User updatedUser);

    String getUserFullName(String email);

    Long getUserId(String email);

    UserDto convertToUserDto(User user);

    User convertToUser(UserDto userDto);

}
