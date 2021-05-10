package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.OAuthUserCredentials;
import engineer.untitled.switter.entity.User;
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
