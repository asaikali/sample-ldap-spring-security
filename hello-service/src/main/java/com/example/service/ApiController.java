package com.example.service;

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
class ApiController {


  @GetMapping("/time")
  String  time(HttpSession httpSession) {
    // this is not thread safe
    Integer counter = (Integer) httpSession.getAttribute("counter");
    if(counter == null) {
      counter = 1;
    } else {
      counter += 1;
    }
    httpSession.setAttribute("counter", counter);

  return "Hello session " + httpSession.getId() +
         "\ncounter is " + counter +  "\ncurrent time is " + LocalDateTime.now();
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
