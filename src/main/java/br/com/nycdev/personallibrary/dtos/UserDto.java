package br.com.nycdev.personallibrary.dtos;

import br.com.nycdev.personallibrary.models.User;

public class UserDto {
    private long id;
    private String name;
    private String login;

    public UserDto() {

    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getUsername();
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
