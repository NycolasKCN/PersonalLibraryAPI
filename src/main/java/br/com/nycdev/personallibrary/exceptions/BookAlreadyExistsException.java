package br.com.nycdev.personallibrary.exceptions;

public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String msg) {
        super(msg);
    }
}
