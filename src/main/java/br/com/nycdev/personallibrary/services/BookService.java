package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.BookNotFoundException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.forms.UserIdForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  private final TokenService tokenService;

  private final UserService userService;

  public BookService(BookRepository bookRepository, TokenService tokenService, UserService userService) {
    this.bookRepository = bookRepository;
    this.tokenService = tokenService;
    this.userService = userService;
  }

  public BookDto addBookToUser(String token, BookForm bookForm) throws BookAlreadyExistsException, UserNotFoundException, AuthorizationDeniedException {
    if (hasNoAuthorization(token, bookForm.userId())) {
      throw new AuthorizationDeniedException("User id: " + bookForm.userId() + " has no authorization.");
    }
    if (isBookExists(bookForm)) {
      throw new BookAlreadyExistsException("Book: " + bookForm.name() + " already register.");
    }

    User owner = userService.findUserById(bookForm.userId());
    Book book = new Book(bookForm);
    book.setOwner(owner);
    bookRepository.save(book);
    return new BookDto(book);
  }

  public List<BookDto> getAll(String token, UserIdForm userId) throws AuthorizationDeniedException {
    if (hasNoAuthorization(token, userId.userId())) {
      throw new AuthorizationDeniedException("User id: " + userId.userId() + "has no authorization.");
    }

    return bookRepository.findBooksByOwnerId(userId.userId()).stream().map(BookDto::new).toList();
  }

  public Book findBookById(String token, Long id) throws BookNotFoundException, AuthorizationDeniedException {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isEmpty()) {
      throw new BookNotFoundException("Book with id: " + id + " not found.");
    }
    Long ownerId = optionalBook.get().getOwner().getId();
    if (hasNoAuthorization(token, ownerId)) {
      throw new AuthorizationDeniedException("user with id: " + tokenService.getUserIdInToken(token) + " does not have authorization");
    }
    return optionalBook.get();
  }

  public BookDto removeBookById(String token, Long id) throws BookNotFoundException, AuthorizationDeniedException {
    Optional<Book> bookOptional = bookRepository.findById(id);
    if (bookOptional.isEmpty()) {
      throw new BookNotFoundException("Could not remove book with id: " + id);
    }
    Long ownerId = bookOptional.get().getOwner().getId();
    if (hasNoAuthorization(token, ownerId)) {
      throw new AuthorizationDeniedException("user with id: " + tokenService.getUserIdInToken(token) + " does not have authorization");
    }
    Book book = bookOptional.get();
    bookRepository.delete(book);
    bookRepository.flush();
    return new BookDto(book);
  }

  public List<Book> findBooksByName(String token, String name) {
    // TODO: 01/06/2023
    return null;
  }

  private boolean hasNoAuthorization(String token, Long userId) {
    return !tokenService.hasAuthorization(token, userId);
  }

  private boolean isBookExists(BookForm bookForm) {
    Optional<Book> aBook = bookRepository.findBookByName(bookForm.name());
    return aBook.map(book -> book.getOwner().getId().equals(bookForm.userId())).orElse(false);
  }


}
