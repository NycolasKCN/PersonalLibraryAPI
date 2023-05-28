package br.com.nycdev.personallibrary.forms;

import java.util.List;

public class UserForm {
  private String name;
  private String login;
  private String password;

  public UserForm(String name, String login, String password) {
    this.name = name;
    this.login = login;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserForm{" +
            "name='" + name + '\'' +
            ", login='" + login + '\'' +
            '}';
  }
}
