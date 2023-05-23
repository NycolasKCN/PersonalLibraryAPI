package br.com.nycdev.personallibrary.forms;

public class BookForm {
    private long userId;
    private String name;
    private String author;

    public BookForm(long userId, String name, String author) {
        this.userId = userId;
        this.name = name;
        this.author = author;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
