package am.mainserver.coursemanagement.service.impl;

import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.repository.UserRepository;
import am.mainserver.coursemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Optional;

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

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

}
