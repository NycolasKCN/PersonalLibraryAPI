package br.com.nycdev.personallibrary.exceptions;

public class AuthorizationDeniedException extends Exception {
  public AuthorizationDeniedException(String message) {
    super(message);
  }
}
