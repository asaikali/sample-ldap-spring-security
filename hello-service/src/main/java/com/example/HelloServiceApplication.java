package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class HelloServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HelloServiceApplication.class, args);
  }

}
