package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

  public String generateToken(Authentication authentication) {
    User loggedUser = (User) authentication.getPrincipal();
    Date today = new Date();
    Date expirationDate = new Date((today.getTime() + Long.parseLong(expiration)));

    return Jwts.builder()
            .setIssuer("PersonalLibrary")
            .setSubject(loggedUser.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean hasAuthorization(String token, Long id) {
    if (isValidToken(token)) {
      return false;
    }
    return getUserIdInToken(token).equals(id);
  }

  public Long getUserIdInToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    return Long.parseLong(claims.getSubject());
  }
}
