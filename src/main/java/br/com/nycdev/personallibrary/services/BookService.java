package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.BookNotFoundException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
    if (!hasAuthorization(token, bookForm)) {
      throw new AuthorizationDeniedException("User id: " + bookForm.getUserId() + " has no authorization.");
    }
    if (isBookExists(bookForm)) {
     throw new BookAlreadyExistsException("Book: " + bookForm.getName() + " already register.");
    }

    User owner = userService.findUserById(bookForm.getUserId());
    Book book = new Book(bookForm);
    book.setOwner(owner);
    bookRepository.save(book);
    return new BookDto(book);
  }

  private boolean hasAuthorization(String token, BookForm bookForm) {
    return tokenService.hasAuthorization(token, bookForm.getUserId());
  }

  private boolean isBookExists(BookForm bookForm) {
    Optional<Book> aBook = bookRepository.findBookByName(bookForm.getName());
    return aBook.map(book -> book.getOwner().getId().equals(bookForm.getUserId())).orElse(false);
  }

  public List<BookDto> getAll() {
    return bookRepository.findAll().stream().map(BookDto::new).toList();
  }

  public List<BookDto> findBooksByUser(User user) {
    return bookRepository.findBooksByOwnerIs(user).stream().map(BookDto::new).toList();
  }

  public BookDto removeBookById(Long userId, Long id) throws BookNotFoundException {
    Optional<Book> bookOptional = bookRepository.findById(id);
    if (bookOptional.isEmpty()) {
      throw new BookNotFoundException("Could not remove book with id: " + id);
    }
    Book book = bookOptional.get();
    if (!book.getOwner().getId().equals(userId)) {
      throw new AccessDeniedException("Could not remove book with id: " + id + " because user with id: " + userId + " is not owner");
    }
    bookRepository.delete(book);
    bookRepository.flush();
    return new BookDto(book);
  }
}
