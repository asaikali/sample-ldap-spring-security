package com.example.client;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class ClientController {


  private final RestTemplate restTemplate;
  ClientController(RestTemplateBuilder restTemplateBuilder,  LoginHelper loginHelper) {
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofMillis(3000))
        .rootUri("http://localhost:9999")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .additionalInterceptors( new FormLoginInterceptor(loginHelper) )
        .build();
  }

  @GetMapping("/")
  String root()
  {
    return "Client called hello service got back: '" + restTemplate.getForObject("/time",String.class) + "'";
  }
}
