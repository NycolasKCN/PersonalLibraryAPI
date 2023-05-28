package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.AuthorizationDeniedException;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.exceptions.UserNotFoundException;
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
        } catch (BookAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    
    @PostMapping("/all")
    @GetMapping
    public List<BookDto> findAllBooksFromUser(@RequestHeader("Authorization") String token) {
        // TODO: 27/05/2023 Pegar todos os livros do usuário que está no corpo da requisição, confirmar se tem autorização.
        return service.getAll();
    }

    @RequestMapping("/{id}")
    @GetMapping
    public ResponseEntity<Book> findBookById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        // TODO: 27/05/2023 Encontrar o livro com id, verificar se tem autorização para pegar. 
        return null;
    }
    
    @RequestMapping("/delete/{id}")
    @DeleteMapping
    public ResponseEntity<BookDto> deleteBookById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        // TODO: 27/05/2023 Deletar o livro com o id, verificar se tem autorização para deletar. 
        return null;
    }

}
