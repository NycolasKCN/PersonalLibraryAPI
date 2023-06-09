package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.UserForm;
import br.com.nycdev.personallibrary.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserDto> registerUser(@RequestBody UserForm userForm) {
    try {
      return new ResponseEntity<>(userService.registerUser(userForm), HttpStatus.CREATED);
    } catch (UserLoginAlreadyExistsException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @RequestMapping("/all")
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.FOUND);
  }

  @RequestMapping("/{id}")
  @DeleteMapping
  public ResponseEntity<UserDto> deleteUserById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
    try {
      UserDto userDto = userService.deleteUserById(token, id);
      return new ResponseEntity<>(userDto, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (AuthorizationDeniedException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
  }
}
