package engineer.untitled.switter.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "usrs")
@Data
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "user",
      targetEntity = OAuthUserCredentials.class
  )
  private List<OAuthUserCredentials> oAuthUserCredentials;

  @NotNull(message = "name can't be empty")
  private String username;

  @NotNull
  private String password;

  private String email;

  private String gender;

  private String locale;

  private LocalDateTime lastVisit;

  private Boolean isActive;

  @Nullable
  private String activationCode;

  @ElementCollection(
      targetClass = Role.class,
      fetch = FetchType.EAGER
  )
  @CollectionTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id")
  )
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

  @OneToMany(
      mappedBy = "author",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  private List<Ad> ads;

  @OneToMany(
      mappedBy = "author",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  private List<Message> messages;

  public User() {

  }

  public User(
      String username,
      String email,
      Optional<String> password
  ) {
    this.username = username;
    this.email = email;
    password.ifPresent(s -> this.password = s);
  }

  @PostLoad
  private void warmUpRelations() {
    this.roles.forEach(role -> {
      switch (role) {
        case ROLE_PUBLISHER:
                    /*
                    if (this.publisher == null) {
                        this.publisher = new Publisher();
                    }
                     */
          break;
        case ROLE_GUEST:
        case ROLE_USER:
        case AGENT:
        case AGENCY:
        case OWNER:
        case ROLE_ADMIN:
        default:
          break;
      }
    });
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(LocalDateTime lastVisit) {
    this.lastVisit = lastVisit;
  }

  public List<Ad> getAds() {
    return ads;
  }

  public void setAds(List<Ad> ads) {
    this.ads = ads;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  public boolean isAdmin() {
    return roles.contains(Role.ROLE_ADMIN);
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean isActive() {
    return isActive;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Nullable
  public String getActivationCode() {
    return activationCode;
  }

  public void setActivationCode(@Nullable String activationCode) {
    this.activationCode = activationCode;
  }

  @Override
  public String toString(){
    return this.getUsername();

  }
}
