package engineer.untitled.switter.endpoint;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.connect.Endpoint;
import engineer.untitled.switter.UserProfile;
import engineer.untitled.switter.entity.OAuthProvider;
import engineer.untitled.switter.entity.OAuthUserCredentials;
import engineer.untitled.switter.entity.Role;
import engineer.untitled.switter.entity.User;
import engineer.untitled.switter.service.UserNotFoundException;
import engineer.untitled.switter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Endpoint
@AnonymousAllowed
public class UserInfoEndpoint {

  @Autowired
  private UserService userService;

  public UserProfile
  getUserInfo() {
    /* OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication; */
    Authentication
        authentication = SecurityContextHolder.getContext().getAuthentication();
    final List<String> authorities = authentication
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    Object principal = authentication.getPrincipal();
    if (authentication instanceof OAuth2AuthenticationToken
        && principal instanceof DefaultOAuth2User
    ) {
      User user = new User(); // todo anonym
      String providerName = ((OAuth2AuthenticationToken) authentication)
          .getAuthorizedClientRegistrationId()
          .toUpperCase(Locale.ROOT);
      DefaultOAuth2User oAuth2User = ((DefaultOAuth2User) principal);
      AtomicReference<OAuthProvider> oAuthProvider = new AtomicReference<>();
      Arrays.stream(OAuthProvider.values()).forEach(oAuthProvider1 -> {
        if (oAuthProvider1.name().equals(providerName)) {
          oAuthProvider.set(oAuthProvider1);
        }
      });
      try {
        switch (oAuthProvider.get()) {
          case FACEBOOK:
            user = ((User) userService.loadUserByOAuthCredential(
                oAuth2User.getAttribute("id"),
                providerName.toUpperCase(Locale.ROOT))
            );
            break;
          case GOOGLE:
            user = ((User) userService.loadUserByOAuthCredential(
                oAuth2User.getAttribute("sub"),
                providerName.toUpperCase(Locale.ROOT))
            );
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + providerName);
        }
      } catch (UserNotFoundException e) { // todo logs or some
        return new UserProfile( "tmp", "tmp", authorities );
      }

      if (user != null) {
        AtomicReference<OAuthUserCredentials>
            oAuthUserCredentialsAtomicReference = new AtomicReference<>();
        // todo how to functional way or simplify
        user.getOAuthUserCredentials().forEach(oAuthUserCredentials1 -> {
              if (oAuthUserCredentials1.getProvider().equals(oAuthProvider.get().name())) {
                oAuthUserCredentialsAtomicReference.set(oAuthUserCredentials1);
              }
            }
        );
        if (oAuthUserCredentialsAtomicReference.get().getId() != null) {

          return new UserProfile(
              user.getUsername(),
              oAuthUserCredentialsAtomicReference.get().getPicture(),
              authorities
          );
        }
      }
    }
    //if (authentication instanceof AnonymousAuthenticationToken) {
    return new UserProfile( "", "", authorities );
  }

  public static class UserInfoData {
    public Long id;
    public String firstName;
    public String lastName;
    public Set<Role> roles;
    //public User user;
  }
}
