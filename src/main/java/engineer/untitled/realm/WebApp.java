package engineer.untitled.realm;

import engineer.untitled.realm.entity.OAuthProvider;
import engineer.untitled.realm.entity.OAuthUserCredentials;
import engineer.untitled.realm.entity.User;
import engineer.untitled.realm.service.UserService;
import engineer.untitled.realm.service.repository.OAuthUserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;


/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Controller
@EnableOpenApi
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class WebApp {

  @Autowired
  private UserService userService;

  @Autowired
  private OAuthUserCredentialsRepository oAuthUserCredentialsRepository;

  public static void main(String[] args) {
    SpringApplication.run(WebApp.class, args);
  }

  public String errorHandler() {
    return "error";
  }

  @GetMapping("swagger-ui")
  public String home() {
    return "redirect:/swagger-ui/index.html";
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

  private Consumer<Map<String, Object>>
  oauth2AuthorizedClient(OAuth2AuthorizedClient client) {
    return some -> {
      System.out.println(some);
      some.put("tmp", client.getPrincipalName());
    };
  }

  private Predicate<String> categoryPaths() {
    Pattern pattern_a = Pattern.compile(".*/category.*");
    Pattern pattern_b = Pattern.compile("..*/category");
    Pattern pattern_c = Pattern.compile(".*/categories");

    return pattern_a.asPredicate()
        .or(pattern_b.asPredicate())
        .or(pattern_c.asPredicate());
  }

  @Bean
  public Docket userApi() {
    AuthorizationScope[] authScopes = new AuthorizationScope[1];
    authScopes[0] = new AuthorizationScopeBuilder()
        .scope("read")
        .description("read access")
        .build();
    SecurityReference securityReference = SecurityReference.builder()
        .reference("test")
        .scopes(authScopes)
        .build();

    List<SecurityContext> securityContexts =
        Collections.singletonList(
            SecurityContext
                .builder()
                .securityReferences(Collections.singletonList(securityReference))
                .build()
        );

    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(Collections.singletonList(new BasicAuth("test")))
        .securityContexts(securityContexts)
        .groupName("user-api")
        .apiInfo(apiInfo())
        .select()
        .paths(input -> input.contains("user"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Springfox petstore API")
        .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum " +
            "has been the industry's standard dummy text ever since the 1500s, when an unknown printer "
            + "took a " +
            "galley of type and scrambled it to make a type specimen book. It has survived not only five " +
            "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
            "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum " +
            "passages, and more recently with desktop publishing software like Aldus PageMaker including " +
            "versions of Lorem Ipsum.")
        .termsOfServiceUrl("http://springfox.io")
        .contact(new Contact("springfox", "", ""))
        .license("Apache License Version 2.0")
        .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
        .version("2.0")
        .build();
  }

  @Bean
  public Docket categoryApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("category-api")
        .apiInfo(apiInfo())
        .select()
        .paths(categoryPaths())
        .build()
        .ignoredParameterTypes(ApiIgnore.class)
        .enableUrlTemplating(true);
  }
}