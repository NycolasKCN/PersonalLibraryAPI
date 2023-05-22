package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.BookNotFoundExecption;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.UserForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private BookService bookService;

  public UserDto registerUser(UserForm userForm) throws UserLoginAlreadyExistsException {
    Optional<User> userOptional = userRepository.findByLogin(userForm.login());
    if (userOptional.isPresent()) {
      throw new UserLoginAlreadyExistsException("User with login: " +userForm.login()+ " already exists.");
    }
    User user = new User(userForm);
    userRepository.save(user);
    return new UserDto(user);
  }

  public List<UserDto> getAll() {
    List<UserDto> all = new ArrayList<>();

    userRepository.findAll().forEach(user -> {
      all.add(new UserDto(user));
    });
    return all;
  }

  public User findUserById(Long id) throws UserNotFoundException{
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      return userOptional.get();
    }
    throw new UserNotFoundException("User with id: " + id + " does not exist.");
  }

  public List<BookDto> findUserBooks(String token) throws UserNotFoundException {
    Long userId = tokenService.getUserIdInToken(token);
    Optional<User> userOptional = userRepository.findById(userId);

    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User with id: " + userId + " not founded.");
    }
    return bookService.findBooksByUser(userOptional.get());
  }

  public BookDto removeBook(String accessToken, Long id) throws BookNotFoundExecption {
    Long userId = tokenService.getUserIdInToken(accessToken);
    return bookService.removeBookById(userId, id);
  }
}
