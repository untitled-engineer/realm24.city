package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.TranslatableString;
import engineer.untitled.switter.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface TranslatableStringRepository extends CrudRepository<TranslatableString, String> {

}
