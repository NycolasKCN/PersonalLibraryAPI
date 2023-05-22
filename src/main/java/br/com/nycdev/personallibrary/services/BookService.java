package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
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

  @Autowired
  private UserService userService;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private TokenService tokenService;

  public BookDto addBookToUser(String accessToken, BookForm bookForm) throws BookAlreadyExistsException, UserNotFoundException {
    Long idUser = tokenService.getUserIdInToken(accessToken);
    User user = userService.findUserById(idUser);

    Book book = new Book(bookForm);
    book.setOwner(user);

    bookRepository.save(book);

    return new BookDto(book);
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
