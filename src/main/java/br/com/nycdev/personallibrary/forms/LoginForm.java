package br.com.nycdev.personallibrary.forms;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
  private String login;
  private String password;

  public LoginForm(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public UsernamePasswordAuthenticationToken convert() {
    return new UsernamePasswordAuthenticationToken(login, password);
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
}
