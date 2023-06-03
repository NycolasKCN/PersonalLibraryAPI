package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.TokenDto;
import br.com.nycdev.personallibrary.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

  @Value("${personalLibrary.jwt.expiration}")
  private String expiration;
  @Value("${personalLibrary.jwt.secret}")
  private String secret;

  public TokenDto generateToken(Authentication authentication) {
    User loggedUser = (User) authentication.getPrincipal();
    Date today = new Date();
    Date expirationDate = new Date((today.getTime() + Long.parseLong(expiration)));

    String token = Jwts.builder()
            .setIssuer("PersonalLibrary")
            .setSubject(loggedUser.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();

    return new TokenDto(loggedUser, token);
  }

  public boolean hasAuthorization(String headerToken, Long id) {
    return getUserIdInToken(headerToken).equals(id);
  }

  public Long getUserIdInToken(String headerToken) {
    String token = recoverTokenFromHeader(headerToken);
    Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    return Long.parseLong(claims.getSubject());
  }

  public String recoverTokenFromHeader(String token) {
    return token.substring(7);
  }
}
