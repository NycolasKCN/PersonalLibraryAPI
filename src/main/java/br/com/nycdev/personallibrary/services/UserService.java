package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.UserForm;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final TokenService tokenService;

  public UserService(UserRepository userRepository, TokenService tokenService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }

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
    return userRepository.findAll().stream().map(UserDto::new).toList();
  }

  public User findUserById(Long id) throws UserNotFoundException{
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      return userOptional.get();
    }
    throw new UserNotFoundException("User with id: " + id + " does not exist.");
  }

  public UserDto deleteUserById(String token, Long id) throws UserNotFoundException, AuthorizationDeniedException{
    Long authUserId = tokenService.getUserIdInToken(tokenService.recoverTokenFromHeader(token));
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User with id: " + id + " does not exist.");
    }
    if (!authUserId.equals(id)) {
      throw new AuthorizationDeniedException("User has no permission.");
    }

    UserDto userDto = new UserDto(userOptional.get());
    userRepository.delete(userOptional.get());

    return userDto;
  }
}
