package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.TokenDto;
import br.com.nycdev.personallibrary.forms.LoginForm;
import br.com.nycdev.personallibrary.forms.TokenForm;
import br.com.nycdev.personallibrary.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> authenticationUser(@RequestBody LoginForm form) {
        UsernamePasswordAuthenticationToken loginData = form.convert();

        try {
            Authentication authentication = authenticationManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/isValid")
    @PostMapping
    public boolean isValid(@RequestBody TokenForm token) {
        System.out.println(token);
        return tokenService.isValidToken(token.token());
    }

    @RequestMapping("/userIdInToken")
    @PostMapping
    public Long userIdInToken(@RequestBody TokenForm token) {
        return tokenService.getUserIdInToken(token.token());
    }
}
