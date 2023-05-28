package br.com.nycdev.personallibrary.forms;

public record UserForm (String name, String login, String password){
  @Override
  public String toString() {
    return "UserForm{" +
            "name='" + name + '\'' +
            ", login='" + login + '\'' +
            '}';
  }
}
