package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.BookNotFoundException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.forms.QueryForm;
import br.com.nycdev.personallibrary.forms.UserIdForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/book")
public class BookController {

  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<BookDto> addBook(@RequestHeader("Authorization") String token, @RequestBody BookForm bookForm) {
    try {
      return new ResponseEntity<>(service.addBookToUser(token, bookForm), HttpStatus.CREATED);
    } catch (BookAlreadyExistsException | UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (AuthorizationDeniedException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
  }

  @RequestMapping("/all")
  @GetMapping
  public ResponseEntity<List<BookDto>> findAllBooksFromUser(@RequestHeader("Authorization") String token, @RequestBody UserIdForm userIdForm) {
    try {
      return new ResponseEntity<>(service.getAll(token, userIdForm), HttpStatus.OK);
    } catch (AuthorizationDeniedException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
  }

  @RequestMapping("/{id}")
  @GetMapping
  public ResponseEntity<Book> findBookById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
    try {
      return new ResponseEntity<>(service.findBookById(token, id), HttpStatus.OK);
    } catch (AuthorizationDeniedException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @RequestMapping("/delete/{id}")
  @DeleteMapping
  public ResponseEntity<BookDto> deleteBookById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
    try {
      return new ResponseEntity<>(service.removeBookById(token, id), HttpStatus.OK);
    } catch (AuthorizationDeniedException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @RequestMapping("/search")
  @GetMapping
  public ResponseEntity<List<BookDto>> findBooks(@RequestHeader("Authorization") String token, @RequestBody QueryForm form) {
    try {
      return new ResponseEntity<>(service.findBooks(token, form), HttpStatus.OK);
    } catch (UserNotFoundException | IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
