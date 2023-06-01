package br.com.nycdev.personallibrary.dtos;

import br.com.nycdev.personallibrary.models.Book;

public class BookDto {
  private long id;
  private String name;
  private String author;
  private String pages;
  private String owner;

  public BookDto(Book book) {
    this.id = book.getId();
    this.name = book.getName();
    this.author = book.getAuthor();
    this.owner = book.getOwner().getName();
    this.pages = book.getPages();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getPages() {
    return pages;
  }

  public void setPages(String pages) {
    this.pages = pages;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
