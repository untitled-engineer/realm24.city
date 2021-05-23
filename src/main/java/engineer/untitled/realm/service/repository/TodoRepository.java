package engineer.untitled.realm.service.repository;

import engineer.untitled.realm.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
