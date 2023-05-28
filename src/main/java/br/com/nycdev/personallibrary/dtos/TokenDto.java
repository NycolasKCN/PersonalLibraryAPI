package br.com.nycdev.personallibrary.dtos;

public class TokenDto {
  private String accessToken;

  private String type = "Bearer";
  private String expireIn = "3600000";

  public TokenDto(String accessToken) {
    this.accessToken = accessToken;
  }
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

}
