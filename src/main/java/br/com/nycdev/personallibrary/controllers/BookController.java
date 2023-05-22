package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.repositorys.BookRepository;
import br.com.nycdev.personallibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<BookDto> addBook(@RequestHeader("accessToken") String token, @RequestBody BookForm bookForm) {
        try {
            return new ResponseEntity<>(service.addBookToUser(token, bookForm), HttpStatus.CREATED);
        } catch (BookAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping
    public List<BookDto> getAll() {
        return service.getAll();
    }


}
