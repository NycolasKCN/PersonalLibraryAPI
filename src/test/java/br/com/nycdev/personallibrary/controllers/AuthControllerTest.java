package br.com.nycdev.personallibrary.controllers;


import br.com.nycdev.personallibrary.models.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Profile("test")
@Transactional
public class AuthControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void shouldReturn200IfAuthenticationDataIsValid() throws Exception {
    User user = new User("Tony test", "tony.test", "123456", null);
    testEntityManager.persist(user);

    URI uri = new URI("/authenticate");

    String body = "{\"login\":\"tony.test\",\"password\":\"123456\"}";

    mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                    .status()
                    .is(200));
  }

  @Test
  public void shouldReturn400IfAuthenticationDataIsNotValid() throws Exception {
    User user = new User("Jones test", "jones.test", "123456", null);
    testEntityManager.persist(user);

    URI uri = new URI("/authenticate");

    String body = "{\"login\":\"jones.test\",\"password\":\"1789\"}";

    mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers
                    .status()
                    .is(400));
  }
}
