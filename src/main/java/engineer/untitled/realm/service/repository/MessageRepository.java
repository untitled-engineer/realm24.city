package engineer.untitled.realm.service.repository;

import engineer.untitled.realm.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
