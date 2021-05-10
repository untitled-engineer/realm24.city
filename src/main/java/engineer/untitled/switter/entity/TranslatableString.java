package engineer.untitled.switter.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "translatable_strings")
@Data
public class TranslatableString {

  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @NotNull
  private String english;

  @NotNull
  private String russian;

  @NotNull
  private String ukrainian;

  public TranslatableString(){

  }

  public void setId(String  id) {
    this.id = id;
  }

  public String  getId() {
    return id;
  }

  public String getEnglish() {
    return english;
  }

  public void setEnglish(String english) {
    this.english = english;
  }

  public String getRussian() {
    return russian;
  }

  public void setRussian(String russian) {
    this.russian = russian;
  }

  public String getUkrainian() {
    return ukrainian;
  }

  public void setUkrainian(String ukraine) {
    this.ukrainian = ukraine;
  }
}
