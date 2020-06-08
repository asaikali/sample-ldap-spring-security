package com.example;

import java.security.Principal;
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AppController {

  @GetMapping("/")
  String  get() {
    return "Hello the time is  " + LocalDateTime.now();
  }

  @GetMapping("/user")
  LdapUserDetails user(@AuthenticationPrincipal LdapUserDetails user)
  {
    return user;
  }
  @GetMapping("/expireSession")
  String expireSession(HttpSession httpSession)
  {
    String result = "Expired Session  " + httpSession.getId();
    httpSession.invalidate();
    return result;
  }
}
