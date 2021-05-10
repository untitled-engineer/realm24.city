package engineer.untitled.switter;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import engineer.untitled.switter.entity.OAuthProvider;
import engineer.untitled.switter.entity.OAuthUserCredentials;
import engineer.untitled.switter.entity.User;
import engineer.untitled.switter.service.UserService;
import engineer.untitled.switter.service.repository.OAuthUserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.vaadin.artur.helpers.LaunchUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Theme("realm")
@PWA(
    name = "Vaadin Todo",
    shortName = "Vaadin Todo",
    offlineResources = {"images/logo.png"}
)
@Controller
public class WebApp
    extends SpringBootServletInitializer
    implements AppShellConfigurator {

  @Autowired
  private UserService userService;

  @Autowired
  private OAuthUserCredentialsRepository oAuthUserCredentialsRepository;


  public static void main(String[] args) {
    LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(WebApp.class, args));
  }

  public String errorHandler() {
    return "error";
  }

  @Bean
  public WebClient
  rest(
      ClientRegistrationRepository clients,
      OAuth2AuthorizedClientRepository auth
  ) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, auth);

    return WebClient.builder()
        .filter(oauth2).build();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User>
  oauth2UserService(WebClient rest) {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    return request -> {

      OAuth2User user = delegate.loadUser(request);

      String providerName = request
          .getClientRegistration()
          .getRegistrationId()
          .toUpperCase(Locale.ROOT);

      User userDetail = new User();

      Optional<OAuthUserCredentials> oAuthUserCredentials =
          Optional.of(new OAuthUserCredentials());

      String pictureUrl = "";

      AtomicReference<OAuthProvider> oAuthProvider = new AtomicReference<>();

      Arrays.stream(OAuthProvider.values()).forEach(oAuthProvider1 -> {
            if (oAuthProvider1.name().equals(providerName)) {
              oAuthProvider.set(oAuthProvider1);
            }
          });

      switch (oAuthProvider.get()) {
        /* case "github":
     user.getAttribute("id").toString(),
     user.getAttribute("login"),
     user.getAttribute("avatar_url") */
        case FACEBOOK:
          oAuthUserCredentials = oAuthUserCredentialsRepository
              .findByAccountIdAndProvider(
                  user.getAttribute("id"),
                  OAuthProvider.FACEBOOK.name()
              );

          class PictureData {
            String url;
            String height;
          }

          class Picture {
            HashMap<String, PictureData> data;
          }

          HashMap<String, Object> picture = user.getAttribute("picture");


          if (picture != null) {
            LinkedHashMap<String, Object> pictureData =
                ((LinkedHashMap<String, Object>) picture.get("data"));
            pictureUrl = pictureData.get("url").toString();
          }

          userDetail = new User(
              user.getAttribute("name"),
              user.getAttribute("email"),
              Optional.empty()
          );

          break;
        case GOOGLE:

          pictureUrl = user.getAttribute("picture");
          oAuthUserCredentials = oAuthUserCredentialsRepository
              .findByAccountIdAndProvider(
                  user.getAttribute("sub"),
                  OAuthProvider.GOOGLE.name()
              );

          userDetail = new User(
              user.getAttribute("name"),
              user.getAttribute("email"),
              Optional.empty()
          );

          break;
      }

      if (!oAuthUserCredentials.isPresent()) {

        userDetail.setUsername(UUID.randomUUID().toString());
        userDetail.setPassword(UUID.randomUUID().toString());

        User newUser = this.userService.addUser(userDetail);

        OAuthUserCredentials oAuthUserCredentialsNew = new OAuthUserCredentials();
        oAuthUserCredentialsNew.setUser(newUser);
        oAuthUserCredentialsNew.setPicture(pictureUrl);

        switch (oAuthProvider.get()) {
          case FACEBOOK:
            oAuthUserCredentialsNew.setAccountId(user.getAttribute("id"));
            oAuthUserCredentialsNew.setProvider(OAuthProvider.FACEBOOK.name());
            break;
          case GOOGLE:
            oAuthUserCredentialsNew.setAccountId(user.getAttribute("sub"));
            oAuthUserCredentialsNew.setProvider(OAuthProvider.GOOGLE.name());
            break;
        }

        this.oAuthUserCredentialsRepository.save(oAuthUserCredentialsNew);
      } else {
        return user;
      }


      /*
      OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
          request.getClientRegistration(),
          user.getName(),
          request.getAccessToken()
      );

      String url = user.getAttribute("organizations_url");
      List<Map<String, Object>> orgs = rest
          .get().uri(url)
          .attributes(oauth2AuthorizedClient(client))
          .retrieve()
          .bodyToMono(List.class)
          .block();

      if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
        return user;
      }
     */

      throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Spring Team", ""));
    };
  }

  private Consumer<Map<String, Object>> oauth2AuthorizedClient(OAuth2AuthorizedClient client) {
    return some -> {
      System.out.println(some);
      some.put("tmp", client.getPrincipalName());
    };
  }
}