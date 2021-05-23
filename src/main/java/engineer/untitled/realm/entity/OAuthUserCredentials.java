package engineer.untitled.realm.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class OAuthUserCredentials {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.REMOVE,
      targetEntity = User.class
  )
  private User user;

  @NotBlank(message = "AccountId can't be empty")
  private String accountId;

  @NotBlank(message = "Provider can't be empty")
  /*
  @ElementCollection(
      targetClass = Role.class,
      fetch = FetchType.EAGER
  )
  @CollectionTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id")
  )
   */
  //@Enumerated(EnumType.STRING)
  private String provider;

  @NotNull
  private String picture;

  public OAuthUserCredentials() {

  }

  public OAuthUserCredentials(
      String provider,
      String accountId,
      String picture,
      User user
  ) {
    this.user = user;
    this.accountId = accountId;
    this.provider = provider;
    this.picture = picture;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }
}
