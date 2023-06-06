package br.com.nycdev.personallibrary.configuration;

import br.com.nycdev.personallibrary.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  private final AuthenticationConfiguration authConfiguration;

  @Autowired
  private AuthService authService;

  public WebSecurityConfig(AuthenticationConfiguration authConfiguration) {
    this.authConfiguration = authConfiguration;
  }

  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return authConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.authService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("authenticate").permitAll()
//                .requestMatchers("/v1/books").permitAll()
//                .anyRequest().authenticated()
//                .and().build();
    return http.csrf().disable()
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and().headers().frameOptions().sameOrigin()
            .and().build();
  }
}
