package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByLogin(login);

    if (user.isPresent()) {
      return user.get();
    }
    throw new UsernameNotFoundException("User with login: " + login + " not founded. Threw by AuthService class.");
  }
}
