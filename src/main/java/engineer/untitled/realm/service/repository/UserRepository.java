package engineer.untitled.realm.service.repository;

import org.springframework.data.repository.CrudRepository;
import engineer.untitled.realm.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User getById(Long userId);
    User findByActivationCode(String code);
    User findByEmail(String email);
}
