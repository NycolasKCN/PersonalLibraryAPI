package br.com.nycdev.personallibrary.exceptions;

public class UserLoginAlreadyExistsException extends Exception {
  public UserLoginAlreadyExistsException(String message) {
    super(message);
  }

  public UserLoginAlreadyExistsException() {
  }
}
