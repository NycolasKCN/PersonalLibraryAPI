package br.com.nycdev.personallibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class PersonallibraryApplication {

  public static void main(String[] args) {
    SpringApplication.run(PersonallibraryApplication.class, args);
  }

}
