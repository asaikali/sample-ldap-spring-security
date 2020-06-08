package com.example.client;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class LoginHelper {
  @Value("${helloService.loginUrl}")
  private String loginUrl;

  @Value("${helloService.username}")
  private String username;

  @Value("${helloService.password}")
  private String password;

  private final RestTemplate restTemplate;

  private Optional<String> sessionCookie = Optional.empty();

  public LoginHelper(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  /**
   * Login the user and return the Session cookie that should be included in future requests.
   *
   * @return Optional with the Session cookie, returns an empty optional otherwise
   */
   synchronized public Optional<String> login() {
     if(sessionCookie.isEmpty()) {
        sendLoginRequest();
     }
     return sessionCookie;
   }

   synchronized public void clearSession() {
     this.sessionCookie = Optional.empty();
   }

  private void sendLoginRequest() {
    ResponseEntity<String> response = restTemplate
        .postForEntity(loginUrl, createRequestEntity(), String.class);
    if (response.getStatusCode().is2xxSuccessful()) {
      this.sessionCookie = Optional.of(extractSessionCookie(response));
    } else {
       this.sessionCookie = Optional.empty();
    }
  }

  private HttpEntity<MultiValueMap<String, String>> createRequestEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("x-api-login","true");

    MultiValueMap<String, String> body= new LinkedMultiValueMap<>();
    body.add("username",username);
    body.add("password",password);

    return new HttpEntity<>(body, headers);
  }

  private String extractSessionCookie(HttpEntity<String> response) {
    List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
    //TODO search for the SESSION COOKIE
    return cookies.get(0);
  }

}
