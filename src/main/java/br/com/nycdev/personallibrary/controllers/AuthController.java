package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.dtos.TokenDto;
import br.com.nycdev.personallibrary.forms.LoginForm;
import br.com.nycdev.personallibrary.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("/token")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<TokenDto> generateToken(@RequestBody LoginForm form) {
    UsernamePasswordAuthenticationToken loginData = form.convert();

    try {
      Authentication authentication = authenticationManager.authenticate(loginData);
      TokenDto tokenDto = tokenService.generateToken(authentication);
      return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    } catch (AuthenticationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}