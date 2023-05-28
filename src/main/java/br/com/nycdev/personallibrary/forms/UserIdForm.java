package br.com.nycdev.personallibrary.forms;

public class UserIdForm {
  private Long userId;

  public UserIdForm(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
