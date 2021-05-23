package engineer.untitled.realm;

import engineer.untitled.realm.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class UserSession implements Serializable {

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
    System.out.println(principal.toString());
    String name = principal.getAttribute("given_name") + " " + principal.getAttribute("family_name");

    return new User( );
  }

  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null;
  }
}
