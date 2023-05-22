package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.BookNotFoundExecption;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.forms.UserForm;

import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.services.BookService;
import br.com.nycdev.personallibrary.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserForm userForm) {
        try {
            return new ResponseEntity<>(userService.registerUser(userForm), HttpStatus.CREATED);
        } catch (UserLoginAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping("/{userId}")
    @GetMapping
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        try {
            UserDto userDto = new UserDto(userService.findUserById(userId));
            return new ResponseEntity<>(userDto, HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping("/books")
    @GetMapping
    public ResponseEntity<List<BookDto>> getUserBooks(@RequestHeader("accessToken") String accessToken) {
        try {
            return new ResponseEntity<>(userService.findUserBooks(accessToken), HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping("/books/{id}")
    @DeleteMapping
    public ResponseEntity<BookDto> removeBook(@RequestHeader("accessToken") String accessToken, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.removeBook(accessToken, id), HttpStatus.ACCEPTED);
        } catch (BookNotFoundExecption e) {
            throw new RuntimeException(e);
        }
    }

}
