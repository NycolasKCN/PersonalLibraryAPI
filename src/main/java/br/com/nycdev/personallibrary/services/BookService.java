package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.BookRepository;
import br.com.nycdev.personallibrary.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TokenService tokenService;

    public BookDto addBookToUser(String accessToken, BookForm bookForm) throws BookAlreadyExistsException, UsernameNotFoundException {
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
}
