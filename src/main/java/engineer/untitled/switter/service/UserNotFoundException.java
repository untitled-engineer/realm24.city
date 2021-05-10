package engineer.untitled.switter.service;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException
    extends AuthenticationException {

  /**
   * Constructs a <code>UsernameNotFoundException</code> with the specified message.
   * @param msg the detail message.
   */
  public UserNotFoundException(String msg) {
    super(msg);
  }

  /**
   * Constructs a {@code UsernameNotFoundException} with the specified message and root
   * cause.
   * @param msg the detail message.
   * @param cause root cause
   */
  public UserNotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
