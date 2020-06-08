package com.example.ldap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {
  @Value("${spring.ldap.embedded.port}")
  private String port;

  @GetMapping("/")
  String  get() {
    return "Embedded LDAP server running on port  " + port;
  }
}
