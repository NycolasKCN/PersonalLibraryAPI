package br.com.nycdev.personallibrary.models;

import br.com.nycdev.personallibrary.forms.BookForm;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private long id;

  private String name;

  private String author;
  private String pages;
  @ManyToOne
  private User owner;

  protected Book() {
  }

  public Book(String name, String author) {
    this.name = name;
    this.author = author;
  }

  public Book(BookForm form) {
    this.name = form.name();
    this.author = form.author();
    this.pages = form.pages();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getOwner() {
    return this.owner;
  }

  public String getPages() {
    return pages;
  }

  public void setPages(String pages) {
    this.pages = pages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Book book = (Book) o;

    if (!name.equals(book.name)) return false;
    return author.equals(book.author);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + author.hashCode();
    return result;
  }
}
