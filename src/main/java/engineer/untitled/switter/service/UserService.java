package engineer.untitled.switter.service;

import engineer.untitled.switter.entity.OAuthUserCredentials;
import engineer.untitled.switter.entity.Role;
import engineer.untitled.switter.entity.User;
import engineer.untitled.switter.service.repository.OAuthUserCredentialsRepository;
import engineer.untitled.switter.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Configuration
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OAuthUserCredentialsRepository oAuthUserCredentialsRepository;

  @Autowired
  private MailSender mailSender;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${hostname}")
  private String hostname;


  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UserNotFoundException("User not found");
    }

    return user;
  }

  public User addUser(User user) {
    User userFromDb = userRepository.findByEmail(user.getEmail());

    assert userFromDb != null;

    userFromDb.setUsername(user.getUsername());
    userFromDb.setPassword(UUID.randomUUID().toString());

    userFromDb.setRoles(Collections.singleton(Role.ROLE_PUBLISHER));
    userFromDb.setActivationCode(UUID.randomUUID().toString());

    userRepository.save(userFromDb);

    return userFromDb;
    // userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
    // sendMessage(user);
  }

  private void sendMessage(User user) {
    if (!StringUtils.isEmpty(user.getEmail())) {
      String message = String.format(
          "Hello, %s! \n" +
              "Welcome to Switter. \n" +
              "Please visit https://%s/activation/%s",
          user.getUsername(),
          hostname,
          user.getActivationCode());
      mailSender.send(user.getEmail(), "Authentication code", message);
    }
  }

  public boolean activateUser(String code) {
    User user = userRepository.findByActivationCode(code);
    if (user == null) {
      return false;
    }
    user.setActivationCode(null);
    userRepository.save(user);

    return true;
  }

  public List<User> findAll() {
    return (List<User>) userRepository.findAll();
  }

  public User getById(Long userId) {
    return userRepository.getById(userId);
  }

  public void saveUser(User user, String username, Map<String, String> form) {
    user.setUsername(username);
    Set<String> roles = Arrays.stream(Role.values())
        .map(Role::name)
        .collect(Collectors.toSet());
    user.getRoles().clear();
    for (String key : form.keySet()) {
      if (roles.contains(key)) {
        user.getRoles().add(Role.valueOf(key));
      }
    }
    userRepository.save(user);
  }

  public void updateProfile(User user, String newPassword, String newEmail) {
    String email = user.getEmail();

    boolean isEmailChanged = (newEmail != null && !newEmail.equals(email)) ||
        (email != null && !email.equals(newEmail));

    if (isEmailChanged) {
      user.setEmail(newEmail);

      if (!StringUtils.isEmpty(email)) {
        user.setActivationCode(UUID.randomUUID().toString());
      }

      sendMessage(user);
    }

    if (!StringUtils.isEmpty(newPassword)) {
      user.setPassword(newPassword);
    }

    userRepository.save(user);

  }

  public UserDetails
  loadUserByOAuthCredential(String id, String provider)
  throws NoSuchElementException
  {
    Optional<OAuthUserCredentials> oAuthUserCredentials =
        oAuthUserCredentialsRepository
            .findByAccountIdAndProvider(id, provider.toUpperCase(Locale.ROOT));

    if (oAuthUserCredentials.isPresent()) {

      OAuthUserCredentials entity = oAuthUserCredentials.get();

      Optional<User> user = userRepository
          .findById(entity.getUser().getId());

      if (!user.isPresent()) {
        throw new UserNotFoundException("User not found");
      }

      return user.get();
    }

    return new User();
  }
}
