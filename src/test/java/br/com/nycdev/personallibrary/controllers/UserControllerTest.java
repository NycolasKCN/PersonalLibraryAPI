package br.com.nycdev.personallibrary.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
@Import(UserController.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturn201IfRegistrationDataIsValid() throws Exception {
    URI uri = new URI("/v1/users");
    String body = "{\"name\": \"Jonh test\",\"login\": \"jonh.test\",\"password\":\"123456\"}";

    mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void shouldReturn400IfThereIsAlreadyARegisteredUserWithLoginSent() throws Exception {
    URI uri = new URI("/v1/users");
    String body1 = "{\"name\": \"Abbie test\",\"login\": \"abbie.test\",\"password\":\"123456\"}";
    String body2 = "{\"name\": \"Amanda test\",\"login\": \"abbie.test\",\"password\":\"123456\"}";

    mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .content(body1)
            .contentType(MediaType.APPLICATION_JSON));

    mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(body2)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status()
                    .isBadRequest());
  }
}
