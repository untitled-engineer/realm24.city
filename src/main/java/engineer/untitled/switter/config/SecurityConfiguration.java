package engineer.untitled.switter.config;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import engineer.untitled.switter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
    extends VaadinWebSecurityConfigurerAdapter {

  private static Object ServletHelper;

  /* @Autowired private DataSource dataSource; */

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public SecurityConfiguration() {
    super();
  }

  @Override
  protected void
  configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth
        .userDetailsService(userService)
        .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void
  configure(HttpSecurity httpSecurity)
      throws Exception {
    httpSecurity
        .logout(l -> l.logoutSuccessUrl("/").permitAll())
        .authorizeRequests(a -> a
                .antMatchers("/**", "/webjars/**")
                .permitAll()
                // carefully .anyRequest().authenticated().and()
        )
        .exceptionHandling(e -> e.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .oauth2Login().permitAll();
    super.configure(httpSecurity);
    // setLoginView(httpSecurity, "/login", "/");
  }

  /**
   * Tests if the request is an internal framework request. The test consists of
   * checking if the request parameter is present and if its value is consistent
   * with any of the request types know.
   *
   * @param {@link HttpServletRequest}
   * @return true if is an internal framework request. False otherwise.
   * static boolean isFrameworkInternalRequest(HttpServletRequest request) {
   * final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
   * return parameterValue != null && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
   * }
   */

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
    web.ignoring().antMatchers("/images/**");
  }

    /*
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient rest) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User user = delegate.loadUser(request);
            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
                return user;
            }

            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient
                    (request.getClientRegistration(), user.getName(), request.getAccessToken());
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

            throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_token", "Not in Spring Team", ""));
        };
    }
     */

    /*
    private Consumer<Map<String, Object>> oauth2AuthorizedClient(OAuth2AuthorizedClient client) {

        Consumer<Map<String, Object>> consumer;

        consumer = new Consumer<Map<String, Object>>();

        return
    }

    @Bean
    public Principal principalExtractor(UserRepository userRepository){
        return () -> {

            return "tmp";
        };
    }
     */
}

