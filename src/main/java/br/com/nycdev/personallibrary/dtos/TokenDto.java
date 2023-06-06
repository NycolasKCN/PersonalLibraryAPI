package br.com.nycdev.personallibrary.dtos;

import br.com.nycdev.personallibrary.models.User;

public class TokenDto {
  private Long id;
  private String name;
  private String token;

  public TokenDto(String accessToken) {
    this.token = accessToken;
  }

  public TokenDto(User user, String token) {
    this.id = user.getId();
    this.name = user.getName();
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
