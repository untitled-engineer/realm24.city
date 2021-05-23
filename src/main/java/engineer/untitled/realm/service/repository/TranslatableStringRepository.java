package engineer.untitled.realm.service.repository;

import engineer.untitled.realm.entity.TranslatableString;
import org.springframework.data.repository.CrudRepository;

public interface TranslatableStringRepository extends CrudRepository<TranslatableString, String> {

}
