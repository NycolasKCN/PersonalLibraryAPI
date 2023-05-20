package br.com.nycdev.personallibrary.forms;

import java.util.List;

public record UserForm (String name, String login, String password, String role){
    @Override
    public String toString() {
        return "UserForm{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
