package br.com.nycdev.personallibrary.repositorys;

import br.com.nycdev.personallibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    Optional<Book> findById(Long aLong);

    Optional<Book> findBookByName(String name);
    Optional<Book> findBookByAuthor(String author);
}
