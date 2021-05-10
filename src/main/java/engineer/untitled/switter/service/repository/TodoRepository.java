package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
