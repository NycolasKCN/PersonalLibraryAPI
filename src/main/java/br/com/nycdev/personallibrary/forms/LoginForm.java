package br.com.nycdev.personallibrary.forms;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginForm(String login, String password) {

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(login, password);
    }
}
