package br.com.nycdev.personallibrary.repositorys;

import br.com.nycdev.personallibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

  @Override
  Optional<Book> findById(Long aLong);

  Optional<Book> findBookByName(String name);
  List<Book> findBooksByNameContains(String name);
  Optional<Book> findBookByAuthor(String author);

  List<Book> findBooksByOwnerId(Long ownerId);
  Optional<Book> removeBookById(Long id);
}
