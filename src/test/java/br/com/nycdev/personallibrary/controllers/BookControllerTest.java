package br.com.nycdev.personallibrary.controllers;

import br.com.nycdev.personallibrary.models.User;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
@Profile("test")
public class BookControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestEntityManager testEntityManager;

  private String token;

  @Test
  void shouldReturn200ifBookDataIsValid() throws Exception {
    if (token == null) init();
    URI uri = new URI("/v1/books/add");
    String request = "{\"name\":\"book test\", \"author\":\"test author\"}";

    mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .header("AccessToken", "Bearer " + token)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\": 1,\"name\": \"book test\", \"author\":\"test author\", \"owner\": \"Joaquin test\"}"));

  }

  public void init() throws Exception {
    System.out.println("------ init ------");
    User user = new User("Joaquin test", "joaquin.test", "12345678", null);
    testEntityManager.persist(user);

    URI uri = new URI("/authenticate");
    String requestBody = "{\"login\":\"joaquin.test\",\"password\":\"12345678\"}";

    String response = mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    System.out.println("response " + response);
    JSONObject jsonObject = new JSONObject(response);
    token = jsonObject.getString("accessToken");
    System.out.println("token: " + token);
  }
}
