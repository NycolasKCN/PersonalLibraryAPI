package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.BookDto;
import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.BookAlreadyExistsException;
import br.com.nycdev.personallibrary.forms.BookForm;
import br.com.nycdev.personallibrary.models.Book;
import br.com.nycdev.personallibrary.repositorys.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public BookDto addBook(BookForm bookForm) throws BookAlreadyExistsException {
        Optional<Book> optionalBook = repository.findBookByName(bookForm.name());

        if (optionalBook.isPresent()) {
            throw new BookAlreadyExistsException("Book with name: " + bookForm.name() + " already exist.");
        }

        Book book = new Book(bookForm);
        repository.save(book);
        return new BookDto(book);
    }

    public List<BookDto> getAll() {
        return repository.findAll().stream().map(BookDto::new).toList();
    }
}
