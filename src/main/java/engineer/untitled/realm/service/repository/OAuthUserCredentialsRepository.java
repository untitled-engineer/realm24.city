package engineer.untitled.realm.service.repository;

import engineer.untitled.realm.entity.OAuthUserCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public
interface OAuthUserCredentialsRepository
    extends CrudRepository<OAuthUserCredentials, Long>
{

  @Override
  Optional<OAuthUserCredentials> findById(Long id);

  Optional<OAuthUserCredentials>
  findByAccountIdAndProvider(String accountId, String value);
}
