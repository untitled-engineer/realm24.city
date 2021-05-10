package engineer.untitled.switter.service.repository;

import org.springframework.data.repository.CrudRepository;
import engineer.untitled.switter.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User getById(Long userId);
    User findByActivationCode(String code);
    User findByEmail(String email);
}
