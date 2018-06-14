package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findById(Long userId);
}
