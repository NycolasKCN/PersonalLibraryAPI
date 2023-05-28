package br.com.nycdev.personallibrary.forms;

public class TokenForm {
  private String accessToken;

  public TokenForm(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}

